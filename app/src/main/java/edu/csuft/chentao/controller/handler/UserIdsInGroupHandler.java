package edu.csuft.chentao.controller.handler;


import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-01-18 16:53.
 * email:qxinhai@yeah.net
 */

class UserIdsInGroupHandler implements Handler {
    @Override
    public void handle(Object object) {
        UserIdsInGroupResp resp = (UserIdsInGroupResp) object;
        LoggerUtil.logger(Constant.TAG, String.valueOf(resp.getGroupId()));
        EBToPreObject ebObj = new EBToPreObject(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER2, resp);
        EventBus.getDefault().post(ebObj);
    }
}
