package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUtil;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mActivityBinding = null;

    private LoginReceiver mLoginReceiver = null;

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

    }

    @Override
    public void initListener() {
        this.mActivityBinding.btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginReq req = new LoginReq();
                req.setType(SharedPrefUtil.getLoginType());
                req.setUsername(LoginActivity.this.mActivityBinding.etLoginUsername.getText().toString());
                req.setPassword(LoginActivity.this.mActivityBinding.etLoginPassword.getText().toString());
                SendMessageUtil.sendMessage(req);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        //添加广播
        filter.addAction(Constant.ACTION_LOGIN);
        mLoginReceiver = new LoginReceiver();
        //注册广播
        this.registerReceiver(mLoginReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销广播
        this.unregisterReceiver(mLoginReceiver);
    }

    private class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) { //登录成功
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                LoggerUtil.logger("LOGIN", isSuccess + "");
                if (isSuccess) { //登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                } else {  //登录失败
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}