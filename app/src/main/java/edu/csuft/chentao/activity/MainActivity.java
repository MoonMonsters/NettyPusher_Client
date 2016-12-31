package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.widget.Toast;

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

    private MainReceiver mReceiver = null;

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
        mReceiver = new MainReceiver();
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

    public class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) {
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                if (isSuccess) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}