package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-22 12:14.
 * email:qxinhai@yeah.net
 */
public class UserInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        UserInfoResp resp = (UserInfoResp) object;

        //判断uid是否为-1，如果为-1，表示登录失败，否则成功
        if (resp.getUserid() > 0) { //登录成功
            if (resp.getType() == Constant.TYPE_LOGIN_AUTO) {   //如果是自动登录类型
                /*
                如果是自动登录类型，表示用户的信息都已经保存过，不需要重新获取
                 */
                //发送广播到MainActivity
                sendBroadcast(true, 0);
            } else if (resp.getType() == Constant.TYPE_LOGIN_NEW
                    || resp.getType() == Constant.TYPE_LOGIN_USER_INFO) { //如果是重新登录类型

                /*
                如果是重新登录类型或者获得其他用户信息类型，则要从服务端把该用户的所有数据都获取过来，保存
                 */
                //保存用户信息
                UserInfoDaoUtil.saveUserInfoFromHandler(resp);
                //保存用户头像
                UserHeadDaoUtil.saveUserHeadFromHandler(resp);

                if (resp.getType() == Constant.TYPE_LOGIN_NEW) {  //重新登录，需要发送广播
                    //发送广播，表示登录成功
                    sendBroadcast(true, resp.getUserid());
                }
            }
        } else { //登录失败
            sendBroadcast(false, -1);
        }
    }

    /**
     * 发送广播
     *
     * @param isSuccess 是否登录成功
     */
    private void sendBroadcast(boolean isSuccess, int userId) {
        Intent intent = new Intent();
        //添加广播
        intent.setAction(Constant.ACTION_LOGIN);
        //发送成功标志
        intent.putExtra(Constant.IS_LOGIN_SUCCESS, isSuccess);
        //用户id
        intent.putExtra(Constant.LOGIN_USER_ID, userId);
        MyApplication.getInstance().sendBroadcast(intent);
    }
}
