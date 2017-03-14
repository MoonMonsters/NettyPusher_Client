package edu.csuft.chentao.controller.handler;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-22 12:13.
 * email:qxinhai@yeah.net
 */

public class RegisterHandler implements Handler {
    @Override
    public void handle(Object object) {
        RegisterResp resp = (RegisterResp) object;

        LoggerUtil.logger(Constant.TAG, "RegisterHandler.handle->" + resp.toString());

        if (resp.getType() == Constant.TYPE_REGISTER_SUCCESS) { //注册成功

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_REGISTER_PRESENTER, resp);
        } else if (resp.getType() == Constant.TYPE_REGISTER_REPEAT_USERNAME) {  //用户名已存在
            Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }
}