package edu.csuft.chentao.netty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by Chalmers on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

/**
 * Netty连接类
 */
public class NettyClient {

    private static Bootstrap sBootstrap = new Bootstrap();

    private static ScheduledExecutorService sExecutor = Executors
            .newScheduledThreadPool(1);

    /**
     * 初始化
     */
    public static void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        sBootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
//                        ch.pipeline().addLast(new IdleStateHandler(5, 5, 5));
                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                ClassResolvers.weakCachingConcurrentResolver(null)));

                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
//                        ch.pipeline().addLast(new MyIdleHandler());
                        ch.pipeline().addLast(new NettyClientHandler());
//                        ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,  4, 4, -8, 0));
//                        ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
//                        ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
//                        ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
//                        ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
//                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    /**
     * 连接
     *
     * @param host url
     * @param port 端口
     */
    public static void connection(final String host, final int port) {
        try {
            LoggerUtil.logger(Constant.TAG, "NettyClient->connection");
            ChannelFuture future = sBootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            sExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connection(host, port);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
