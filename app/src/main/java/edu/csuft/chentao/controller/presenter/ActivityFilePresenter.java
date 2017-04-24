package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityFileBinding;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.ui.activity.FileActivity;
import edu.csuft.chentao.utils.SendMessageUtil;

/**
 * @author csuft.chentao
 * @date 2017-04-23
 */

/**
 * FileActivity的处理类
 */
public class ActivityFilePresenter extends BasePresenter {
    private ActivityFileBinding mActivityBinding;
    private int mGroupId;

    public ActivityFilePresenter(ActivityFileBinding activityBinding, int groupId) {
        this.mActivityBinding = activityBinding;
        this.mGroupId = groupId;

        init();
    }

    @Override
    protected void initData() {
        mActivityBinding.setVariable(BR.title, "群文件");
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

    /**
     * 处理Activity传递的文件路径
     */
    public void setPathUri(Uri uri) {
        String path = getPath(uri);
        File file = new File(path);
        try {
            byte[] buf = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(buf);
            fis.close();

            FileZip fz = new FileZip();
            fz.setFileName("test");
            fz.setZip(buf);
            SendMessageUtil.sendMessage(fz);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
}
