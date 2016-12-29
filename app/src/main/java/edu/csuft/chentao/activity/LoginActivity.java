package edu.csuft.chentao.activity;

import android.content.IntentFilter;
import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityLoginPresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.utils.Constant;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mActivityBinding = null;

    private ActivityLoginPresenter.LoginReceiver mLoginReceiver = null;

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
        ActivityLoginPresenter presenter = new ActivityLoginPresenter(mActivityBinding);
        mActivityBinding.setPresenter(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        //添加广播
        filter.addAction(Constant.ACTION_LOGIN);
        mLoginReceiver = new ActivityLoginPresenter.LoginReceiver(LoginActivity.this);
        //注册广播
        this.registerReceiver(mLoginReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销广播
        this.unregisterReceiver(mLoginReceiver);
    }
}