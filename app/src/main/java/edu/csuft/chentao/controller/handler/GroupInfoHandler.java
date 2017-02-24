package edu.csuft.chentao.controller.handler;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 12:12.
 * email:qxinhai@yeah.net
 */

class GroupInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupInfoResp resp = (GroupInfoResp) object;

        if (resp.getType() == Constant.TYPE_GROUP_INFO_OWNER) { //需要存储在本地
            Groups groups = CopyUtil.saveGroupInfoToGroups(resp);
            EBToPreObject ebObj = new EBToPreObject(Constant.TAG_FRAGMENT_GROUP_LIST_PRESENTER, groups);
            LoggerUtil.logger(Constant.TAG, "接收到GroupInfoResp--OWNER数据");
            EventBus.getDefault().post(ebObj);
            //搜索得到的群信息
        } else if (resp.getType() == Constant.TYPE_GROUP_INFO_SEARCH) { //如果返回的GroupInfoResp的数据类型是搜索群
            EBToPreObject ebObj = new EBToPreObject(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER, resp);
            EventBus.getDefault().post(ebObj);
        }
    }
}