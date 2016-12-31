package edu.csuft.chentao.utils;

import java.util.List;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
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

}
