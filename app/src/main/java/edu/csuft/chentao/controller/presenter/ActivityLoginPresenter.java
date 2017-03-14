package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.ui.activity.LoginActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.ui.activity.RegisterActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2016-12-28 13:41.
 * email:qxinhai@yeah.net
 */

public class ActivityLoginPresenter extends BasePresenter {

    private ActivityLoginBinding mActivityBinding = null;

    public ActivityLoginPresenter(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityLoginBinding) viewDataBinding;
        init();
    }

    @Override
    public void initData() {
        LoggerUtil.logger(Constant.TAG, "3-->" + mActivityBinding.toString());
        if (mActivityBinding == null) {
            LoggerUtil.logger(Constant.TAG, "ActivityLoginPresenter->ActivityBinding为空");
        } else {
            LoggerUtil.logger(Constant.TAG, "ActivityLoginPresenter->ActivityBinding不为空");
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //登录成功
        //从Handler接收到数据，来判断是否登录成功
        //如果登录成功，则保存用户名及密码，以及修改相关其他数据
        if (ebObj.getTag().equals(Constant.TAG_USER_LOGIN_PRESENTER)) {

            UserInfoResp resp = (UserInfoResp) ebObj.getObject();
            //得到用户id
            int userId = resp.getUserid();
            if (userId > 0) {
                //得到登录的用户名和密码，并保存
                String username = mActivityBinding.etLoginUsername.getText().toString();
                String password = mActivityBinding.etLoginPassword.getText().toString();

                //保存用户名和密码
                SharedPrefUserInfoUtil.setUsernameAndPassword(username, password);
                //保存用户id
                SharedPrefUserInfoUtil.setUserId(userId);
                //将登录类型设置成自动登录类型
                SharedPrefUserInfoUtil.setLoginType();

                mActivityBinding.getRoot().getContext().startActivity(
                        new Intent(mActivityBinding.getRoot().getContext(),
                                MainActivity.class));
                finishLoginActivity();
            }
        }
    }

    /**
     * 点击登录
     */
    public void onClickToLogin() {
        String username = mActivityBinding.etLoginUsername.getText().toString();
        String password = mActivityBinding.etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mActivityBinding.getRoot().getContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mActivityBinding.getRoot().getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

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
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), RegisterActivity.class);
        mActivityBinding.getRoot().getContext().startActivity(intent);
        finishLoginActivity();
    }

    /**
     * 关闭LoginActivity
     */
    private void finishLoginActivity() {
        ((LoginActivity) mActivityBinding.getRoot().getContext()).finish();
    }
}
