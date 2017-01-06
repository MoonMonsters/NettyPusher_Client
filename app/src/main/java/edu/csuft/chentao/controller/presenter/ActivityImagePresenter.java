package edu.csuft.chentao.controller.presenter;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import edu.csuft.chentao.databinding.ActivityImageBinding;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2017-01-04 11:19.
 * email:qxinhai@yeah.net
 */

public class ActivityImagePresenter {

    private ActivityImageBinding mActivityBinding = null;

    public ActivityImagePresenter(ActivityImageBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void onClickToSaveImage() {
        byte[] buf = mActivityBinding.getMessage().getImage();
        try {
            File file = new File(Constant.PATH, OperationUtil.getImageName());
            boolean isCreateNewFile = false;
            if (!file.exists()) {
                isCreateNewFile = file.createNewFile();
            }
            if (isCreateNewFile) {  //文件创建成功
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buf);
                fos.close();

                showSnakeBar("图片保存成功", "完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSnakeBar("图片保存失败", "取消");
        }
    }

    private void showSnakeBar(String action, String command) {
        final Snackbar snackbar = Snackbar.make(mActivityBinding.getRoot(), action, Snackbar.LENGTH_SHORT);
        snackbar.show();
        snackbar.setAction(command, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
    }

}
