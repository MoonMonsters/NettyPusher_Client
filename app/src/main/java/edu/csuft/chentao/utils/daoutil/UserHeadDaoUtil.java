package edu.csuft.chentao.utils.daoutil;

/**
 * Created by csuft.chentao on 2017-01-07 14:01.
 * email:qxinhai@yeah.net
 */

import java.util.List;

import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

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

    public static List<UserHead> loadAll() {
        return DaoSessionUtil.getUserHeadDao().loadAll();
    }

    /**
     * 从Handler中把UserHead数据保存到数据库
     */
    public static void saveUserHeadFromHandler(UserInfoResp resp) {
        //用户头像
        UserHead userHead = getUserHead(resp.getUserid());
        if (userHead == null) { //插入

            LoggerUtil.logger(Constant.TAG, "用户头像为空");

            userHead = new UserHead();
            userHead.setUserid(resp.getUserid());
            userHead.setImage(resp.getHeadImage());

            saveUserHead(userHead);
        } else {    //更新

            LoggerUtil.logger(Constant.TAG, "更新头像");

            userHead.setUserid(resp.getUserid());
            userHead.setImage(resp.getHeadImage());

            updateUserHead(userHead);
        }
    }

}
