package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityLoginPresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mActivityBinding = null;
    private ActivityLoginPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityLoginBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new ActivityLoginPresenter(mActivityBinding);
        LoggerUtil.logger(Constant.TAG, "2-->" + mActivityBinding.toString());
        mActivityBinding.setPresenter(mPresenter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销EventBus
        mPresenter.unregisterEventBus();
    }
}