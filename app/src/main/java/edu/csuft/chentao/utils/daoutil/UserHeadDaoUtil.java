package edu.csuft.chentao.utils.daoutil;

/**
 * Created by csuft.chentao on 2017-01-07 14:01.
 * email:qxinhai@yeah.net
 */

import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.pojo.bean.UserHead;

/**
 * 用户头像UserHead操作类
 */
public class UserHeadDaoUtil {

    /**
     * 获得用户头像UserHead类对象
     *
     * @param userId 用户id
     * @return UserHead对象
     */
    public static UserHead getUserHead(int userId) {

        return DaoSessionUtil.getUserHeadDao().queryBuilder()
                .where(UserHeadDao.Properties.Userid.eq(userId))
                .unique();
    }

    /**
     * 保存用户头像UserHead对象
     *
     * @param userHead UserHead对象
     */
    public static void saveUserHead(UserHead userHead) {
        DaoSessionUtil.getUserHeadDao().insert(userHead);
    }

    /**
     * 更新用户头像UserHead对象
     *
     * @param userHead UserHead对象
     */
    public static void updateUserHead(UserHead userHead) {
        DaoSessionUtil.getUserHeadDao().update(userHead);
    }

}
