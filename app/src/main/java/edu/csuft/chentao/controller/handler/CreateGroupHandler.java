package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 12:11.
 * email:qxinhai@yeah.net
 */

class CreateGroupHandler implements Handler {
    @Override
    public void handle(Object object) {
        CreateGroupResp resp = (CreateGroupResp) object;
        LoggerUtil.showToast(MyApplication.getInstance(), resp.getDescription());
    }
}
