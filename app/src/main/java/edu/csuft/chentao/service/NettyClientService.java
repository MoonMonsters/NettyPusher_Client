package edu.csuft.chentao.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;

public class NettyClientService extends Service {
    public NettyClientService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.logger(Constant.TAG, "NettyClientService-->启动服务，发送连接消息");
        //初始化网络连接
        SendMessageUtil.initNettyClient();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
