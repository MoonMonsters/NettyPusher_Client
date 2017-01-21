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
     * 虚拟机连接
     */
//    String CONNECTION_URL = "192.168.0.102";
    /**
     * Wifi连接
     */
    String CONNECTION_URL = "192.168.191.1";
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
    int TYPE_REGISTER_REPEAT_USERNAME = 0;
    /**
     * 注册成功
     */
    int TYPE_REGISTER_SUCCESS = 1;

    /**
     * 创建群成功
     */
    int TYPE_CREATE_GROUP_SUCCESS = 0;
    /**
     * 创建群失败
     */
    int TYPE_CREATE_GROUP_FAIL = 1;

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
     * 用户信息类型
     */
    int TYPE_LOGIN_USER_INFO = 2;

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
     * 注册广播
     */
    String ACTION_REGISTER = "edu.csuft.chentao.action.register";
    /**
     * 接收到群数据广播
     */
    String ACTION_GROUPS = "edu.csuft.chentao.action_groups";
    /**
     * 接收到Message数据广播
     */
    String ACTION_CHATTING_MESSAGE = "edu.csuft.chentao.action.chatting_message";

    /**
     * 在ChattingListFragment中的广播
     */
    String ACTION_CHATTING_LIST = "edu.csuft.chentao.action.chatting.list";
    /**
     * 返回消息的广播ReturnMessage
     */
    String ACTION_RETURN_MESSAGE = "edu.csuft.chentao.action.return.message";

    /**
     * 查看详细
     */
    String ACTION_GROUP_DATAIL = "edu.csuft.chentao.action.group.detail";
    /**
     * 是否登录成功
     */
    String IS_LOGIN_SUCCESS = "is_login_success";
    /**
     * 登录用户的id
     */
    String LOGIN_USER_ID = "login_user_id";

    /**
     * 调试输出值
     */
    String TAG = "logger_tag";

    /**
     * 消息类型为文字
     */
    int TYPE_MSG_TEXT = 0;
    /**
     * 消息类型为图片
     */
    int TYPE_MSG_IMAGE = 1;

    /**
     * 传递注册后的返回对象
     */
    String EXTRA_REGISTERRESP = "extra_registerresp";
    /**
     * 传递群id
     */
    String EXTRA_GROUP_ID = "extra_group_id";
    /**
     * 传递用户id
     */
    String EXTRA_USER_ID = "extra_user_id";

    /**
     * 传递Groups数据
     */
    String EXTRA_GROUPS = "extra_groups";
    /**
     * 传递ChattingMessage数据
     */
    String EXTRA_CHATTING_MESSAGE = "extra_chatting_message";
    /**
     * 传递位置
     */
    String EXTRA_GROUPSITEM = "extra_groupsitem";
    /**
     * 广播传递的数据类型
     */
    String EXTRA_MESSAGE_TYPE = "extra_message_type";

    /**
     * 图片详细信息
     */
    String EXTRA_IMAGE_DETAIL = "extra_image_detail";

    /**
     * 传递ReturnMessage对象
     */
    String EXTRA_RETURN_MESSAGE = "extra_return_message";

    /**
     * 位置
     */
    String EXTRA_POSITION = "extra_position";
    /**
     * 传递群中所有用户信息及其身份信息
     */
    String EXTRA_USER_IDS_CAPITAL_LIST = "extra_user_ids_capital_list";

    String EXTRA_USER_IDS_IN_GROUP = "extra_user_ids_in_group";
    /**
     * 在MessageActivity和Presenter通过Handler传递数据
     */
    int HANDLER_MESSAGE_CHATTING_MESSAGE = 0;

    /**
     * 在LoginActivity和Presenter之间传递数据，登录成功
     */
    int HANDLER_LOGIN_SUCCESS = 1;

    /**
     * 在RegisterActivity和Presenter之间传递数据
     */
    int HANDLER_REGISTER = 2;
    /**
     * 在GroupListFragment和Presenter之间传递数据
     */
    int HANDLER_GROUPLIST = 3;
    /**
     * 在ChattingListFragment和Presenter之间增加数据
     */
    int HANDLER_CHATTING_LIST_ADD = 4;
    /**
     * 重新加载数据
     */
    int HANDLER_CHATTING_LIST_DELETE = 5;
    /**
     * 刷新
     */
    int HANDLER_CHATTING_LIST_REFRESH = 6;
    /**
     * 传递ReturnMessage数据
     */
    int HANDLER_RETURN_MESSAGE = 7;
    /**
     * 刷新
     */
    int HANDLER_CHATTING_MESSAGE_REFRESH = 8;

    /**
     * 传递图片数据，在MessageActivity和Presenter之间
     */
    int HANDLER_MESSAGE_CHATTING_MESSAGE_IMAGE = 9;
    /**
     * 传递图片数据，在EditorInfoActivity和Presenter之间
     */
    int HANDLER_RETURN_MESSAGE_IMAGE = 10;

    /**
     * 在GroupDetailActivity和Presenter之间通过Handler传递数据
     */
    int HANDLER_GROUP_DETAIL = 11;

    /**
     * 用户名字符串
     */
    String STRING_USER_NAME = "用户名: ";

    /*
    选取图片后返回
     */
    /**
     * 图片
     */
    String IMAGE_TYPE = "image/*";
    /**
     * 资源代码，这里的IMAGE_CODE是自己任意定义的
     */
    int IMAGE_CODE = 0;

    /*
     * 在群里的身份信息
	 */
    /**
     * 群主
     */
    int TYPE_GROUP_CAPITAL_OWNER = 0;
    String OWNER = "群主";
    /**
     * 管理员
     */
    int TYPE_GROUP_CAPITAL_ADMIN = 1;
    String ADMIN = "管理员";
    /**
     * 普通用户
     */
    int TYPE_GROUP_CAPITAL_USER = 2;
    String USER = "普通成员";
    /*
     * 请求的数类型
	 */
    /**
     * 请求用户信息
     */
    int TYPE_USER_GROUP_INFO_USER = 0;
    /**
     * 请求群信息
     */
    int TYPE_USER_GROUP_INFO_GROUP = 1;
}