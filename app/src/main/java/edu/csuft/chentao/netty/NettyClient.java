package edu.csuft.chentao.netty;

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

public class NettyClient {

    private Bootstrap mBootstrap;

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
                                ClassResolvers.weakCachingConcurrentResolver(null)));
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new NettyClientHandler());
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
}
