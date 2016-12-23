package edu.csuft.chentao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import edu.csuft.chentao.base.MyApplication;

/**
 * Created by Chalmers on 2016-12-22 08:39.
 * email:qxinhai@yeah.net
 */

public class SharedPrefUtil {

    private static final String SHARED_PREF_NAME = "NettyPusher_shared_pref";
    private static final String TYPE_LOGIN = "type_login";

    private static final String USER_ID = "user_id";

    private static SharedPreferences mSharedPreferences = MyApplication.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
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

}
