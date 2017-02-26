package edu.csuft.chentao.controller.handler;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
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

        //发送消息到ActivityMessagePresenter
        EBToPreObject ebObj = new EBToPreObject(Constant.TAG_ADD_CHATTING_MESSAGE, chattingMessage);
        EventBus.getDefault().post(ebObj);

//        Intent intent = new Intent();
//        intent.setAction(Constant.ACTION_CHATTING_MESSAGE);
//        intent.putExtra(Constant.EXTRA_CHATTING_MESSAGE, chattingMessage);
//        MyApplication.getInstance().sendBroadcast(intent);
    }
}