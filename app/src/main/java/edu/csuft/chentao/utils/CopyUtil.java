package edu.csuft.chentao.utils;

import java.util.List;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 14:20.
 * email:qxinhai@yeah.net
 */

public class CopyUtil {

    /**
     * 将发送的内容转换成ChattingMessage对象并且保存
     */
    public static ChattingMessage saveMessageReqToChattingMessage(Message message) {
        LoggerUtil.logger(Constant.TAG, "Message转换成ChattingMessage");
        ChattingMessage chattingMessage = new ChattingMessage();

        chattingMessage.setImage(message.getPicFile());
        chattingMessage.setTime(message.getTime());
        chattingMessage.setUserid(message.getUserid());
        chattingMessage.setGroupid(message.getGroupid());
        chattingMessage.setMessage(message.getMessage());
        chattingMessage.setType(message.getType());
        chattingMessage.setTypemsg(message.getTypeMsg());

        String nickname = UserInfoDaoUtil.getUserInfo(message.getUserid()) == null ?
                null : UserInfoDaoUtil.getUserInfo(message.getUserid()).getNickname();

        //保存到本地
        ChattingMessageDaoUtil.saveChattingMessage(chattingMessage);

        int groupId = chattingMessage.getGroupid();
        String lastMessage = chattingMessage.getTypemsg() == Constant.TYPE_MSG_TEXT ? chattingMessage.getMessage()
                : "[图片]";
        saveChattingListItemData(groupId, nickname + ": " + lastMessage);

        return chattingMessage;
    }

    /**
     * 将GroupInfo数据转成Groups数据保存在本地
     */
    public static Groups saveGroupInfoToGroups(GroupInfoResp resp) {
        Groups groups = new Groups();
        groups.setGroupid(resp.getGroupid());
        groups.setGroupname(resp.getGroupname());
        groups.setImage(resp.getHeadImage());
        groups.setNumber(resp.getNumber());
        groups.setTag(resp.getTag());

        //保存到本地
        GroupsDaoUtil.saveGroups(groups);

        return groups;
    }

    /**
     * 保存Groups数据
     */
    public static Groups updateGroupInfoToGroups(GroupInfoResp resp, List<Groups> groupsList) {
        Groups groups = groupsList.get(0);
        groups.setGroupname(resp.getGroupname());
        groups.setImage(resp.getHeadImage());
        groups.setNumber(resp.getNumber());
        groups.setTag(resp.getTag());

        GroupsDaoUtil.updateGroups(groups);

        return groups;
    }

    /**
     * 保存ChattingGroupItem数据
     */
    private static synchronized void saveChattingListItemData(int groupId, String lastMessage) {
        List<GroupChattingItem> chattingItemList = GroupChattingItemDaoUtil.loadAllWithGroupId(groupId);
        if (chattingItemList.size() == 0) {
            Groups groups = GroupsDaoUtil.getGroups(groupId);
            GroupChattingItem chattingItem = new GroupChattingItem();
            chattingItem.setGroupid(groupId);
            chattingItem.setLastmessage(lastMessage);
            chattingItem.setImage(groups.getImage());
            chattingItem.setGroupname(groups.getGroupname());
            chattingItem.setNumber(1);
            GroupChattingItemDaoUtil.saveGroupChattingItem(chattingItem);

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_ADD_ITEM, chattingItem);
        } else {
            GroupChattingItem chattingItem = chattingItemList.get(0);
            chattingItem.setLastmessage(lastMessage);
            chattingItem.setNumber(chattingItem.getNumber() + 1);
            GroupChattingItemDaoUtil.updateGroupChattingItem(chattingItem);

            //TODO
            //发送更新广播
            OperationUtil.sendBroadcastToUpdateGroupChattingItem(chattingItem);
        }
    }

    /**
     * 将GroupReminderResp数据对象，转成Hint对象，并保存到数据库中
     */
    public static Hint saveHintFromGroupReminder(GroupReminderResp resp) {
        Hint hint = new Hint();

        hint.setGroupid(resp.getGroupId());
        hint.setImage(resp.getImage());
        hint.setDescription(resp.getDescription());
        hint.setGroupname(resp.getGroupName());
        hint.setType(resp.getType());
        hint.setUserid(resp.getUserId());

        int show = -1;
        //加入了群
        if (resp.getType() == Constant.TYPE_GROUP_REMINDER_ADD_GROUP) {
            show = Constant.TYPE_HINT_SHOW_NONE;
            //被管理员提出群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_REMOVE_USER) {
            show = Constant.TYPE_HINT_SHOW_NONE;
            //自己退出群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_EXIT_BY_MYSELF) {
            show = Constant.TYPE_HINT_SHOW_NONE;
            //邀请自己入群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_INVITE_GROUP) {
            show = Constant.TYPE_HINT_SHOW_AGREE_REFUSE_BUTTON;
            //被管理员拒绝加入群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_REFUSE_ADD_GROUP) {
            show = Constant.TYPE_HINT_SHOW_NONE;
            //管理员同意加入群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_AGREE_ADD_GROUP) {
            show = Constant.TYPE_HINT_SHOW_NONE;
            //用户申请加入群
        } else if (resp.getType() == Constant.TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP) {
            show = Constant.TYPE_HINT_SHOW_AGREE_REFUSE_BUTTON;
        }

        hint.setShow(show);

        HintDaoUtil.insert(hint);

        return hint;
    }
}