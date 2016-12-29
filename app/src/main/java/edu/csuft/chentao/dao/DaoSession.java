package edu.csuft.chentao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.GroupUser;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;

import edu.csuft.chentao.dao.ChattingMessageDao;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.dao.GroupUserDao;
import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.dao.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig chattingMessageDaoConfig;
    private final DaoConfig groupChattingItemDaoConfig;
    private final DaoConfig groupsDaoConfig;
    private final DaoConfig groupUserDaoConfig;
    private final DaoConfig userHeadDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final ChattingMessageDao chattingMessageDao;
    private final GroupChattingItemDao groupChattingItemDao;
    private final GroupsDao groupsDao;
    private final GroupUserDao groupUserDao;
    private final UserHeadDao userHeadDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        chattingMessageDaoConfig = daoConfigMap.get(ChattingMessageDao.class).clone();
        chattingMessageDaoConfig.initIdentityScope(type);

        groupChattingItemDaoConfig = daoConfigMap.get(GroupChattingItemDao.class).clone();
        groupChattingItemDaoConfig.initIdentityScope(type);

        groupsDaoConfig = daoConfigMap.get(GroupsDao.class).clone();
        groupsDaoConfig.initIdentityScope(type);

        groupUserDaoConfig = daoConfigMap.get(GroupUserDao.class).clone();
        groupUserDaoConfig.initIdentityScope(type);

        userHeadDaoConfig = daoConfigMap.get(UserHeadDao.class).clone();
        userHeadDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        chattingMessageDao = new ChattingMessageDao(chattingMessageDaoConfig, this);
        groupChattingItemDao = new GroupChattingItemDao(groupChattingItemDaoConfig, this);
        groupsDao = new GroupsDao(groupsDaoConfig, this);
        groupUserDao = new GroupUserDao(groupUserDaoConfig, this);
        userHeadDao = new UserHeadDao(userHeadDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(ChattingMessage.class, chattingMessageDao);
        registerDao(GroupChattingItem.class, groupChattingItemDao);
        registerDao(Groups.class, groupsDao);
        registerDao(GroupUser.class, groupUserDao);
        registerDao(UserHead.class, userHeadDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        chattingMessageDaoConfig.clearIdentityScope();
        groupChattingItemDaoConfig.clearIdentityScope();
        groupsDaoConfig.clearIdentityScope();
        groupUserDaoConfig.clearIdentityScope();
        userHeadDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
    }

    public ChattingMessageDao getChattingMessageDao() {
        return chattingMessageDao;
    }

    public GroupChattingItemDao getGroupChattingItemDao() {
        return groupChattingItemDao;
    }

    public GroupsDao getGroupsDao() {
        return groupsDao;
    }

    public GroupUserDao getGroupUserDao() {
        return groupUserDao;
    }

    public UserHeadDao getUserHeadDao() {
        return userHeadDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}
