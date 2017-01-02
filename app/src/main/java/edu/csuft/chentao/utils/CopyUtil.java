package edu.csuft.chentao.utils;

import android.content.Intent;

import java.util.List;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;

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

        DaoSessionUtil.saveChattingMessage(chattingMessage);

        int groupId = chattingMessage.getGroupid();
        String lastMessage = chattingMessage.getTypemsg() == Constant.TYPE_MSG_TEXT ? chattingMessage.getMessage()
                : "[图片]";
        saveChattingListItemData(groupId, lastMessage);


        return chattingMessage;
    }

    public static Groups saveGroupInfoToGroups(GroupInfoResp resp) {
        Groups groups = new Groups();
        groups.setGroupid(resp.getGroupid());
        groups.setGroupname(resp.getGroupname());
        groups.setImage(resp.getHeadImage());
        groups.setNumber(resp.getNumber());
        groups.setTag(resp.getTag());
        DaoSessionUtil.getGroupsDao().insert(groups);

        return groups;
    }

    public static Groups updateGroupInfoToGroups(GroupInfoResp resp, List<Groups> groupsList) {
        Groups groups = groupsList.get(0);
        groups.setGroupname(resp.getGroupname());
        groups.setImage(resp.getHeadImage());
        groups.setNumber(resp.getNumber());
        groups.setTag(resp.getTag());
        DaoSessionUtil.getGroupsDao().update(groups);

        return groups;
    }

    private static void saveChattingListItemData(int groupId, String lastMessage) {
        GroupChattingItem chattingItem = DaoSessionUtil.getGroupChattingItemDao().queryBuilder()
                .where(GroupChattingItemDao.Properties.Groupid.eq(groupId))
                .list().get(0);
        if (chattingItem == null) {
            Groups groups = DaoSessionUtil.getGroupsDao().queryBuilder()
                    .where(GroupsDao.Properties.Groupid.eq(groupId))
                    .build().list().get(0);
            chattingItem = new GroupChattingItem();
            chattingItem.setGroupid(groupId);
            chattingItem.setLastmessage(lastMessage);
            chattingItem.setImage(groups.getImage());
            chattingItem.setGroupname(groups.getGroupname());
            DaoSessionUtil.getGroupChattingItemDao().insert(chattingItem);
        } else {
            chattingItem.setLastmessage(lastMessage);
            DaoSessionUtil.getGroupChattingItemDao().update(chattingItem);
        }

        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_CHATTING_LIST);
        intent.putExtra(Constant.EXTRA_GROUPID, groupId);
        intent.putExtra(Constant.EXTRA_LAST_MESSAGE, lastMessage);
        MyApplication.getInstance().sendBroadcast(intent);
    }

}
