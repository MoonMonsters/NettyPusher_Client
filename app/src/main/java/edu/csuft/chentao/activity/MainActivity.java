package edu.csuft.chentao.activity;

import android.content.IntentFilter;
import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMainPresenter;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityBinding = null;

    private ActivityMainPresenter.MainReceiver mReceiver = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new ActivityMainPresenter.MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOGIN);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void initData() {
        new ActivityMainPresenter(mActivityBinding).init();
    }
}