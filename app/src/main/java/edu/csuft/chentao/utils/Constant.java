/**
 * 常量类
 */
package edu.csuft.chentao.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author csuft.chentao
 *         <p>
 *         2016年12月10日 上午10:22:54
 */
public interface Constant {

    /**
     * 服务端地址
     */
    String CONNECTION_URL = "192.168.0.102";
    /**
     * 服务端端口
     */
    int CONNECTION_PORT = 10101;

    /**
     * 图片缓存文件夹
     */
    String PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "NettyPusher_imageCache";
    /**
     * 图片名称
     */
    String SPLASH_IMAGE_NAME = "splash.jpg";
    /**
     * 默认用户id值，从10000开始
     */
    int DEFAULT_USERID = 10000;
    /**
     * 默认群id从20000开始
     */
    int DEFAULT_GROUPID = 100000;

    /**
     * 注册失败，用户名重复
     */
    int REGISTER_TYPE_REPEAT_USERNAME = 0;
    /**
     * 注册成功
     */
    int REGISTER_TYPE_SUCCESS = 1;

    /**
     * 创建群成功
     */
    int CREATE_GROUP_SUCCESS = 0;
    /**
     * 创建群失败
     */
    int CREATE_GROUP_FAIL = 1;

    /**
     * 加入群
     */
    int TYPE_GROUP_ENTER = 0;
    /**
     * 退出群
     */
    int TYPE_GROUP_EXIT = 1;

    /**
     * 成功
     */
    int TYPE_RETURN_MESSAGE_SUCCESS = 0;
    /**
     * 失败
     */
    int TYPE_RETURN_MESSAGE_FAIL = 3;

    /**
     * 更新昵称
     */
    int TYPE_UPDATE_NICKNAME = 0;
    /**
     * 更新签名
     */
    int TYPE_UPDATE_SIGNATURE = 1;
    /**
     * 更新头像
     */
    int TYPE_UPDATE_HEADIMAGE = 2;

    /**
     * 自动登录
     */
    int TYPE_LOGIN_AUTO = 0;
    /**
     * 新的登录
     */
    int TYPE_LOGIN_NEW = 1;

    /**
     * 发送消息
     */
    int TYPE_MSG_SEND = 0;
    /**
     * 接收消息
     */
    int TYPE_MSG_RECV = 1;

    /**
     * 登录广播
     */
    String ACTION_LOGIN = "edu.csuft.chentao.action.login";
    /**
     * 是否登录成功
     */
    String IS_LOGIN_SUCCESS = "is_login_success";

}
