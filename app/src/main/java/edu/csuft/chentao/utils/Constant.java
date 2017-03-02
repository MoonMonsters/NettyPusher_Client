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
//    String CONNECTION_URL = "169.254.187.72";
    /**
     * Wifi连接
     */
//    String CONNECTION_URL = "192.168.191.3";
    /**
     * 外网连接
     */
    String CONNECTION_URL = "119.29.143.22";
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
    int TYPE_RETURN_INFO_SUCCESS = 0;
    /**
     * 失败
     */
    int TYPE_RETURN_INFO_FAIL = 1;
    /**
     * 创建群成功
     */
    int TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS = 2;
    /**
     * 创建群失败
     */
    int TYPE_RETURN_INFO_CREATE_GROUP_FAIL = 3;
    /**
     * 更新头像成功
     */
    int TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS = 4;
    /**
     * 更新头像失败
     */
    int TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_FAIL = 5;
    /**
     * 更新签名成功
     */
    int TYPE_RETURN_INFO_UPDATE_SIGNATURE_SUCESS = 6;
    /**
     * 更新签名失败
     */
    int TYPE_RETURN_INFO_UPDATE_SIGNATURE_FAIL = 7;
    /**
     * 更新昵称成功
     */
    int TYPE_RETURN_INFO_UPDATE_NICKNAME_SUCCESS = 8;
    /**
     * 更新昵称失败
     */
    int TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL = 9;

    /**
     * 更新用户身份成功
     */
    int TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS = 10;
    /**
     * 更新用户身份失败
     */
    int TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL = 11;
    /**
     * 搜索群时，数据个数为0
     */
    int TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0 = 12;
    /**
     * 退出群失败
     */
    int TYPE_RETURN_INFO_EXIT_GROUP_FAIL = 13;
    /**
     * 管理员把用户踢出群成功
     */
    int TYPE_RETURN_INFO_REMOVE_USER_SUCCESS = 14;
    /**
     * 管理员把用户踢出群失败
     */
    int TYPE_RETURN_INFO_REMOVE_USER_FAIL = 15;
    /**
     * 申请加入群，群不存在
     */
    int TYPE_RETURN_INFO_GROUP_NOT_EXIST = 16;
    /**
     * 申请加入群，用户已经在群中存在
     */
    int TYPE_RETURN_INFO_GROUP_MUL_USER = 17;
    /**
     * 错误的用户id
     */
    int TYPE_RETURN_INFO_ERROR_USERID = 18;
    /**
     * 邀请用户加入群，邀请成功
     */
    int TYPE_RETURN_INFO_INVITE_SUCCESS = 19;
    /**
     * 邀请用户时，用户已经在群里，重复邀请
     */
    int TYPE_RETURN_INFO_INVITE_REPEAT = 20;
    /**
     * 获取用户信息
     */
    int TYPE_GET_INFO_USERINFO = 0;
    /**
     * 退出登录
     */
    int TYPE_GET_INFO_UNLOGIN = 1;
    /**
     * 根据群id搜索
     */
    int TYPE_GET_INFO_SEARCH_GROUP_ID = 2;
    /**
     * 根据群名搜索
     */
    int TYPE_GET_INFO_SEARCH_GROUP_NAME = 3;
    /**
     * 根据标签搜索
     */
    int TYPE_GET_INFO_SEARCH_GROUP_TAG = 4;

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
    String ACTION_RETURN_INFO = "edu.csuft.chentao.action.return.info";

    /**
     * 修改用户数据的返回值
     */
    String ACTION_RETURN_INFO_USER_CAPITAL = "edu.csuft.chentao.action.return.info.user.capital";

    /**
     * 查看详细
     */
    String ACTION_GROUP_DATAIL = "edu.csuft.chentao.action.group.detail";

    /**
     * 创建群界面
     */
    String ACTION_CREATE_GROUP = "edu.csuft.chentao.action.create.group";

    /**
     * 获得成功用户信息
     */
    String ACTION_GET_USERINFO = "edu.csuft.chentao.action.get.userinfo";
    /**
     * 搜索群数据时，接收到广播
     */
    String ACTION_SEARCH_GROUP = "edu.csuft.chentao.action.search.group";
    /**
     * 移除掉群，退出或者被踢出
     */
    String ACTION_REMOVE_GROUP = "edu.csuft.chentao.action.remove.group";

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
    String EXTRA_RETURN_INFO = "extra_return_info";

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
    int HANDLER_RETURN_INFO = 7;
    /**
     * 刷新
     */
    int HANDLER_PRESENTER_REFRESH = 8;
    /**
     * 刷新用户信息
     */
    int HANDLER_PRESENTER_REFRESH_USERINFO = 9;
    /**
     * 刷新用户身份值
     */
    int HANDLER_PRESENTER_REFRESH_CAPITAL = 10;

    /**
     * 传递图片数据，在MessageActivity和Presenter之间
     */
    int HANDLER_MESSAGE_CHATTING_MESSAGE_IMAGE = 9;
    /**
     * 传递图片数据，在EditorInfoActivity和Presenter之间
     */
    int HANDLER_RETURN_INFO_IMAGE = 10;
    /**
     * 搜索到的数据为0
     */
    int HANDLER_SEARCH_GROUP_SIZE_0 = 11;
    /**
     * 移除掉群
     */
    int HANDLER_REMOVE_GROUP = 12;

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
    /**
     * 用户拥有的群
     */
    int TYPE_GROUP_INFO_OWNER = 0;
    /**
     * 用户搜索时获取的群
     */
    int TYPE_GROUP_INFO_SEARCH = 1;

    /*
     * 群操作
	 */
    /**
     * 自己退出群
     */
    int TYPE_GROUP_OPERATION_EXIT_BY_MYSELF = 1;
    /**
     * 被管理员踢出群
     */
    int TYPE_GROUP_OPERATION_EXIT_BY_ADMIN = 2;
    /**
     * 自己加入群
     */
    int TYPE_GROUP_OPERATION_ADD_BY_MYSELF = 3;
    /**
     * 被邀请加入群
     */
    int TYPE_GROUP_OPERATION_ADD_BY_INVITE = 4;
    /**
     * 同意加入群
     */
    int TYPE_GROUP_OPERATION_AGREE_ADD_GROUP = 5;
    /**
     * 拒绝加入群
     */
    int TYPE_GROUP_OPERATION_REFUSE_ADD_GROUP = 6;

    /*
     * 群消息相应
     */
    //1.退出群
    int TYPE_GROUP_REMINDER_EXIT_BY_MYSELF = 0;
    //2.踢出群
    int TYPE_GROUP_REMINDER_REMOVE_USER = 1;
    //3.加入群
    int TYPE_GROUP_REMINDER_ADD_GROUP = 2;
    //4.邀请入群
    int TYPE_GROUP_REMINDER_INVITE_GROUP = 3;
    //5.拒绝用户加入群
    int TYPE_GROUP_REMINDER_REFUSE_ADD_GROUP = 4;
    //6.同意用户加入群
    int TYPE_GROUP_REMINDER_AGREE_ADD_GROUP = 5;
    //7.某用户申请加入群
    int TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP = 6;

    /**
     * 不显示Button也不显示TextView
     */
    int TYPE_HINT_SHOW_NONE = 0;
    /**
     * 显示已经同意的TextView
     */
    int TYPE_HINT_SHOW_AGREE = 1;
    /**
     * 显示已经拒绝的TextView
     */
    int TYPE_HINT_SHOW_REFUSE = 2;
    /**
     * 显示同意或者拒绝的Button
     */
    int TYPE_HINT_SHOW_AGREE_REFUSE_BUTTON = 3;

    /**
     * 在对话框的标题显示类型
     */
    /**
     * 邀请用户
     */
    int TYPE_INVITE_TITLE_AND_HINT_USER = 1;
    /**
     * 加入群
     */
    int TYPE_INVITE_TITLE_AND_HINT_GROUP = 2;

    /**
     * ActivityGroupDetailPresenter
     */
    String TAG_ACTIVITY_GROUP_DETAIL_PRESENTER = "TAGActivityGroupDetailPresenter";
    /**
     * Handler向ActivityGroupDetailPresenter发送UserIdsInGroupResp数据
     */
    String TAG_ACTIVITY_GROUP_DETAIL_PRESENTER2 = "TAGActivityGroupDetailPresenter2";
    /**
     * 向GroupDetailActivity界面添加UserInfo数据
     */
    String TAG_ACTIVITY_GROUP_DETAIL_PRESENTER_ADD_USER = "tag.activity.group.detail.presenter.add.user";

    /**
     * Handler向FragmentGroupListPresenter中传递Groups数据
     */
    String TAG_FRAGMENT_GROUP_LIST_PRESENTER = "TAGFragmentGroupListPresenter";
    /**
     * Handler向FragmentGroupListPresenter中传递GroupInfoResp数据
     */
    String TAG_ACTIVITY_SEARCH_GROUP_PRESENTER = "TAGActivitySearchGroupPresenter";
    /**
     * 当搜索到的群信息为0时
     */
    String TAG_ACTIVITY_SEARCH_GROUP_PRESENTER_SIZE_0 = "TAGActivitySearchGroupPresenter.size.0";
    /**
     * Handler向ActivityHintPresenter中传递Hint数据
     */
    String TAG_ACTIVITY_HINT_PRESENTER = "TAGActivityHintPresenter";

