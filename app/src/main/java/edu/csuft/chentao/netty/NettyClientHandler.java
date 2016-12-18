package edu.csuft.chentao.netty;

import edu.csuft.chentao.utils.LoggerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Chalmers on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    private String TAG = "NettyClientHandler";

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerUtil.logger(TAG,"messageReceived");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LoggerUtil.logger(TAG,"channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LoggerUtil.logger(TAG,"channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        LoggerUtil.logger(TAG,"channelRead");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LoggerUtil.logger(TAG,"exceptionCaught");
    }
}
