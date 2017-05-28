package edu.csuft.chentao.controller.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityFileBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.ui.activity.FileActivity;
import edu.csuft.chentao.ui.adapter.FileAdapter;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * @author csuft.chentao
 * @date 2017-04-23
 */

/**
 * FileActivity的处理类
 */
public class ActivityFilePresenter extends BasePresenter
        implements FileAdapter.ICloseOpenedItems, FileAdapter.IShowProgressDialog {
    private ActivityFileBinding mActivityBinding;
    private int mGroupId;

    private List<FileZip> mFileZipList;
    private FileAdapter mAdapter;

    private ProgressDialog mProgressDialog;

    public ActivityFilePresenter(ActivityFileBinding activityBinding, int groupId) {
        this.mActivityBinding = activityBinding;
        this.mGroupId = groupId;

        init();
    }

    @Override
    protected void initData() {
        mActivityBinding.setVariable(BR.title, "群文件");

        requestGroupFileList();
        mFileZipList = new ArrayList<>();
        mAdapter = new FileAdapter(mActivityBinding.getRoot().getContext(), this, mFileZipList, mGroupId);
        mActivityBinding.slvFileContent.setAdapter(mAdapter);
    }

    @Override
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //在界面上显示数据
        if (ebObj.getTag().equals(Constant.TAG_FILE_PRESENTER)) {

            LoggerUtil.logger("FilePresenter", "增加数据");

            FileZip fileZip = (FileZip) ebObj.getObject();
            mFileZipList.add(fileZip);
            adapterToNotifyDataChanged();
            //删除列表中的数据
        } else if (ebObj.getTag().equals(Constant.TAG_FILE_PRESENTER_REMOVE_FILE)) {

            LoggerUtil.logger("FilePresenter", "删除数据");

            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            String serialNumber = (String) resp.getObj();

            FileZip fz = null;
            for (FileZip f : mFileZipList) {
                //在列表中一一匹配
                if (f.getSerialNumber().equals(serialNumber)) {
                    fz = f;
                    break;
                }
            }
            mFileZipList.remove(fz);
            adapterToNotifyDataChanged();
        } else if (ebObj.getTag().equals(Constant.TAG_FILE_PRESENTER_REFRESH)) {

            LoggerUtil.logger("FilePresenter", "刷新数据");
            LoggerUtil.logger("FilePresenter", "下载完成-->" + Constant.TAG_FILE_PRESENTER_REFRESH);

            FileZip fileZip = (FileZip) ebObj.getObject();
            mAdapter.setFileDownloadSuccess(fileZip.getSerialNumber());
            adapterToNotifyDataChanged();
        }
    }

    /**
     * 点击选择文件
     */
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        ((FileActivity) mActivityBinding.getRoot().getContext())
                .startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 10);
    }

    @Override
    public void initListener() {
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FileActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    /**
     * 请求群文件列表数据
     */
    private void requestGroupFileList() {
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_GROUP_FILE_LIST);
        req.setArg1(mGroupId);
        SendMessageUtil.sendMessage(req);
    }

    private void showProgressDialog(String content) {
        mProgressDialog = new ProgressDialog(mActivityBinding.getRoot().getContext());
        mProgressDialog.setMessage(content);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "隐藏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hideProgress();
            }
        });
        mProgressDialog.show();
    }

    private void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 将uri转换成String类型路径
     */
    private String getPath(Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = mActivityBinding.getRoot().getContext().getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * 处理Activity传递的文件路径，上传文件
     */
    public void setPathUri(Uri uri) {
        String path = getPath(uri);
        File file = new File(path);
        try {
            byte[] buf = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(buf);
            fis.close();

            /*
            构造FileZip对象
             */
            FileZip fz = new FileZip();
            fz.setFileName(file.getName());
            fz.setGroupId(mGroupId);
            fz.setNickname(UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId()).getNickname());
            fz.setSerialNumber(OperationUtil.getSerialnumber());
            fz.setTime(OperationUtil.getCurrentTime());
            fz.setUserId(SharedPrefUserInfoUtil.getUserId());
            fz.setZip(buf);

            showUploadDialog();
            //传输文件
            SendMessageUtil.sendMessage(fz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortFileListByTime() {
        Collections.sort(mFileZipList, new Comparator<FileZip>() {
            @Override
            public int compare(FileZip lhs, FileZip rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
    }

    /**
     * 将文件按照时间排序，并且刷新数据
     */
    private void adapterToNotifyDataChanged() {
        hideProgress();
        sortFileListByTime();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeOpenedItems() {
        mActivityBinding.slvFileContent.closeOpenedItems();
    }

    @Override
    public void showDownloadDialog() {
        showProgressDialog("正在下载...");
    }

    private void showUploadDialog() {
        showProgressDialog("正在上传...");
    }
}