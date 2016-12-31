package edu.csuft.chentao.utils;

import java.util.List;

import edu.csuft.chentao.dao.ChattingMessageDao;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.dao.GroupUserDao;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.dao.UserInfoDao;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;

/**
 * Created by Chalmers on 2016-12-29 16:23.
 * email:qxinhai@yeah.net
 */

public class DaoSessionUtil {

    /**
     * 获得UserInfoDao对象
     */
    public static UserInfoDao getUserInfoDao() {
        return GreenDaoUtil.getInstance()
                .getDaoSession().getUserInfoDao();
    }

    /**
     * 根据用户id获得UserInfo对象
     *
     * @param userId 用户id
     */
    public static UserInfo getUserInfo(int userId) {

        return getUserInfoDao().queryBuilder()
                .where(UserInfoDao.Properties.Userid.eq(userId))
                .build().list().get(0);
    }

    /**
     * 获得UserHeadDao对象
     */
    public static UserHeadDao getUserHeadDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getUserHeadDao();
    }

    /**
     * 根据用户id获得UserHead对象
     *
     * @param userId 用户id
     */
    public static UserHead getUserHead(int userId) {
        return getUserHeadDao().queryBuilder()
                .where(UserHeadDao.Properties.Userid.eq(userId))
                .build().list().get(0);
    }

    public static GroupsDao getGroupsDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getGroupsDao();
    }

    /**
     * 返回所有的Groups数据
     */
    public static List<Groups> getGroupsList() {
        return getGroupsDao().queryBuilder().list();
    }

    public static GroupUserDao getGroupUserDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getGroupUserDao();
    }

    public static GroupChattingItemDao getGroupChattingItemDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getGroupChattingItemDao();
    }

    public static ChattingMessageDao getChattingMessageDao() {
        return GreenDaoUtil.getInstance().getDaoSession()
                .getChattingMessageDao();
    }

    public static void saveChattingMessage(ChattingMessage message) {
        getChattingMessageDao().insert(message);
    }

}
