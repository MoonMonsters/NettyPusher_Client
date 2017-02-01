package edu.csuft.chentao.netty;

import edu.csuft.chentao.controller.handler.AllMessageHandler;
import edu.csuft.chentao.controller.handler.Handler;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
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
        //获取发送对象
        SendMessageUtil.sChannel = ctx.channel();
        //发送用户信息
        SendMessageUtil.sendLoginReq();
        LoggerUtil.logger(Constant.TAG, VALUE + "channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //重新连接
        NettyClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
        LoggerUtil.logger(Constant.TAG, VALUE + "channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerUtil.logger(Constant.TAG, VALUE + "channelRead");
        //获得对应对象
        Handler handler = AllMessageHandler.handleMessage(msg);
        //处理消息
        handler.handle(msg);
    }

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
//            throws Exception {
//        super.userEventTriggered(ctx, evt);
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent e = (IdleStateEvent) evt;
//
//            if (e.state() == IdleState.READER_IDLE) {
////                System.out.println("--- Reader Idle ---");
////
////                ctx.writeAndFlush(String.valueOf("Client Reader Idle"));
//
//                // ctx.close();
//            } else if (e.state() == IdleState.WRITER_IDLE) {
//                LoggerUtil.logger(Constant.TAG, "--- Write Idle ---");
//
//                ctx.writeAndFlush(String.valueOf("Client Write Idle"));
//                // ctx.close();
//            } else if (e.state() == IdleState.ALL_IDLE) {
////                System.out.println("--- All_IDLE ---");
////
////                ctx.writeAndFlush(String.valueOf("Client All Idle"));
//            }
//        }
//    }
}
