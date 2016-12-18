package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mMainBinding = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mMainBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
