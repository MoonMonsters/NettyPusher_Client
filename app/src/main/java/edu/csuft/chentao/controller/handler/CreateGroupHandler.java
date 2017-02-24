package edu.csuft.chentao.controller.handler;

import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.resp.CreateGroupResp;

/**
 * Created by Chalmers on 2016-12-22 12:11.
 * email:qxinhai@yeah.net
 */

class CreateGroupHandler implements Handler {
    @Override
    public void handle(Object object) {
        CreateGroupResp resp = (CreateGroupResp) object;
        Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
