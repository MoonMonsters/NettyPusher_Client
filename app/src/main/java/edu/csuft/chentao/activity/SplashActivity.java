package edu.csuft.chentao.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Handler;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivitySplashBinding;
import edu.csuft.chentao.utils.Constant;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding mSplashBinding;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mSplashBinding = (ActivitySplashBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        this.mSplashBinding.setImageUrl(Constant.SPLASH_IMAGE_NAME);
        this.mSplashBinding.setPlacehold(R.drawable.splash);

        enterAnotherActivity();
    }

    @Override
    public void initListener() {

    }

    /**
     * 进入主Activity，并且关闭当前Activity
     */
    private void enterAnotherActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
