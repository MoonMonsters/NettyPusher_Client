package edu.csuft.chentao.utils;

import android.text.TextUtils;

import edu.csuft.chentao.netty.NettyClient;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.LoginReq;
import io.netty.channel.Channel;

/**
 * Created by csuft.chentao on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

/**
 * 联网操作
 */
public class SendMessageUtil {

    public static Channel sChannel;

    /**
     * 初始化Netty
     */
    public static void initNettyClient() {

        LoggerUtil.logger(Constant.TAG, "SendMessageUtil.initNettyClient");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NettyClient.init();
                    NettyClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发送数据
     *
     * @param object 待发送数据对象
     */
    public static synchronized void sendMessage(final Object object) {
        LoggerUtil.logger(Constant.TAG, "SendMessageUtil->发送消息");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sChannel.isActive()) {
                        sChannel.writeAndFlush(object);
                        sChannel.read();
                    }
                } catch (Exception e) {

                    synchronized (this) {
                        NettyClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        sendMessage(object);
                    }
                }
            }
        }).start();
    }

    /**
     * 发送登录信息
     */
    public static void sendLoginReq() {
        LoggerUtil.logger(Constant.TAG, "SendMessageUtil->发送登录消息");
        String username = SharedPrefUserInfoUtil.getUsername();
        String password = SharedPrefUserInfoUtil.getPassword();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            LoginReq req = new LoginReq();
            req.setType(SharedPrefUserInfoUtil.getLoginType() ==
                    Constant.TYPE_LOGIN_AUTO ?
                    Constant.TYPE_LOGIN_AUTO :
                    Constant.TYPE_LOGIN_NEW);
            req.setUsername(username);
            req.setPassword(password);
            sendMessage(req);
        }
    }

    /**
     * 退出登录时，发送消息告知服务器
     */
    public static void sendUnLoginReq(int userId) {
        LoggerUtil.logger("ct.chentao", "SendMessageUtil当前线程是-->" + Thread.currentThread());
        LoggerUtil.logger(Constant.TAG, "SendMessageUtil->发送退出登录消息");
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_UNLOGIN);
        req.setArg1(userId);
        LoggerUtil.logger("ct.chentao", "SendMessageUtil.sendUnLoginReq-->userId = " + userId);
        sendMessage(req);
    }
}