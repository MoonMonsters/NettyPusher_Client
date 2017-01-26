package edu.csuft.chentao.controller.handler;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 12:12.
 * email:qxinhai@yeah.net
 */

public class GroupInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupInfoResp resp = (GroupInfoResp) object;
        final Groups groups = CopyUtil.saveGroupInfoToGroups(resp);

        LoggerUtil.logger(Constant.TAG, "接收到GroupInfoResp数据");

        EventBus.getDefault().post(groups);

    }
}