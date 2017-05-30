package edu.csuft.chentao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import edu.csuft.chentao.base.MyApplication;

/**
 * Created by Chalmers on 2016-12-22 08:39.
 * email:qxinhai@yeah.net
 */

public class SharedPrefUserInfoUtil {

    private static final String SHARED_PREF_NAME = "NettyPusher_shared_pref";
    /**
     * 登录方式
     */
    private static final String TYPE_LOGIN = "type_login";
    /**
     * 用户id
     */
    private static final String USER_ID = "user_id";
    /**
     * 用户名
     */
    private static final String USERNAME = "username";
    /**
     * 密码
     */
    private static final String PASSWORD = "password";
    /**
     * 是否有新的提示信息暂未阅读
     */
    private static final String HINT_NEW = "hint_new";

    /**
     * 连接网络时的时间
     */
    private static final String START_ACTIVE_TIME = "start_active_time";
    /**
     * 断开网络时的时间
     */
    private static final String END_INACTIVE_TIME = "end_inactive_time";
    /**
     * 网络是否连接状态
     */
    private static final String IS_CONNECT = "is_connect";

    private static SharedPreferences mSharedPreferences = MyApplication.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_MULTI_PROCESS);
    private static SharedPreferences.Editor mEditor = mSharedPreferences.edit();

    /**
     * 获得登录方式，有两种，一种是自动登录，一种是重新登录
     *
     * @return 登录方式
     */
    public static int getLoginType() {

        return mSharedPreferences.getInt(TYPE_LOGIN, Constant.TYPE_LOGIN_NEW);
    }

    /**
     * 在登录过一次后，之后便是重新登录
     */
    public static void setLoginType() {
        mEditor.putInt(TYPE_LOGIN, Constant.TYPE_LOGIN_AUTO);
        mEditor.apply();
    }

    /**
     * 设置userId
     *
     * @param userId 用户id
     */
    public static void setUserId(int userId) {

        LoggerUtil.logger("CHENTAO", "SharedPreUserInfoUtil-->userId = " + userId);

        mEditor.putInt(USER_ID, userId);
        mEditor.apply();
    }

    /**
     * 获得用户id
     *
     * @return 用户id
     */
    public static int getUserId() {

        return mSharedPreferences.getInt(USER_ID, -1);
    }

    /**
     * 保存用户名和密码
     *
     * @param username 用户名
     * @param password 密码
     */
    public static void setUsernameAndPassword(String username, String password) {
        mEditor.putString(USERNAME, username);
        mEditor.putString(PASSWORD, password);
        mEditor.apply();
    }

    /**
     * 获得用户名
     *
     * @return 用户名
     */
    public static String getUsername() {

        return mSharedPreferences.getString(USERNAME, null);
    }

    /**
     * 保存消息，是否有新的提示信息
     */
    public static void setHintNew(boolean isNew) {
        mEditor.putBoolean(HINT_NEW, isNew);
        mEditor.apply();
    }

    /**
     * 获得是否有新的提示消息没有阅读
     */
    public static boolean getHintNew() {

        return mSharedPreferences.getBoolean(HINT_NEW, false);
    }

    /**
     * 获得密码
     *
     * @return 密码
     */
    public static String getPassword() {

        return mSharedPreferences.getString(PASSWORD, null);
    }

    /**
     * 清空保存的用户信息
     */
    public static void clearUserInfo() {
        mEditor.clear();
        mEditor.apply();
    }

    /**
     * 保存登录时的时间
     */
    public static void saveStartActiveTime() {
        String time = OperationUtil.getCurrentTime();
        mEditor.putString(START_ACTIVE_TIME, time);
    }

    /**
     * 获得登录时的时间
     */
    public static String getStartActiveTime() {
        return mSharedPreferences.getString(START_ACTIVE_TIME, null);
    }

    /**
     * 保存断开时的时间
     */
    public static void saveEndInactiveTime() {
        String time = OperationUtil.getCurrentTime();
        mEditor.putString(END_INACTIVE_TIME, time);
    }

    /**
     * 得到断开时的时间
     */
    public static String getEndInactiveTime() {
        return mSharedPreferences.getString(END_INACTIVE_TIME, null);
    }

    /**
     * 保存网络状态
     */
    public synchronized static void saveNetStatus(boolean isConnect) {
        mEditor.putBoolean(IS_CONNECT, isConnect);
        mEditor.apply();
    }

    /**
     * 得到网络连接状态
     */
    public static boolean getNetStatus() {
        return mSharedPreferences.getBoolean(IS_CONNECT, false);
    }

}