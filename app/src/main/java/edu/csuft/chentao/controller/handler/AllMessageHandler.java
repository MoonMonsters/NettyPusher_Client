package edu.csuft.chentao.controller.handler;

/**
 * Created by Chalmers on 2016-12-22 12:08.
 * email:qxinhai@yeah.net
 */

/**
 * 使用了简单工厂模式
 */
public class AllMessageHandler {

    /**
     * 处理消息
     *
     * @param object 消息对象
     */
    public static void handleMessage(Object object) {

        Handler handler = MessageHandlerFactory.getMessageHandler(object);

        handler.handle(object);
    }
}
