package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityHintPresenter;
import edu.csuft.chentao.databinding.ActivityHintBinding;

public class HintActivity extends BaseActivity {

    private ActivityHintBinding mActivityBinding;
    private ActivityHintPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_hint;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityHintBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new ActivityHintPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnDestroy() {
        mPresenter.unregisterEventBus();
    }
}
