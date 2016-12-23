package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-22 12:14.
 * email:qxinhai@yeah.net
 */

public class UserInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        UserInfoResp resp = (UserInfoResp) object;
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_LOGIN);
        //判断uid是否为-1，如果为-1，表示登录失败，否则成功
        if (resp.getUserid() > 0) { //登录成功
            if (resp.getType() == Constant.TYPE_LOGIN_AUTO) {

            } else if (resp.getType() == Constant.TYPE_LOGIN_NEW) {

            }

            //发送成功标志
            intent.putExtra(Constant.IS_LOGIN_SUCCESS, true);

        } else { //登录失败
            intent.putExtra(Constant.IS_LOGIN_SUCCESS, false);
        }
        MyApplication.getInstance().sendBroadcast(intent);

    }
}
