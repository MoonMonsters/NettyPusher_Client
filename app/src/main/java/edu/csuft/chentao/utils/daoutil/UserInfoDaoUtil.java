package edu.csuft.chentao.utils.daoutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.dao.UserInfoDao;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;

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

        UserInfo userInfo =
                DaoSessionUtil.getUserInfoDao()
                        .queryBuilder().where(UserInfoDao.Properties.Userid.eq(userId))
                        .unique();

        //如果请求的数据为空，那么便向服务器请求用户的数据
        if (userInfo == null) {
            LoggerUtil.logger(Constant.TAG, "userId" + userId + "的数据为空");
            OperationUtil.getUserInfoFromServerByUserId(userId);
        }

        return userInfo;
    }

    /**
     * 根据用户名得到用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public static UserInfo getUserInfoByUsername(String username) {

        return DaoSessionUtil.getUserInfoDao()
                .queryBuilder().where(UserInfoDao.Properties.Username.eq(username))
                .unique();
    }

    /**
     * 保存用户信息
     *
     * @param userInfo UserInfo对象
     */
    public static void saveUserInfo(UserInfo userInfo) {
        DaoSessionUtil.getUserInfoDao()
                .insert(userInfo);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo UserInfo对象
     */
    public static void updateUserInfo(UserInfo userInfo) {
        DaoSessionUtil.getUserInfoDao().update(userInfo);
    }

    public static List<UserInfo> getAllUserInfosWithGroupIdMap(Map<Integer, Integer> map) {
        List<UserInfo> userInfoList = new ArrayList<>();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            UserInfo userInfo = getUserInfo((int) it.next());
            if (userInfo != null) {
                userInfoList.add(userInfo);
            }
        }

        return userInfoList;
    }

    /**
     * 加载所有数据
     */
    public static List<UserInfo> loadAll() {
        return DaoSessionUtil.getUserInfoDao().loadAll();
    }

    /**
     * 将Handler中的UserInfo数据保存到数据库中
     */
    public static void saveUserInfoFromHandler(UserInfoResp resp) {
        //用户信息
        UserInfo userInfo = getUserInfo(resp.getUserid());
        if (userInfo == null) { //如果用户数据不存在，则插入
            userInfo = new UserInfo();
            userInfo.setUserid(resp.getUserid());
            userInfo.setNickname(resp.getNickname());
            userInfo.setSignature(resp.getSignature());
            userInfo.setUsername(resp.getUsername());
            saveUserInfo(userInfo);
        } else {    //如果存在，则更新
            userInfo.setUserid(resp.getUserid());
            userInfo.setNickname(resp.getNickname());
            userInfo.setSignature(resp.getSignature());
            updateUserInfo(userInfo);
        }
    }
}
