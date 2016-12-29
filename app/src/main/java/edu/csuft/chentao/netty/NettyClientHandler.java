package edu.csuft.chentao.netty;

import edu.csuft.chentao.controller.handler.AllMessageHandler;
import edu.csuft.chentao.controller.handler.Handler;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Chalmers on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {


    private String VALUE = "NettyClientHandler-->";

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerUtil.logger(Constant.TAG, VALUE + "messageReceived");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LoggerUtil.logger(Constant.TAG, VALUE + "channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        LoggerUtil.logger(Constant.TAG, VALUE + "channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        LoggerUtil.logger(Constant.TAG, VALUE + "channelRead");
        //获得对应对象
        Handler handler = AllMessageHandler.handleMessage(msg);
        //处理消息
        handler.handle(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LoggerUtil.logger(Constant.TAG, VALUE + "exceptionCaught");
    }
}
