package edu.csuft.chentao.controller.handler;


import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-01-18 16:53.
 * email:qxinhai@yeah.net
 */

public class UserIdsInGroupHandler implements Handler {
    @Override
    public void handle(Object object) {
        UserIdsInGroupResp resp = (UserIdsInGroupResp) object;

        LoggerUtil.logger(Constant.TAG, String.valueOf(resp.getGroupId()));

        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_GROUP_DATAIL);
        intent.putExtra(Constant.EXTRA_USER_IDS_IN_GROUP, resp);
        MyApplication.getInstance().sendBroadcast(intent);
    }
}
