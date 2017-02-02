package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
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

    public static Handler handleMessage(Object object) {

        Handler handler = null;

        String printMsg = "null";

        if (object instanceof CreateGroupResp) {
            printMsg = "CreateGroupResp";
            handler = new CreateGroupHandler();
        } else if (object instanceof GroupInfoResp) {
            printMsg = "GroupInfoResp";
            handler = new GroupInfoHandler();
        } else if (object instanceof RegisterResp) {
            printMsg = "RegisterResp";
            handler = new RegisterHandler();
        } else if (object instanceof ReturnInfoResp) {
            printMsg = "ReturnInfoResp";
            handler = new ReturnMessageHandler();
        } else if (object instanceof UserInfoResp) {
            printMsg = "UserInfoResp";
            handler = new UserInfoHandler();
        } else if (object instanceof Message) {
            printMsg = "Message";
            handler = new MessageHandler();
        } else if (object instanceof UserIdsInGroupResp) {
            printMsg = "UserIdsInGroupResp";
            handler = new UserIdsInGroupHandler();
        }

        LoggerUtil.logger(Constant.TAG, VALUE + printMsg);

        return handler;
    }

}
