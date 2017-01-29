package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

public class ReturnMessageHandler implements Handler {
    @Override
    public void handle(Object object) {
        ReturnMessageResp resp = (ReturnMessageResp) object;
//        OperationUtil.sendBroadcastToUpdateUserInfo(resp);
        //如果是创建群相关的返回消息
        if (resp.getType() == Constant.TYPE_RETURN_MESSAGE_CREATE_GROUP_SUCCESS
                || resp.getType() == Constant.TYPE_RETURN_MESSAGE_CREATE_GROUP_FAIL) {
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_CREATE_GROUP);
            intent.putExtra(Constant.EXTRA_RETURN_MESSAGE, resp);
            MyApplication.getInstance().sendBroadcast(intent);
            //更新数据的返回消息
        }else if(resp.getType()>=Constant.TYPE_RETURN_MESSAGE_UPDATE_HEAD_IMAGE_SUCCESS
                &&resp.getType()<=Constant.TYPE_RETURN_MESSAGE_UPDATE_NICKNAME_FAIL){
            Intent intent = new Intent();
            intent.setAction(Constant.ACTION_RETURN_MESSAGE);
            intent.putExtra(Constant.EXTRA_RETURN_MESSAGE,resp);
            MyApplication.getInstance().sendBroadcast(intent);
        }
    }
}