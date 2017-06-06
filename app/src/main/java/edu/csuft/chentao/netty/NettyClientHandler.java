package edu.csuft.chentao.netty;

import edu.csuft.chentao.controller.handler.AllMessageHandler;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by Chalmers on 2016-12-16 15:20.
 * email:qxinhai@yeah.net
 */

class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

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

        //保存连接网络时的时间
        SharedPrefUserInfoUtil.saveStartActiveTime();
        isSaveEndTime = true;

        OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_MAIN_PRESENTER_CONNECTION, null);
    }

    //只需要保存第一次断开网络事件即可
    private boolean isSaveEndTime = true;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //保存断开连接时的时间
        if (isSaveEndTime) {
            SharedPrefUserInfoUtil.saveEndInactiveTime();
            isSaveEndTime = false;
        }

        //重新连接
        NettyClient.connection(Constant.CONNECTION_URL, Constant.CONNECTION_PORT);
        LoggerUtil.logger(Constant.TAG, VALUE + "channelInactive");
        OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_MAIN_PRESENTER_NO_CONNECTION, null);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoggerUtil.logger(Constant.TAG, VALUE + "channelRead");
        //处理消息
        AllMessageHandler.handleMessage(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
//        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;

            if (e.state() == IdleState.READER_IDLE) {
                LoggerUtil.logger("NettyClientHandler", "--- Reader Idle ---");
//
                ctx.writeAndFlush(String.valueOf("Client Reader Idle"));

                // ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                LoggerUtil.logger("NettyClientHandler", "--- Write Idle ---");
//                LoggerUtil.logger(Constant.TAG, "--- Write Idle ---");

                ctx.writeAndFlush(String.valueOf("Client Write Idle"));
                // ctx.close();
            } else if (e.state() == IdleState.ALL_IDLE) {
                LoggerUtil.logger("NettyClientHandler", "--- All_IDLE ---");
//                System.out.println("--- All_IDLE ---");
//
                ctx.writeAndFlush(String.valueOf("Client All Idle"));
            }
        }
    }
}
