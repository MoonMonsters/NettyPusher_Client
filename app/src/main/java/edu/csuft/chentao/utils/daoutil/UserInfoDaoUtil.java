package edu.csuft.chentao.utils.daoutil;

import edu.csuft.chentao.dao.UserInfoDao;
import edu.csuft.chentao.pojo.bean.UserInfo;

/**
 * Created by csuft.chentao on 2017-01-07 14:01.
 * email:qxinhai@yeah.net
 */

/**
 * 用户信息UserInfo操作类
 */
public class UserInfoDaoUtil {

    /**
     * 根据用户id得到用户信息
     *
     * @param userId 用户id
     * @return UserInfo对象
     */
    public static UserInfo getUserInfo(int userId) {


        return DaoSessionUtil.getUserInfoDao()
                .queryBuilder().where(UserInfoDao.Properties.Userid.eq(userId))
                .unique();
    }

    /**
     * 保存用户信息
     *
     * @param userInfo UserInfo对象
     */
    public static void saveUserInfo(UserInfo userInfo) {
        DaoSessionUtil.getUserInfoDao()
                .save(userInfo);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo UserInfo对象
     */
    public static void updateUserInfo(UserInfo userInfo) {
        DaoSessionUtil.getUserInfoDao().update(userInfo);
    }
}
