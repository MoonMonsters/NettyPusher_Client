package edu.csuft.chentao.controller.handler;

import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

class ReturnInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        ReturnInfoResp resp = (ReturnInfoResp) object;

        LoggerUtil.logger("TAG", "ReturnInfoHandler-->" + resp.toString());

        switch (resp.getType()) {
            //如果是创建群相关的返回消息
            case Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS:
            case Constant.TYPE_RETURN_INFO_CREATE_GROUP_FAIL:
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_CREATE_GROUP_PRESENTER, resp);
                break;
            //更新用户数据的返回消息
            case Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS:
            case Constant.TYPE_RETURN_INFO_UPDATE_SIGNATURE_SUCESS:
            case Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_SUCCESS:
            case Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL:
            case Constant.TYPE_RETURN_INFO_UPDATE_SIGNATURE_FAIL:
            case Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_FAIL:
            case Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_SUCCESS:
            case Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_FAIL:
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_UPDATE_USER_INFO, resp);
                break;
            //更新用户身份
            case Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS:
            case Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL:
                //管理员对用户的身份信息进行更改，接收更改后的信息，并返回到ActivityGroupDetailPresenter中去
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_REFRESH_USER_CAPITAL, resp);
                break;
            case Constant.TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0:
                //搜索到的群的数量为0，传递数据到ActivitySearchGroupPresenter中去
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER_SIZE_0, resp);
                break;
            //管理员把用户踢出去成功，提示管理员
            case Constant.TYPE_RETURN_INFO_REMOVE_USER_SUCCESS:
                /*
                直接把数据发送到ActivityGroupDetailPresenter类中去，把用户踢出群后的返回信息
                */
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER, resp);
                break;
            //删除文件成功
            case Constant.TYPE_RETURN_INFO_REMOVE_FILE_SUCCESS:
                /*
                发送到ActivityFilePresenter类中去处理
                 */
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_FILE_PRESENTER_REMOVE_FILE, resp);
                break;
            case Constant.TYPE_RETURN_INFO_SEND_MESSAGE_SUCCESS:    //消息发送成功，返回标志
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_MESSAGE_PRESENTER_SEND_SUCCESS, resp);
                break;
            //退出群失败，弹出提示框
            case Constant.TYPE_RETURN_INFO_EXIT_GROUP_FAIL:
                //管理员把用户踢出去失败，提示管理员
            case Constant.TYPE_RETURN_INFO_REMOVE_USER_FAIL:
                //申请加入群，但群不存在，提示用户
            case Constant.TYPE_RETURN_INFO_GROUP_NOT_EXIST:
                //申请加入群，但用户已经在群里存在，提示用户
            case Constant.TYPE_RETURN_INFO_GROUP_MUL_USER:
                //邀请用户加入群，但用户id错误
            case Constant.TYPE_RETURN_INFO_ERROR_USERID:
                //邀请用户加入群，邀请操作成功
            case Constant.TYPE_RETURN_INFO_INVITE_SUCCESS:
                //邀请用户加入群，但用户已经在群里存在
            case Constant.TYPE_RETURN_INFO_INVITE_REPEAT:
                //删除文件失败
            case Constant.TYPE_RETURN_INFO_REMOVE_FILE_FAIL:
                Toast.makeText(MyApplication.getInstance(), (String) resp.getObj(), Toast.LENGTH_SHORT).show();
                break;
            case Constant.TYPE_RETURN_INFO_CLIENT_EXIT:
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_MAIN_PRESENTER_EXIT_LOGIN, resp);
                break;
            case Constant.TYPE_RETURN_INFO_SYNC_COMPLETE:
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_GROUP_SETTING_PRESENTER_SYNC_COMPLETE, resp);
                break;
        }
    }
}