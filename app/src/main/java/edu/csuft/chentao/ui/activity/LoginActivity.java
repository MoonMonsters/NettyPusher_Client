package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityLoginPresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.utils.OperationUtil;

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

        OperationUtil.fullScreen(this);

        mPresenter = new ActivityLoginPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnStop() {
        //注销EventBus
        mPresenter.unregisterEventBus();
    }

}