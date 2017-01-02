package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.activity.LoginActivity;
import edu.csuft.chentao.activity.RegisterActivity;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2016-12-28 13:41.
 * email:qxinhai@yeah.net
 */

public class ActivityLoginPresenter {

    private ActivityLoginBinding mBinding = null;
    private Context mContext = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_LOGIN_SUCCESS) {
                //得到用户id
                int userId = (int) msg.obj;
                //得到登录的用户名和密码，并保存
                String username = mBinding.etLoginUsername.getText().toString();
                String password = mBinding.etLoginPassword.getText().toString();
                //保存用户名和密码
                SharedPrefUserInfoUtil.setUsernameAndPassword(username, password);
                //保存用户id
                SharedPrefUserInfoUtil.setUserId(userId);
                //将登录类型设置成自动登录类型
                SharedPrefUserInfoUtil.setLoginType();

            }
        }
    };

    public ActivityLoginPresenter(ActivityLoginBinding binding) {
        this.mBinding = binding;
        mContext = mBinding.getRoot().getContext();

        //发送Handler对象到Activity
        EventBus.getDefault().post(new HandlerMessage(mHandler, "LoginActivity"));

    }

    /**
     * 点击登录
     */
    public void onClickToLogin() {
        String username = mBinding.etLoginUsername.getText().toString();
        String password = mBinding.etLoginPassword.getText().toString();

        //包装成登录信息
        LoginReq req = new LoginReq();
        req.setUsername(username);
        req.setPassword(password);
        req.setType(Constant.TYPE_LOGIN_NEW);

        //发送数据
        SendMessageUtil.sendMessage(req);
    }

    /**
     * 点击后，进入RegisterActivity
     */
    public void onClickToRegisterActivity() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mBinding.getRoot().getContext().startActivity(intent);
        finishLoginActivity();
    }

    /**
     * 关闭LoginActivity
     */
    private void finishLoginActivity() {
        ((LoginActivity) mContext).finish();
    }
}
