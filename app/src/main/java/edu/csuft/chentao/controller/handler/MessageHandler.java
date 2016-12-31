package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;

/**
 * Created by Chalmers on 2016-12-22 14:22.
 * email:qxinhai@yeah.net
 */

public class MessageHandler implements Handler {
    @Override
    public void handle(Object object) {

        Message message = (Message) object;
        ChattingMessage chattingMessage = CopyUtil.saveMessageReqToChattingMessage(message);

        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_CHATTING_MESSAGE);
        intent.putExtra(Constant.EXTRA_CHATTING_MESSAGE, chattingMessage);
        MyApplication.getInstance().sendBroadcast(intent);
    }
}