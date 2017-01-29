/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.csuft.chentao.netty;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Lilinfeng
 * @date 2014年3月15日
 * @version 1.0
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
		LoggerUtil.logger(Constant.TAG,"HeartBeatReqHandler->channelRead");
	NettyMessage message = (NettyMessage) msg;
	// 握手成功，主动发送心跳消息
	if (message.getHeader() != null
		&& message.getHeader().getType() == MessageType.LOGIN_RESP
			.value()) {
	    heartBeat = ctx.executor().scheduleAtFixedRate(
		    new HeartBeatTask(ctx), 0, 5000,
		    TimeUnit.MILLISECONDS);
	} else if (message.getHeader() != null
		&& message.getHeader().getType() == MessageType.HEARTBEAT_RESP
			.value()) {
	    LoggerUtil.logger(Constant.TAG,"Client receive server heart beat message : ---> "
			    + message);
	} else
	    ctx.fireChannelRead(msg);
    }

    private class HeartBeatTask implements Runnable {
	private final ChannelHandlerContext ctx;

	public HeartBeatTask(final ChannelHandlerContext ctx) {
	    this.ctx = ctx;
	}

	@Override
	public void run() {
	    NettyMessage heatBeat = buildHeatBeat();
		LoggerUtil.logger(Constant.TAG,"Client send heart beat messsage to server : ---> "
			    + heatBeat);
	    ctx.writeAndFlush(heatBeat);
	}

	private NettyMessage buildHeatBeat() {
		LoggerUtil.logger(Constant.TAG,"HeartBeatReqHandler->buildHeatBeat");
	    NettyMessage message = new NettyMessage();
	    Header header = new Header();
	    header.setType(MessageType.HEARTBEAT_REQ.value());
	    message.setHeader(header);
	    return message;
	}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
		LoggerUtil.logger(Constant.TAG,"HeartBeatReqHandler->exception");
	cause.printStackTrace();
	if (heartBeat != null) {
	    heartBeat.cancel(true);
	    heartBeat = null;
	}
	ctx.fireExceptionCaught(cause);
    }
}