package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mActivityBinding = null;

    @Override
    public int getLayoutResourceId() {
        return 0;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityRegisterBinding) viewDataBinding;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
