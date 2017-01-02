package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityLoginPresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mActivityBinding = null;

    private LoginReceiver mLoginReceiver = null;

    private Handler mHandler = null;

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
        //注册EventBus
        EventBus.getDefault().register(this);

        ActivityLoginPresenter presenter = new ActivityLoginPresenter(mActivityBinding);
        mActivityBinding.setPresenter(presenter);
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("LoginActivity")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    /**
     * 登录类型广播
     */
    public class LoginReceiver extends BroadcastReceiver {

        private void enterMainActivity() {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            LoginActivity.this.finish();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) { //匹配广播
                //得到是否登录成功数据
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                if (isSuccess) { //登录成功
                    //得到用户的id
                    int userId = intent.getIntExtra(Constant.LOGIN_USER_ID, -1);
                    //发送消息
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_LOGIN_SUCCESS;
                    msg.obj = userId;
                    mHandler.sendMessage(msg);

                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    enterMainActivity();

                } else {  //登录失败
                    Toast.makeText(LoginActivity.this, "登录失败，请确认账号和密码后重新登录", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}