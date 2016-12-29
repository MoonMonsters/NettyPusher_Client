package edu.csuft.chentao.utils;

import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.netty.NettyClient;
import io.netty.channel.Channel;

/**
 * Created by Chalmers on 2016-12-16 15:20.
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
    public static void initNettyClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient = new NettyClient();
                    mClient.init();

                    mChannel = mClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static int count = 0;

    /**
     * 发送数据
     *
     * @param object 待发送数据对象
     */
    public static void sendMessage(final Object object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mChannel.isActive()) {
                    mChannel.writeAndFlush(object);
                    mChannel.read();
                    count = 0;
                } else {
                    if (count <= 7) {
                        initNettyClient();
                        count++;
                        sendMessage(object);
                    } else {
                        Toast.makeText(MyApplication.getInstance(), "网络没有连接", Toast.LENGTH_SHORT).show();
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
