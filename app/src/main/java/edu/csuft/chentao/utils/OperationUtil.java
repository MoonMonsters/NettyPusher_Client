package edu.csuft.chentao.utils;

import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;

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
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTime().toString();
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

}
