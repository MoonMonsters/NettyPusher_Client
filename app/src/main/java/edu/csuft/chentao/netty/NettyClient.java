package edu.csuft.chentao.netty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.csuft.chentao.utils.Constant;
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
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by Chalmers on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

public class NettyClient {

    private Bootstrap mBootstrap;

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);

    /**
     * 初始化
     */
    public void init() {
        EventLoopGroup group = new NioEventLoopGroup();
        mBootstrap = new Bootstrap();
        mBootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                ClassResolvers.weakCachingConcurrentResolver(null)))
                                .addLast(new ObjectEncoder())
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new NettyClientHandler())
                                .addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
                                .addLast("MessageEncoder",
                                        new NettyMessageEncoder())
                                .addLast("readTimeoutHandler",
                                        new ReadTimeoutHandler(50))
                                .addLast("LoginAuthHandler",
                                        new LoginAuthReqHandler())
                                .addLast("HeartBeatHandler",
                                        new HeartBeatReqHandler())
                        ;
                    }
                });
    }

    /**
     * 连接
     *
     * @param host url
     * @param port 端口
     */
    public Channel connection(String host, int port) {
        ChannelFuture future = null;
        Channel channel = null;
        try {
            future = mBootstrap.connect(host, port).sync();
            channel = future.sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return channel;
    }

    public Channel connect(String host, int port) {
        Channel channel = null;
        Bootstrap bootstrap = null;

        try {
            EventLoopGroup group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.weakCachingConcurrentResolver(null)))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new NettyClientHandler())
                                    .addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
                                    .addLast("MessageEncoder",
                                            new NettyMessageEncoder())
                                    .addLast("readTimeoutHandler",
                                            new ReadTimeoutHandler(50))
                                    .addLast("LoginAuthHandler",
                                            new LoginAuthReqHandler())
                                    .addLast("HeartBeatHandler",
                                            new HeartBeatReqHandler())
                            ;
                        }
                    });
            ChannelFuture future = bootstrap.connect(
                    Constant.CONNECTION_URL, Constant.CONNECTION_PORT
            ).sync();
            channel = future.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {

        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);// 发起重连操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return channel;
    }
}
