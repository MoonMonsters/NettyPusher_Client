package edu.csuft.chentao.utils.daoutil;

import edu.csuft.chentao.dao.ChattingMessageDao;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.dao.UserInfoDao;

/**
 * Created by Chalmers on 2016-12-29 16:23.
 * email:qxinhai@yeah.net
 */

class DaoSessionUtil {

    /**
     * 获得UserInfoDao对象
     */
    static UserInfoDao getUserInfoDao() {
        return GreenDaoUtil.getInstance()
                .getDaoSession().getUserInfoDao();
    }

    /**
     * 获得UserHeadDao对象
     */
    static UserHeadDao getUserHeadDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getUserHeadDao();
    }

    /**
     * 获得GroupsDao对象
     */
    static GroupsDao getGroupsDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getGroupsDao();
    }

    /**
     * 获得GroupChattingItemDao对象
     */
    static GroupChattingItemDao getGroupChattingItemDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getGroupChattingItemDao();
    }

    /**
     * 获得ChattingMessageDao对象
     */
    static ChattingMessageDao getChattingMessageDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getChattingMessageDao();
    }
}
