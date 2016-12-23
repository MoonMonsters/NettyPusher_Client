package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.req.HeadImage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.req.PicFile;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 12:08.
 * email:qxinhai@yeah.net
 */

public class AllMessageHandler {

    public static Handler handleMessage(Object object) {

        LoggerUtil.logger("Handler", object.toString());

        Handler handler = null;

        if (object instanceof CreateGroupResp) {
            handler = new CreateGroupHandler();
        } else if (object instanceof GroupInfoResp) {
            handler = new GroupInfoHandler();
        } else if (object instanceof RegisterResp) {
            handler = new RegisterHandler();
        } else if (object instanceof ReturnMessageResp) {
            handler = new ReturnMessageHandler();
        } else if (object instanceof UserInfoResp) {
            handler = new UserInfoHandler();
        } else if (object instanceof HeadImage) {
            handler = new HeadImageHandler();
        } else if (object instanceof PicFile) {
            handler = new PicFileHandler();
        } else if (object instanceof Message) {
            handler = new MessageHandler();
        }

        return handler;
    }

}
