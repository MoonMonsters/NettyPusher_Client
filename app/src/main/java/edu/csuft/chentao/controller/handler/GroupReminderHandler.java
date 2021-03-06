package edu.csuft.chentao.controller.handler;

/**
 * Created by Chalmers on 2017-02-07 15:33.
 * email:qxinhai@yeah.net
 */

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.ui.view.MessageNotification;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * 处理操作群返回的消息
 */
class GroupReminderHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupReminderResp resp = (GroupReminderResp) object;
        //转换成合适的数据类型，并保存到数据表中
        Hint hint = CopyUtil.saveHintFromGroupReminder(resp);
        EBToPreObject ebObj = new EBToPreObject(Constant.TAG_ACTIVITY_HINT_PRESENTER, hint);
        //发送数据
        EventBus.getDefault().post(ebObj);

        /*
         * 如果接收到的GroupReminderResp是移除群类型，那么发送广播到群列表和聊天列表中，移除对应项
         */
        if (resp.getType() == Constant.TYPE_GROUP_REMINDER_EXIT_BY_MYSELF
                || resp.getType() == Constant.TYPE_GROUP_REMINDER_REMOVE_USER) {
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_REMOVE_GROUPS, resp);
        }

        sendNotification(hint);
    }

    private void sendNotification(Hint hint) {
        MessageNotification notification = new MessageNotification(MyApplication.getInstance());
        notification.show(hint);
    }

}