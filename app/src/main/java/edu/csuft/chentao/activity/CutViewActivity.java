package edu.csuft.chentao.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yuyh.library.imgsel.ImgSelActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URI;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityCutViewPresenter;
import edu.csuft.chentao.databinding.ActivityCutViewBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;

public class CutViewActivity extends BaseActivity {

    private ActivityCutViewBinding mActivityBinding;
    private ActivityCutViewPresenter mActivityPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_cut_view;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityCutViewBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        mActivityPresenter = new ActivityCutViewPresenter(mActivityBinding, getIntent().getStringExtra(Constant.EXTRA_CUT_VIEW));
        mActivityBinding.setVariable(BR.presenter, mActivityPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityPresenter.unregisterEventBus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);

            File file = new File(URI.create("file://" + pathList.get(0)));
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                byte[] buf = OperationUtil.bitmapToBytes(bitmap);

                ImageDetail imageDetail = new ImageDetail(Constant.IMAGE_ACTIVITY_CUT_VIEW_PRESENTER, buf);
                EventBus.getDefault().post(imageDetail);

            } else {
                this.finish();
            }
            //如果点击了返回，或者没有选择图片，那么将会直接结束，回到原界面
        } else {
            this.finish();
        }
    }
}
