package edu.csuft.chentao.controller.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.ui.activity.LoginActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.ui.activity.RegisterActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

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
                String username = mActivityBinding.etLoginUsername.getEditText().getText().toString();
                String password = mActivityBinding.etLoginPassword.getEditText().getText().toString();

                //保存用户名和密码
                SharedPrefUserInfoUtil.setUsernameAndPassword(username, password);
                //保存用户id
                SharedPrefUserInfoUtil.setUserId(userId);

                //将登录类型设置成自动登录类型
                SharedPrefUserInfoUtil.setLoginType();

                dismissLoginProgressDialog();
                mActivityBinding.getRoot().getContext().startActivity(
                        new Intent(mActivityBinding.getRoot().getContext(),
                                MainActivity.class));
                finishLoginActivity();
            }
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_MAIN_PRESENTER_EXIT_LOGIN)) {
            dismissLoginProgressDialog();
        }
    }

    /**
     * Username输入框的输入监听事件
     */
    public void onTextChangedForUsername(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) { //用户名不能为空
            usernameNotNull();
        } else if (s.length() < 6) {    //用户名不能低于6位
            mActivityBinding.etLoginUsername.setError(OperationUtil.getString(mActivityBinding, R.string.string_not_length_less6_username));
            mActivityBinding.etLoginUsername.setErrorEnabled(true);
        } else {    //都符合，隐去错误提示
            mActivityBinding.etLoginUsername.setError(null);
            mActivityBinding.etLoginUsername.setErrorEnabled(false);
        }

        //根据输入的用户名获得用户信息
        UserInfo userInfo = UserInfoDaoUtil.getUserInfoByUsername(s.toString());
        //如果根据用户名，读取到了用户信息，则设置用户头像
        if (userInfo != null) {

            //得到用户头像
            UserHead userHead = UserHeadDaoUtil.getUserHead(userInfo.getUserid());
            if (userHead != null) {
                //设置图片
                mActivityBinding.setVariable(BR.userHead, userHead);
            }
        } else {    //没有用户信息，则使用默认头像
            mActivityBinding.ivLoginHead.setImageResource(R.mipmap.ic_launcher);
        }
    }

    /**
     * Password输入框的输入监听事件
     */
    public void onTextChangedForPassword(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) { //密码不能为空
            passwordNotNull();
        } else if (s.length() < 6) {    //密码不能低于6位
            mActivityBinding.etLoginPassword.setError(OperationUtil.getString(mActivityBinding, R.string.string_not_length_less6_password));
            mActivityBinding.etLoginPassword.setErrorEnabled(true);
        } else {    //都符合，隐去错误提示
            mActivityBinding.etLoginPassword.setError(null);
            mActivityBinding.etLoginPassword.setErrorEnabled(false);
        }
    }

    /**
     * 密码不能为空
     */
    private void passwordNotNull() {
        mActivityBinding.etLoginPassword.setError(OperationUtil.getString(mActivityBinding, R.string.string_not_none_password));
        mActivityBinding.etLoginPassword.setErrorEnabled(true);
    }

    /**
     * 用户名不能为空
     */
    private void usernameNotNull() {
        mActivityBinding.etLoginUsername.setError(OperationUtil.getString(mActivityBinding, R.string.string_not_none_username));
        mActivityBinding.etLoginUsername.setErrorEnabled(true);
    }

    /**
     * 点击登录
     */
    public void onClickToLogin() {

        if (!SendMessageUtil.sChannel.isActive()) {
            LoggerUtil.showToast(mActivityBinding.getRoot().getContext(), "网络异常，无法登录");

            return;
        }

        String username = mActivityBinding.etLoginUsername.getEditText().getText().toString();
        String password = mActivityBinding.etLoginPassword.getEditText().getText().toString();

        /*
        写用户名和密码不能为空的代码，是避免用户在不输入的情况下，直接按登录按钮而没有错误提示信息
         */
        //用户名不能为空
        if (TextUtils.isEmpty(username)) {
            usernameNotNull();
            return;
        }

        //密码不能为空
        if (TextUtils.isEmpty(password)) {
            passwordNotNull();
            return;
        }

        //用户名和密码都不能低于6位
        if (username.length() < 6 || password.length() < 6) {
            return;
        }

        //包装成登录信息
        LoginReq req = new LoginReq();
        req.setUsername(username);
        req.setPassword(password);
        req.setType(Constant.TYPE_LOGIN_NEW);

        //发送数据
        SendMessageUtil.sendMessage(req);

        showLoginProgressDialog();
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
        dismissLoginProgressDialog();
    }

    private ProgressDialog mLoginDialog;

    /**
     * 显示登录时的对话框
     */
    private void showLoginProgressDialog() {
        if (mLoginDialog == null) {
            mLoginDialog = new ProgressDialog(mActivityBinding.getRoot().getContext());
            mLoginDialog.setMessage("正在登录...");
            mLoginDialog.setCanceledOnTouchOutside(false);
            mLoginDialog.setCancelable(false);
            mLoginDialog.show();
        }
    }

    /**
     * 隐藏登录时的对话框
     */
    private void dismissLoginProgressDialog() {
        if (mLoginDialog != null && mLoginDialog.isShowing()) {
            mLoginDialog.dismiss();
            mLoginDialog = null;
        }
    }
}