//    String HANDLER_ACTIVITY_EDITOR_INFO_Presenter = "HandlerActivityEditor";

    /**
     * 当接收到该EB数据时，把群移除掉，即退出了群，删除聊天列表的数据和群数据
     */
    String TAG_REMOVE_GROUPS = "tag.remove.groups";
    /**
     * 向聊天列表更新数据项
     */
    String TAG_FRAGMENT_CHATTING_LIST_PRESENTER_UPDATE_ITEM = "tag.fragment.chatting.list.presenter.update.item";
    /**
     * 向聊天列表添加数据项
     */
    String TAG_FRAGMENT_CHATTING_LIST_PRESENTER_ADD_ITEM = "tag.fragment.chatting.list.presenter.add.item";
    /**
     * 移除聊天列表的数据项
     */
    String TAG_FRAGMENT_CHATTING_LIST_PRESENTER_REMOVE_ITEM = "tag.fragment.chatting.list.presenter.remove.item";
    /**
     * 在MessageActivity界面添加聊天数据
     */
    String TAG_ADD_CHATTING_MESSAGE = "tag.add.chatting.message";

    /**
     * RegisterHandler向ActivityRegisterPresenter传递RegisterResp数据
     */
    String TAG_REGISTER_PRESENTER = "tag.register.presenter";
    /**
     * 创建群的返回信息，从Handler中传递到ActivityCreateGroupPresenter中去
     */
    String TAG_CREATE_GROUP_PRESENTER = "tag.create.group.presenter";
    /**
     * 更新用户信息，从Handler传递数据到ActivityEditorInfoPresenter
     */
    String TAG_UPDATE_USER_INFO = "tag.update.user.info";
    /**
     * 管理员更改用户的身份信息，从Handler传递数据到ActivityGroupDetailPresenter
     */
    String TAG_REFRESH_USER_CAPITAL = "tag.refresh.user.capital";
    /**
     * 用户登录是否成功，把数据从Handler发送到ActivityLoginPresenter中去
     */
    String TAG_USER_LOGIN_PRESENTER = "tag.user.login.presenter";

    /**
     * 从CreateGroupActivity传递Image数据到Presenter中去
     */
    String IMAGE_ACTIVITY_CREATE_GROUP_PRESENTER = "image.activity.create.group.presenter";
    /**
     * 更新头像，从EditorInfoActivity把Image数据传递到Presenter中去
     */
    String IMAGE_ACTIVITY_EDITOR_INFO_PRESENTER = "image.activity.editor.info.presenter";
    /**
     * 发送图片，把图片从MessageActivity发送到Presenter
     */
    String IMAGE_ACTIVITY_MESSAGE_PRESENTER = "image.activity.message.presenter";
    /**
     * 选择头像，把图片从RegisterActivity发送到Presenter
     */
    String IMAGE_ACTIVITY_REGISTER_PRESENTER = "image.activity.register.presenter";
    /**
     * 选择头像，把头像发送到CutViewActivity
     */
    String IMAGE_ACTIVITY_CUT_VIEW_PRESENTER = "image.activity.cut.view.presenter";
    /**
     * 将图片数据发送RegisterActivity
     */
    String CUT_VIEW_REGISTER_ACTIVITY = "cut.view.register.activity";
    /**
     * 将图片数据发送到EditorInfoActivity
     */
    String CUT_VIEW_EDITOR_INFO_PRESENTER = "cut.view.editor.info.presenter";
    /**
     * 将图片发送到CreateGroupActivity中去
     */
    String CUT_VIEW_CREATE_GROUP_ACTIVITY = "cut.view.create.group.activity";
    /**
     * 传递需要CutView图片类型
     */
    String EXTRA_CUT_VIEW = "extra.cut.view";
}