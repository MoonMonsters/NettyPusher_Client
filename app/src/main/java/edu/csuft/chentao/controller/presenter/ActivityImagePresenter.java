package edu.csuft.chentao.controller.presenter;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityImageBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2017-01-04 11:19.
 * email:qxinhai@yeah.net
 */

public class ActivityImagePresenter extends BasePresenter {

    private ActivityImageBinding mActivityBinding = null;

    public ActivityImagePresenter(ActivityImageBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    /**
     * 保存图片到本地
     */
    public void onClickToSaveImage() {
        byte[] buf = mActivityBinding.getDetail().getImage();
        try {
            File file = OperationUtil.createFile(Constant.PATH, OperationUtil.getImageName());
            if (file != null) {  //文件创建成功
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buf);
                fos.close();

                showSnakeBar(OperationUtil.getString(mActivityBinding, R.string.string_save_image_success),
                        OperationUtil.getString(mActivityBinding, R.string.string_complete));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showSnakeBar(OperationUtil.getString(mActivityBinding, R.string.string_save_image_fail),
                    OperationUtil.getString(mActivityBinding, R.string.string_fail));
        }
    }

    /**
     * 显示底部提示栏
     *
     * @param action  显示内容
     * @param command 点击内容
     */
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

    @Override
    protected void initData() {
        ImageDetail imageDetail = (ImageDetail) ((ImageActivity) mActivityBinding.getRoot().getContext())
                .getIntent().getSerializableExtra(Constant.EXTRA_IMAGE_DETAIL);
        mActivityBinding.setVariable(BR.detail, imageDetail);
    }
}
