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
    /**
     * 判断文件是否下载成功集合
     */
    private Map<String, Boolean> mIsDownloadMap;
    private File[] mLocalFileList;
    private File mDirectory;

    private ICloseOpenedItems mCloseItems;
    private IShowProgressDialog mShowDialog;


    public FileAdapter(Context context, ActivityFilePresenter presenter, List<FileZip> fileZipList, int groupId) {
        this.mFileZipList = fileZipList;
        this.mContext = context;
        this.mGroupId = groupId;

        mCloseItems = presenter;
        mShowDialog = presenter;
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

        binding.setVariable(BR.itemPresenter, new ItemFilePresenter(fileZip));
        binding.setVariable(BR.fileZip, fileZip);
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

        String serialNumber = fz.getSerialNumber();
        String fileName = fz.getFileName();
        String fileSize = fz.getFileSize();

        //默认为false
        boolean result = false;

        //创建Map集合
        if (mIsDownloadMap == null) {
            mIsDownloadMap = new HashMap<>();
        }

        /*
         * 如果数据不为空，则直接返回结果
         */
        if (mIsDownloadMap.get(serialNumber) != null) {
            return mIsDownloadMap.get(serialNumber);
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

        for (File file : mLocalFileList) {
            String fName = file.getName();

            //如果该文件夹中包含了同名的文件
            if (fName.contains("_repeat_")) {
                /*
                此步骤，主要为了移除掉index。
                该if段判断出该文件包含_repeat_字符串，却无法判断index的值，
                所以接下来的部分，主要为了得到index的值，然后移除掉整个部分_repeat_index
                 */
                while (true) {
                    int index = 1;
                    //构建同名文件后缀
                    String indexString = "_repeat_" + index;
                    //判断是否存在
                    if (fName.contains(indexString)) {
                        fName = fName.replace(indexString, "");
                        break;
                    }
                    //最大存储50个同名文件
                    if ((++index) >= 50) {
                        break;
                    }
                }
            }

            String fSize = String.valueOf(file.length());

            /*
            把文件夹中的文件移除掉后缀，如果文件名和文件大小都与服务端文件一样，那么表示已下载
             */
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

        private FileZip mFileZip;

        ItemFilePresenter(FileZip fileZip) {
            this.mFileZip = fileZip;
        }

        /**
         * 点击下载文件
         */
        public void doClickToDownloadFile() {

            if (mIsDownloadMap.get(mFileZip.getSerialNumber()) != null
                    && mIsDownloadMap.get(mFileZip.getSerialNumber())) {

                LoggerUtil.showToast(mContext, "该文件已经下载完成，请不要重复下载");

                return;
            }

            GetInfoReq req = new GetInfoReq();
            req.setType(Constant.TYPE_GET_INFO_DOWNLOAD_FILE);
            req.setObj(mFileZip.getSerialNumber());

            showDownloadDialog();
            closeOpenedItems();
            SendMessageUtil.sendMessage(req);
        }

        /**
         * 点击删除文件
         */
        public void doClickToRemoveFile() {
            closeOpenedItems();

            CustomerAlertDialog dialog = new CustomerAlertDialog(
                    mContext, this, "提醒", "是否确认删除该文件", "确认", "取消");
            dialog.show();
        }

        /**
         * 点击打开文件
         */
        public void doClickToOpenFile() {

            closeOpenedItems();

            String path = Constant.PATH_FILE + File.separator + mGroupId;

            //要打开软件的文件名
            String fileName = mFileZip.getFileName();
            //要打开软件的大小
            String fileSize = mFileZip.getFileSize();

            //根据文件名构造文件对象
            File file = new File(path, mFileZip.getFileName());
            int index = 1;
            while (file.exists()) {
                //文件夹中文件大小
                String fSize = String.valueOf(file.length());

                //如果能匹配上
                if (fileSize.equals(fSize)) {
                    openFile(file);
                    break;
                }

                //往文件名后面加上后缀 _repeat_
                String indexString = fileName.substring(0, fileName.lastIndexOf("."));
                String fName = indexString + "_repeat_" + index + fileName.substring(fileName.lastIndexOf("."));

                LoggerUtil.logger(FileAdapter.class, "fName = " + fName);

                file = new File(path, fName);
                index++;
            }
        }

        /**
         * 打开文件
         */
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

    public void setFileDownloadSuccess(String serialNumber) {
        mIsDownloadMap.put(serialNumber, true);
    }


    /**
     * 关闭所有打开的选项
     */
    private void closeOpenedItems() {
        mCloseItems.closeOpenedItems();
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog() {
        mShowDialog.showDownloadDialog();
    }

    /**
     * 点击后，关闭打开状态的item
     */
    public interface ICloseOpenedItems {
        void closeOpenedItems();
    }

    /**
     * 显示进度条对话框
     */
    public interface IShowProgressDialog {
        /**
         * 显示下载对话框
         */
        void showDownloadDialog();
    }
}