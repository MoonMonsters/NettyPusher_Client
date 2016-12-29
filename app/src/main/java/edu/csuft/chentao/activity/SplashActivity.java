package edu.csuft.chentao.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Handler;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivitySplashBinding;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding mActivityBinding;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivitySplashBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        this.mActivityBinding.setImageUrl(Constant.SPLASH_IMAGE_NAME);
        this.mActivityBinding.setPlacehold(R.drawable.splash);

        enterAnotherActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SplashActivity.this.finish();
    }

    /**
     * 进入其他的Activity
     */
    private void startAnotherActivity(Class c){
        SplashActivity.this.startActivity(new Intent(this,c));
    }

    /**
     * 进入主Activity，并且关闭当前Activity
     */
    private void enterAnotherActivity() {

        int type = SharedPrefUserInfoUtil.getLoginType();
        if (type == Constant.TYPE_LOGIN_AUTO) {   //如果是自动登录类型

            String username = SharedPrefUserInfoUtil.getUsername();
            String password = SharedPrefUserInfoUtil.getPassword();

            LoginReq req = new LoginReq();
            req.setUsername(username);
            req.setPassword(password);
            req.setType(Constant.TYPE_LOGIN_AUTO);

            SendMessageUtil.sendMessage(req);

            startAnotherActivity(MainActivity.class);
        } else {  //否则在2秒后进入登录界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnotherActivity(LoginActivity.class);
            }
        }, 2000);
        }
    }
}