package edu.csuft.chentao.controller.handler;

import android.content.Intent;

import java.util.List;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.GreenDaoUtil;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2016-12-22 12:12.
 * email:qxinhai@yeah.net
 */

public class GroupInfoHandler implements Handler {
    @Override
    public void handle(Object object) {
        GroupInfoResp resp = (GroupInfoResp) object;

        LoggerUtil.logger(Constant.TAG, "GroupInfoResp-->" + resp.toString());

        GroupsDao groupsDao = GreenDaoUtil.getInstance()
                .getDaoSession().getGroupsDao();
        List<Groups> groupsList = groupsDao.queryBuilder().where(GroupsDao.Properties.Groupid.eq(resp.getGroupid()))
                .build().list();
        Groups groups = null;
        if (groupsList.size() > 0) {
            groups = CopyUtil.updateGroupInfoToGroups(resp, groupsList);
        } else {
            groups = CopyUtil.saveGroupInfoToGroups(resp);
        }

        /*
        发送广播
         */
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_GROUPS);
        intent.putExtra(Constant.EXTRA_GROUPS, groups);
        MyApplication.getInstance().sendBroadcast(intent);

    }
}