package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.controller.presenter.ActivityFilePresenter;
import edu.csuft.chentao.databinding.ItemFileBinding;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.ui.view.CustomerAlertDialog;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2017-05-01 19:40.
 * email:qxinhai@yeah.net
 */

/**
 * FileActivity的ListView的适配器
 */
public class FileAdapter extends BaseAdapter {

    private List<FileZip> mFileZipList;
    private Context mContext;
    private int mGroupId;
    private ActivityFilePresenter mPresenter;
    /**
     * 判断文件是否下载成功集合
     */
    private Map<String, Boolean> mIsDownloadMap;
    private File[] mLocalFileList;
    private File mDirectory;

    public FileAdapter(Context context, ActivityFilePresenter presenter, List<FileZip> fileZipList, int groupId) {
        this.mFileZipList = fileZipList;
        this.mContext = context;
        this.mGroupId = groupId;
        this.mPresenter = presenter;
    }

    @Override
    public int getCount() {
        return mFileZipList.size();
    }

    @Override
    public FileZip getItem(int position) {
        return mFileZipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemFileBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_file, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        /*
        绑定数据
         */
        FileZip fileZip = getItem(position);
        binding.setVariable(BR.itemPresenter, new ItemFilePresenter(binding, fileZip));
        binding.setVariable(BR.fileZip, fileZip);
//        isFileDownloadComplete(fileZip);
        //如果已经下载了，则显示已经下载的图片
        binding.setVariable(BR.isVisible, isFileDownloadComplete(fileZip)
                ? View.VISIBLE
                : View.GONE);

        return binding.getRoot();
    }

    /**
     * 判断文件是否已经下载，对每一个添加到list中的数据都进行判断一次
     */
    private boolean isFileDownloadComplete(FileZip fz) {
        //默认为false
        boolean result = false;

        //创建Map集合
        if (mIsDownloadMap == null) {
            mIsDownloadMap = new HashMap<>();
        }

        //创建目录对象
        if (mDirectory == null) {
            String path = Constant.PATH_FILE + File.separator + mGroupId;
            mDirectory = new File(path);
        }

        //如果目录不存在，则直接返回false，并且将该serialNumber在Map中置为false
        if (!mDirectory.exists()) {
            mIsDownloadMap.put(fz.getSerialNumber(), false);
            return false;
        }

        //得到目录下文件列表集合
        if (mLocalFileList == null) {
            mLocalFileList = mDirectory.listFiles();
        }

        String serialNumber = fz.getSerialNumber();
        String fileName = fz.getFileName();
        String fileSize = fz.getFileSize();

        for (File file : mLocalFileList) {
            String fName = file.getName();
            fName = fName.substring(0, fName.length() > fileName.length()
                    ? (fileName.length())
                    : (fName.length()));
            String fSize = String.valueOf(file.length());

            LoggerUtil.logger("TAG", "FileAdapter-->fileName=" + fileName + "---fName=" + fName);

            if (fileName.equals(fName) && fileSize.equals(fSize)) {
                mIsDownloadMap.put(serialNumber, true);
                result = true;
                break;
            }
        }


        return result;
    }

    /**
     * ItemFile布局的Presenter对象，用来处理item的事件
     */
    public class ItemFilePresenter implements CustomerAlertDialog.IAlertDialogClickListener {

        private ItemFileBinding mBinding;
        private FileZip mFileZip;

        ItemFilePresenter(ItemFileBinding binding, FileZip fileZip) {
            this.mBinding = binding;
            this.mFileZip = fileZip;
        }

        /**
         * 点击下载文件
         */
        public void doClickToDownloadFile() {

            LoggerUtil.showToast(mContext, "下载");

            if (mIsDownloadMap.get(mFileZip.getSerialNumber()) != null
                    && mIsDownloadMap.get(mFileZip.getSerialNumber())) {

                LoggerUtil.showToast(mContext, "该文件已经下载完成，请不要重复下载");

                return;
            }

            GetInfoReq req = new GetInfoReq();
            req.setType(Constant.TYPE_GET_INFO_DOWNLOAD_FILE);
            req.setObj(mFileZip.getSerialNumber());

            SendMessageUtil.sendMessage(req);
        }

        /**
         * 点击删除文件
         */
        public void doClickToRemoveFile() {
            closeOpenedItems();

            LoggerUtil.showToast(mContext, "删除");
            CustomerAlertDialog dialog = new CustomerAlertDialog(
                    mContext, this, "提醒", "是否确认删除该文件", "确认", "取消");
            dialog.show();
        }

        /**
         * 点击打开文件
         */
        public void doClickToOpenFile() {

            closeOpenedItems();

            LoggerUtil.showToast(mContext, "打开");
            String path = Constant.PATH_FILE + File.separator + mGroupId;

            String fileName = mFileZip.getFileName();
            String fileSize = mFileZip.getFileSize();

            File file = new File(path, mFileZip.getFileName());
            int index = 1;
            while (file.exists()) {
                String fSize = String.valueOf(file.length());
                String fName = file.getName();
                fName = fName.substring(0, fName.length() > fileName.length()
                        ? fileName.length()
                        : fName.length());

                LoggerUtil.logger("TAG", "FileAdapter--->fileName2=" + fileName + "---fName2=" + fName);

                if (fileName.equals(fName) && fileSize.equals(fSize)) {
                    openFile(file);
                    break;
                }

                file = new File(path, file.getName() + "_repeat_" + index);
                index++;
            }
        }

        private void openFile(File f) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);

             /* 调用getMIMEType()来取得MimeType */
            String type = getMIMEType(f);
            /* 设置intent的file与MimeType */
            intent.setDataAndType(Uri.fromFile(f), type);
            mContext.startActivity(intent);
        }

        /* 判断文件MimeType的method */
        private String getMIMEType(File f) {
            String type = "";
            String fName = f.getName();
            /* 取得扩展名 */
            String end = fName.substring(fName.lastIndexOf(".")
                    + 1, fName.length()).toLowerCase();

            /* 依扩展名的类型决定MimeType */
            if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                    end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
                type = "audio";
            } else if (end.equals("3gp") || end.equals("mp4")) {
                type = "video";
            } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                    end.equals("jpeg") || end.equals("bmp")) {
                type = "image";
            } else if (end.equals("apk")) {
                /* android.permission.INSTALL_PACKAGES */
                type = "application/vnd.android.package-archive";
            } else {
                type = "*";
            }
             /*如果无法直接打开，就跳出软件列表给用户选择 */
            if (end.equals("apk")) {
            } else {
                type += "/*";
            }
            return type;
        }

        @Override
        public void doClickAlertDialogToOk() {
            closeOpenedItems();
            /*
            确定删除文件
             */
            GetInfoReq req = new GetInfoReq();
            req.setType(Constant.TYPE_GET_INFO_REMOVE_FILE);
            req.setObj(mFileZip.getSerialNumber());
            req.setArg1(mGroupId);
            req.setArg2(SharedPrefUserInfoUtil.getUserId());

            SendMessageUtil.sendMessage(req);
        }
    }

    private void closeOpenedItems() {
        mPresenter.closeOpenedItems();
    }

    /**
     * 点击后，关闭打开状态的item
     */
    public interface ICloseOpenedItems {
        void closeOpenedItems();
    }
}
