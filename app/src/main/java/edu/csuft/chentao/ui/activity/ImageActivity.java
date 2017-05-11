package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityImagePresenter;
import edu.csuft.chentao.databinding.ActivityImageBinding;

public class ImageActivity extends BaseActivity {

    private ActivityImageBinding mActivityBinding = null;
    private ActivityImagePresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_image;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityImageBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new ActivityImagePresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnStop() {
        mPresenter.unregisterEventBus();
    }
}
