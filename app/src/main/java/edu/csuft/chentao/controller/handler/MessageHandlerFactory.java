package edu.csuft.chentao.controller.handler;

import java.util.HashMap;
import java.util.Map;

import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by csuft.chentao on 2017/3/14.
 */

class MessageHandlerFactory {
    private static final String VALUE = "AllMessageHandler.handlerMessage->";

    /**
     * 用来存储Handler对象，提高存取速度
     */
    private static final Map<MessageHandlerFactory.EHandler, Handler> handlerMap = new HashMap<>();

    /**
     * 根据收到的数据类型，返回对应的Handler对象
     *
     * @param object 数据类型对象
     * @return Handler对象
     */
    static Handler getMessageHandler(Object object) {

        Handler handler = null;

        //得到object对应的type值
        EHandler type = getHandlerType(object);

        //判断type对应的Handler对象是否存在，存在则直接取出，节省内容，提高速度
        if (handlerMap.get(type) != null) {
            handler = handlerMap.get(type);

            //从Map中取出Handler对象后，直接返回
            return handler;
        }

        /*
        没有Handler对象，则重新创建
         */
        if (object instanceof CreateGroupResp) {
            handler = new CreateGroupHandler();
        } else if (object instanceof GroupInfoResp) {
            handler = new GroupInfoHandler();
        } else if (object instanceof RegisterResp) {
            handler = new RegisterHandler();
        } else if (object instanceof ReturnInfoResp) {
            handler = new ReturnInfoHandler();
        } else if (object instanceof UserInfoResp) {
            handler = new UserInfoHandler();
        } else if (object instanceof Message) {
            handler = new MessageHandler();
        } else if (object instanceof UserIdsInGroupResp) {
            handler = new UserIdsInGroupHandler();
        } else if (object instanceof GroupReminderResp) {
            handler = new GroupReminderHandler();
        } else if (object instanceof Announcement) {
            handler = new AnnouncementHandler();
        }

        //将Handler对象放入Map中
        handlerMap.put(type, handler);

        LoggerUtil.logger(Constant.TAG, VALUE + type);

        return handler;
    }

    /**
     * 根据object类型，获得type值
     *
     * @param object 对象
     * @return type值
     */
    private static EHandler getHandlerType(Object object) {
        EHandler type = EHandler.NONEOBJECT;

        if (object instanceof CreateGroupResp) {
            type = EHandler.CreateGroupResp;
        } else if (object instanceof GroupInfoResp) {
            type = EHandler.GroupInfoResp;
        } else if (object instanceof RegisterResp) {
            type = EHandler.RegisterResp;
        } else if (object instanceof ReturnInfoResp) {
            type = EHandler.ReturnInfoResp;
        } else if (object instanceof UserInfoResp) {
            type = EHandler.UserInfoResp;
        } else if (object instanceof Message) {
            type = EHandler.Message;
        } else if (object instanceof UserIdsInGroupResp) {
            type = EHandler.UserIdsInGroupResp;
        } else if (object instanceof GroupReminderResp) {
            type = EHandler.GroupReminderResp;
        } else if (object instanceof Announcement) {
            type = EHandler.Announcement;
        }

        return type;
    }

    /**
     * enum
     */
    private enum EHandler {
        NONEOBJECT,
        CreateGroupResp,
        GroupInfoResp,
        RegisterResp,
        ReturnInfoResp,
        UserInfoResp,
        Message,
        UserIdsInGroupResp,
        GroupReminderResp,
        Announcement
    }
}
