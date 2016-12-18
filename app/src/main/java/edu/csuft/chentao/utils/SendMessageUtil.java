package edu.csuft.chentao.utils;

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

    /**
     * 初始化Netty
     */
    public static void initNettyClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NettyClient client = new NettyClient();
                    client.init();

                    mChannel = client.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);

                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerUtil.logger("SendMessageUtil", "没有网络");
//                    Toast.makeText(MyApplication.getInstance(), "没有网络", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    /**
     * 发送数据
     *
     * @param object 待发送数据对象
     */
    public static void sendMessage(final Object object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mChannel.writeAndFlush(object);
                mChannel.read();
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
