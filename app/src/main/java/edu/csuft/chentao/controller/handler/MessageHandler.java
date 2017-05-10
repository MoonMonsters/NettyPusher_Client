package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.ui.view.MessageNotification;
import edu.csuft.chentao.utils.CopyUtil;

/**
 * Created by Chalmers on 2016-12-22 14:22.
 * email:qxinhai@yeah.net
 */

class MessageHandler implements Handler {
    @Override
    public void handle(Object object) {

        Message message = (Message) object;

        CopyUtil.saveMessageReqToChattingMessage(message);
        MessageNotification messageNotification
                = new MessageNotification(MyApplication.getInstance());
        messageNotification.show(message);
    }
}