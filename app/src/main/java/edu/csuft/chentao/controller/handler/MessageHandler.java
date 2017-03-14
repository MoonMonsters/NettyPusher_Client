package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

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

        OperationUtil.sendEBToObjectPresenter(Constant.TAG_ADD_CHATTING_MESSAGE, chattingMessage);
    }
}