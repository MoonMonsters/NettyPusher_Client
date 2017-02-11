package edu.csuft.chentao.controller.handler;

/**
 * Created by Chalmers on 2017-02-07 15:33.
 * email:qxinhai@yeah.net
 */

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * 处理操作群返回的消息
 */
class GroupReminderHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupReminderResp resp = (GroupReminderResp) object;
        LoggerUtil.logger(Constant.TAG, resp.toString());
        EventBus.getDefault().post(resp);
    }
}