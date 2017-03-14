package edu.csuft.chentao.controller.handler;

import java.util.HashMap;
import java.util.Map;

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
 * Created by Chalmers on 2016-12-22 12:08.
 * email:qxinhai@yeah.net
 */

public class AllMessageHandler {

    private static final String VALUE = "AllMessageHandler.handlerMessage->";

    private static Map<EHandler, Handler> handlerMap = new HashMap<>();

    /**
     * 根据收到的数据类型，返回对应的Handler对象
     *
     * @param object 数据类型对象
     * @return Handler对象
     */
    public static Handler handleMessage(Object object) {

        Handler handler = null;

        EHandler type = getHandlerType(object);

        if (handlerMap.get(type) != null) {
            handler = handlerMap.get(type);
        } else if (object instanceof CreateGroupResp) {
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
        }

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
        }

        return type;
    }

    private enum EHandler {
        NONEOBJECT,
        CreateGroupResp,
        GroupInfoResp,
        RegisterResp,
        ReturnInfoResp,
        UserInfoResp,
        Message,
        UserIdsInGroupResp,
        GroupReminderResp
    }
}
