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
    public void onDestroy() {
        super.onDestroy();
        //当服务销毁时，关闭网络连接
        SendMessageUtil.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
