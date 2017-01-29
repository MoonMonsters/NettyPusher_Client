package edu.csuft.chentao.utils.daoutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.dao.UserInfoDao;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.resp.UserInfoResp;

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
            userInfoList.add(getUserInfo((int) it.next()));
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
     *
     * @param resp
     */
    public static void saveUserInfoFromHandler(UserInfoResp resp) {
        //用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(resp.getUserid());
        userInfo.setNickname(resp.getNickname());
        userInfo.setSignature(resp.getSignature());

        //保存
        List<UserInfo> userInfoList = loadAll();

        if (userInfoList.size() > 0) {  //如果该数据已经存在，则更新
            UserInfo userInfo2 = userInfoList.get(0);
            userInfo2.setSignature(userInfo.getSignature());
            userInfo2.setNickname(userInfo.getNickname());
            updateUserInfo(userInfo2);
        } else {    //否则插入
            saveUserInfo(userInfo);
        }
    }
}
