package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

public class ReturnMessageHandler implements Handler {
    @Override
    public void handle(Object object) {
        ReturnMessageResp resp = (ReturnMessageResp) object;
        OperationUtil.sendBroadcastToUpdateUserInfo(resp);
    }
}