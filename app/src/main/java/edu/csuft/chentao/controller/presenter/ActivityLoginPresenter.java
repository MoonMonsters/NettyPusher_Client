package edu.csuft.chentao.controller.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.csuft.chentao.activity.LoginActivity;
import edu.csuft.chentao.activity.MainActivity;
import edu.csuft.chentao.activity.RegisterActivity;
import edu.csuft.chentao.databinding.ActivityLoginBinding;
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

    public ActivityLoginPresenter(ActivityLoginBinding binding) {
        this.mBinding = binding;
        mContext = mBinding.getRoot().getContext();
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

    /**
     * 登录类型广播
     */
    public static class LoginReceiver extends BroadcastReceiver {

        private LoginActivity mActivity;

        public LoginReceiver(LoginActivity activity) {
            this.mActivity = activity;
        }

        private void enterMainActivity() {
            Intent intent = new Intent(mActivity, MainActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) { //匹配广播
                //得到是否登录成功数据
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                if (isSuccess) { //登录成功
                    ActivityLoginBinding binding = (ActivityLoginBinding) mActivity.getActivityBinding();
                    String username = binding.etLoginUsername.getText().toString();
                    String password = binding.etLoginPassword.getText().toString();
                    //保存用户名和密码
                    SharedPrefUserInfoUtil.setUsernameAndPassword(username, password);

                    //保存用户的id
                    int userId = intent.getIntExtra(Constant.LOGIN_USER_ID, -1);
                    SharedPrefUserInfoUtil.setUserId(userId);

                    //将登录类型设置成自动登录类型
                    SharedPrefUserInfoUtil.setLoginType();

                    Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
                    enterMainActivity();

                } else {  //登录失败
                    Toast.makeText(mActivity, "登录失败，请确认账号和密码后重新登录", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
