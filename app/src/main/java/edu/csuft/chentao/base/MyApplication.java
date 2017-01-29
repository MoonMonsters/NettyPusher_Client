package edu.csuft.chentao.base;

import android.app.Application;
import android.content.Intent;

import edu.csuft.chentao.service.NettyClientService;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-16 15:39.
 * email:qxinhai@yeah.net
 */

public class MyApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        LoggerUtil.logger(Constant.TAG, "MyApplication.onCreate");
        application = this;
        startService(new Intent(this, NettyClientService.class));
    }

    public static Application getInstance() {
        return application;
    }
}
