package edu.csuft.chentao.utils;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
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
        //向FragmentChattingListPresenter发送数据，更新数据项

    }

    /**
     * 发送广播去添加GroupChattingItem独享
     */
    public static void sendBroadcastToAddGroupChattingItem(GroupChattingItem chattingItem) {
        LoggerUtil.logger("FragmentChattingListPresenter", Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_ADD_ITEM + "sendBroadcastToAddGroupChattingItem");
        //传递对象到FragmentChattingListPresenter中去，添加数据项

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

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 拼凑数量和标签
     *
     * @param resp GroupInfoResp对象
     * @return 拼凑的字符串
     */
    public static String getTagAndNumber(GroupInfoResp resp) {

        return resp.getNumber() + "人/" + resp.getTag();
    }

    /**
     * 根据类型的值，返回对应的String值
     */
    public static String getTextWithHintType(int type) {
        String result = null;
        if (type == Constant.TYPE_HINT_SHOW_AGREE) {
            result = "已同意";
        } else if (type == Constant.TYPE_HINT_SHOW_REFUSE) {
            result = "已拒绝";
        }

        return result;
    }

    /**
     * 将bitmap转化成byte[]数组
     *
     * @param bm Bitmap对象
     * @return 转化后的byte[]数组
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 发送EBToObjectPresenter对象
     *
     * @param tag    标志
     * @param object 实例对象
     */
    public static void sendEBToObjectPresenter(String tag, Object object) {
        EBToPreObject ebObj = new EBToPreObject(tag, object);
        EventBus.getDefault().post(ebObj);
    }

    /**
     * 发送ImageDetail对象
     *
     * @param tag 标志
     * @param buf 图片的byte[]数组
     */
    public static void sendImageDetail(String tag, byte[] buf) {
        ImageDetail imageDetail = new ImageDetail(tag, buf);
        EventBus.getDefault().post(imageDetail);
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return 是否创建成功
     */
    private static boolean createDirectory(String path) {
        boolean isExist = true;
        File file = new File(path);
        if (!file.exists()) {
            //创建
            isExist = file.mkdir();
        }

        return isExist;
    }

    /**
     * 创建文件
     *
     * @param path     文件夹路径
     * @param fileName 文件路径
     */
    public static File createFile(String path, String fileName) {

        File file = null;

        if (createDirectory(path)) {
            file = new File(path, fileName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 获得id对应的String内容
     */
    public static String getString(ViewDataBinding viewDataBinding, int stringId) {
        return viewDataBinding.getRoot().getContext().getString(stringId);
    }

}
