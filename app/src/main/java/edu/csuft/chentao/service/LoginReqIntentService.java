package edu.csuft.chentao.service;

import android.app.IntentService;
import android.content.Intent;

import edu.csuft.chentao.pojo.req.LoginReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

public class LoginReqIntentService extends IntentService {

    public LoginReqIntentService() {
        super("LoginReqIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        LoggerUtil.logger(Constant.TAG, "LoginReqIntentService-->发送登录消息");

        String username = SharedPrefUserInfoUtil.getUsername();
        String password = SharedPrefUserInfoUtil.getPassword();

        LoginReq req = new LoginReq();
        req.setUsername(username);
        req.setPassword(password);
        req.setType(Constant.TYPE_LOGIN_AUTO);

        LoggerUtil.logger(Constant.TAG, "LoginReq-->发送登录信息");

        SendMessageUtil.sendMessage(req);
    }


}
