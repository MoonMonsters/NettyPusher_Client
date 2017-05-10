package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-22 12:12.
 * email:qxinhai@yeah.net
 */

/**
 * 群相关信息
 */
class GroupInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupInfoResp resp = (GroupInfoResp) object;

        if (resp.getType() == Constant.TYPE_GROUP_INFO_OWNER) { //需要存储在本地
            Groups groups = CopyUtil.saveGroupInfoToGroups(resp);
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FRAGMENT_GROUP_LIST_PRESENTER, groups);
            //搜索得到的群信息
        } else if (resp.getType() == Constant.TYPE_GROUP_INFO_SEARCH) { //如果返回的GroupInfoResp的数据类型是搜索群
            LoggerUtil.logger(Constant.TAG, "GroupInfoHandler-->搜索到群");

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER, resp);
        }
    }
}