package edu.csuft.chentao.controller.handler;

import java.util.List;

import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
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
        if (groupsList.size() > 0) {
            Groups groups = groupsList.get(0);
            groups.setGroupname(resp.getGroupname());
            groups.setImage(resp.getHeadImage());
            groups.setNumber(resp.getNumber());
            groups.setTag(resp.getTag());
            groupsDao.update(groups);
        } else {
            Groups groups = new Groups();
            groups.setGroupid(resp.getGroupid());
            groups.setGroupname(resp.getGroupname());
            groups.setImage(resp.getHeadImage());
            groups.setNumber(resp.getNumber());
            groups.setTag(resp.getTag());
            groupsDao.insert(groups);
        }
    }
}