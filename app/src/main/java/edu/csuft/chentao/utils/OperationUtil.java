package edu.csuft.chentao.utils;

import android.content.Intent;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.req.Message;

/**
 * Created by Chalmers on 2016-12-29 17:43.
 * email:qxinhai@yeah.net
 */

public class OperationUtil {

    /**
     * 得到当前时间
     *
     * @return 时间
     */
    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return DateFormat.format("yyyy'-'MM'-'dd' 'kk':'mm':'ss' '", cal).toString();
    }

    /**
     * 发送广播去更新聊天框数据
     *
     * @param chattingItem GroupChattingItem对象
     */
    public static void sendBroadcastToUpdateGroupChattingItem(GroupChattingItem chattingItem) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_CHATTING_LIST);
        intent.putExtra(Constant.EXTRA_MESSAGE_TYPE, 2);
        intent.putExtra(Constant.EXTRA_GROUPSITEM, chattingItem);
        MyApplication.getInstance().sendBroadcast(intent);
    }

    /**
     * 发送广播去添加GroupChattingItem独享
     */
    public static void sendBroadcastToAddGroupChattingItem(GroupChattingItem chattingItem) {
        //发送广播，通知数据发生改变
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_CHATTING_LIST);
        intent.putExtra(Constant.EXTRA_GROUPSITEM, chattingItem);
        intent.putExtra(Constant.EXTRA_MESSAGE_TYPE, 1);
        MyApplication.getInstance().sendBroadcast(intent);
    }

    /**
     * 发送消息
     *
     * @param groupId 群id
     * @param msgType 消息类型
     * @param content 文字内容
     * @param image   图片
     */
    public static Message sendChattingMessage(int groupId, int msgType,
                                              String content, byte[] image) {
        //发送数据，构造对象
        Message message = new Message();
        //消息类型，文字还是图片
        message.setTypeMsg(msgType);
        //发送类型
        message.setType(Constant.TYPE_MSG_SEND);
        message.setGroupid(groupId);
        //因为是文字类型，所以图片为空
        message.setPicFile(image);
        //文字内容
        message.setMessage(content);
        //用户id
        message.setUserid(SharedPrefUserInfoUtil.getUserId());
        //设置时间
        message.setTime(OperationUtil.getCurrentTime());

        //发送数据
        SendMessageUtil.sendMessage(message);

        return message;
    }

}
