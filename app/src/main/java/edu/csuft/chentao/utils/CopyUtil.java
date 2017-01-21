package edu.csuft.chentao.utils;

import java.util.List;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
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
        ChattingMessage chattingMessage = new ChattingMessage();

        chattingMessage.setImage(message.getPicFile());
        chattingMessage.setTime(message.getTime());
        chattingMessage.setUserid(message.getUserid());
        chattingMessage.setGroupid(message.getGroupid());
        chattingMessage.setMessage(message.getMessage());
        chattingMessage.setType(message.getType());
        chattingMessage.setTypemsg(message.getTypeMsg());

        String nickname = UserInfoDaoUtil.getUserInfo(message.getUserid()).getNickname();

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

            //发送添加广播
            OperationUtil.sendBroadcastToAddGroupChattingItem(chattingItem);
        } else {
            GroupChattingItem chattingItem = chattingItemList.get(0);
            chattingItem.setLastmessage(lastMessage);
            chattingItem.setNumber(chattingItem.getNumber() + 1);
            GroupChattingItemDaoUtil.updateGroupChattingItem(chattingItem);

            //发送更新广播
            OperationUtil.sendBroadcastToUpdateGroupChattingItem(chattingItem);
        }
    }
}