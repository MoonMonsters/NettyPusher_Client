package edu.csuft.chentao.controller.handler;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

public class ReturnInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        ReturnInfoResp resp = (ReturnInfoResp) object;
        //如果是创建群相关的返回消息
        if (resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS
                || resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_FAIL) {

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_CREATE_GROUP_PRESENTER, resp);
            //更新用户数据的返回消息
        } else if (resp.getType() >= Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS
                && resp.getType() <= Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL) {
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_UPDATE_USER_INFO, resp);
            //更新用户身份
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS ||
                resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL) {
            //管理员对用户的身份信息进行更改，接收更改后的信息，并返回到ActivityGroupDetailPresenter中去
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_REFRESH_USER_CAPITAL, resp);
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0) {
            //搜索到的群的数量为0，传递数据到ActivitySearchGroupPresenter中去
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER_SIZE_0, resp);
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_EXIT_GROUP_FAIL) {   //退出群失败，弹出提示框
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_REMOVE_USER_SUCCESS) {   //管理员把用户踢出去成功，提示管理员
            /*
            直接把数据发送到ActivityGroupDetailPresenter类中去，把用户踢出群后的返回信息
             */
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER, resp);
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_REMOVE_USER_FAIL) {  //管理员把用户踢出去失败，提示管理员
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_GROUP_NOT_EXIST) {   //申请加入群，但群不存在，提示用户
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_GROUP_MUL_USER) {    //申请加入群，但用户已经在群里存在，提示用户
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_ERROR_USERID) {  //邀请用户加入群，但用户id错误
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_INVITE_SUCCESS) {    //邀请用户加入群，邀请操作成功
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_INVITE_REPEAT) { //邀请用户加入群，但用户已经在群里存在
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }
}