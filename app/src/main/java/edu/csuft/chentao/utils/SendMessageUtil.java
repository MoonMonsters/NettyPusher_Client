package edu.csuft.chentao.utils;

import edu.csuft.chentao.netty.NettyClient;
import io.netty.channel.Channel;

/**
 * Created by csuft.chentao on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

/**
 * 联网操作
 */
public class SendMessageUtil {

    private static Channel mChannel;
    private static NettyClient mClient = null;

    /**
     * 初始化Netty
     */
    public static synchronized void initNettyClient() {

        LoggerUtil.logger(Constant.TAG, "SendMessageUtil.initNettyClient");

        mClient = new NettyClient();
        mChannel = mClient.connect(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mClient = new NettyClient();
//                    mClient.init();
//
//                    mChannel = mClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    /**
     * 发送数据
     *
     * @param object 待发送数据对象
     */
    public static synchronized void sendMessage(final Object object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mChannel.isActive()) {
                        mChannel.writeAndFlush(object);
                        mChannel.read();
                    }
                } catch (Exception e) {

                    synchronized (this) {
                        if (mChannel == null || !mChannel.isActive()) {
                            mChannel = mClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
                        }
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
     * 关闭网络连接
     */
    public static void close() {
        try {
            mChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}