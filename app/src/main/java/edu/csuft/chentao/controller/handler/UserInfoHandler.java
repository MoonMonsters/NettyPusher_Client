package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.service.SyncMessageService;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-22 12:14.
 * email:qxinhai@yeah.net
 */
class UserInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        UserInfoResp resp = (UserInfoResp) object;

        //判断uid是否为-1，如果为-1，表示登录失败，否则成功
        if (resp.getUserid() > 0) { //登录成功
            if (resp.getType() == Constant.TYPE_LOGIN_AUTO) {   //如果是自动登录类型
//                LoggerUtil.showToast(MyApplication.getInstance(), "登录成功");

                MyApplication.getInstance().startService(new Intent(MyApplication.getInstance(), SyncMessageService.class));

                //发送广播到MainActivity
            } else if (resp.getType() == Constant.TYPE_LOGIN_NEW
                    || resp.getType() == Constant.TYPE_LOGIN_USER_INFO) { //如果是重新登录类型
                /*
                如果是新登录或者获得其他用户信息类型，则要从服务端把该用户的所有数据都获取过来，保存
                 */
                //保存用户信息
                UserInfoDaoUtil.saveUserInfoFromHandler(resp);
                //保存用户头像
                UserHeadDaoUtil.saveUserHeadFromHandler(resp);

                if (resp.getType() == Constant.TYPE_LOGIN_NEW) {  //重新登录，需要发送广播
                    //把数据从Handler传递到ActivityLoginPresenter，由Presenter去处理是否登录成功，已经后续操作
                    EBToPreObject ebObj = new EBToPreObject(Constant.TAG_USER_LOGIN_PRESENTER, resp);
                    EventBus.getDefault().post(ebObj);
                }
                if (resp.getType() == Constant.TYPE_LOGIN_USER_INFO) {
                    LoggerUtil.logger(Constant.TAG, "UserInfoHandler->LOGIN_USER_INFO....接收到用户数据");
                    //向需要更新用户数据的Presenter中发送数据
                    EBToPreObject ebObj = new EBToPreObject(Constant.TAG_ACTIVITY_PRESENTER_UPDATE_USERINFO, resp);
                    EventBus.getDefault().post(ebObj);
                }
            }
        } else { //登录失败
            LoggerUtil.showToast(MyApplication.getInstance(), "登录失败");
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_LOGIN_PRESENTER_LOGIN_FAIL, null);
        }
    }
}