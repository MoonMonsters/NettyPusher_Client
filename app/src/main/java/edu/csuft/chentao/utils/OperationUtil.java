package edu.csuft.chentao.utils;

import android.content.Intent;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;

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
     * 更新用户信息
     */
    public static void sendBroadcastToUpdateUserInfo(ReturnInfoResp resp) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_RETURN_INFO);
        intent.putExtra(Constant.EXTRA_RETURN_INFO, resp);
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

    /**
     * 把偶才能的图片的文件名称
     */
    public static String getImageName() {
        String str = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(str.charAt((int) (Math.random() * str.length())));
        }

        return sb.toString() + ".jpg";
    }

    /**
     * 获得身份信息
     *
     * @param capital 身份值
     */
    public static String getCapital(int capital) {
        String result = null;

        switch (capital) {
            case Constant.TYPE_GROUP_CAPITAL_ADMIN:
                result = Constant.ADMIN;
                break;
            case Constant.TYPE_GROUP_CAPITAL_OWNER:
                result = Constant.OWNER;
                break;
            case Constant.TYPE_GROUP_CAPITAL_USER:
                result = Constant.USER;
                break;
        }

        return result;
    }

    /**
     * 根据身份获得背景颜色
     *
     * @param capital 身份值
     */
    public static int getCapitalBackgroundColor(int capital) {
        int result = -1;

        switch (capital) {
            case Constant.TYPE_GROUP_CAPITAL_ADMIN:
                result = android.R.color.holo_blue_light;
                break;
            case Constant.TYPE_GROUP_CAPITAL_OWNER:
                result = android.R.color.holo_red_light;
                break;
            case Constant.TYPE_GROUP_CAPITAL_USER:
                result = android.R.color.darker_gray;
                break;
        }

        return result;
    }

    /**
     * 发送消息请求用户数据
     *
     * @param userId 用户id
     */
    public static void getUserInfoFromServerByUserId(int userId) {
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_USERINFO);
        req.setArg1(userId);
        SendMessageUtil.sendMessage(req);
    }



}
