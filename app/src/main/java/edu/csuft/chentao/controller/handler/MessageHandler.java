package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 14:22.
 * email:qxinhai@yeah.net
 */

class MessageHandler implements Handler {
    @Override
    public void handle(Object object) {

        LoggerUtil.logger(Constant.TAG, "MessageHandler接收到到消息->" + object.toString());

        Message message = (Message) object;
        ChattingMessage chattingMessage = CopyUtil.saveMessageReqToChattingMessage(message);

        LoggerUtil.logger(Constant.TAG, "MessageHandler->保存到了本地，发送广播");

        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_CHATTING_MESSAGE);
        intent.putExtra(Constant.EXTRA_CHATTING_MESSAGE, chattingMessage);
        MyApplication.getInstance().sendBroadcast(intent);
    }
}