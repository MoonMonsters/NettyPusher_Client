package edu.csuft.chentao.controller.handler;

import android.content.Intent;
import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

public class ReturnInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        ReturnInfoResp resp = (ReturnInfoResp) object;
//        OperationUtil.sendBroadcastToUpdateUserInfo(resp);
        //如果是创建群相关的返回消息
        if (resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS
                || resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_FAIL) {
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_CREATE_GROUP);
            intent.putExtra(Constant.EXTRA_RETURN_INFO, resp);
            MyApplication.getInstance().sendBroadcast(intent);
            //更新数据的返回消息
        } else if (resp.getType() >= Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS
                && resp.getType() <= Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_FAIL) {
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_RETURN_INFO);
            intent.putExtra(Constant.EXTRA_RETURN_INFO, resp);
            MyApplication.getInstance().sendBroadcast(intent);
            //更新用户身份
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS ||
                resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_FAIL) {
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_RETURN_INFO_USER_CAPITAL);
            intent.putExtra(Constant.EXTRA_RETURN_INFO, resp);
            MyApplication.getInstance().sendBroadcast(intent);
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_SEARCH_GROUP_SIZE_0) {
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_SEARCH_GROUP);
            intent.putExtra(Constant.EXTRA_RETURN_INFO, resp);
            MyApplication.getInstance().sendBroadcast(intent);
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_EXIT_GROUP_FAIL) {   //退出群失败，弹出提示框
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        } else if (resp.getType() == Constant.TYPE_RETURN_INFO_REMOVE_USER_SUCCESS) {   //管理员把用户踢出去成功，提示管理员

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