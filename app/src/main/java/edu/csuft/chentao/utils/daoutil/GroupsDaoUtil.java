package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.pojo.bean.Groups;

/**
 * Created by Chalmers on 2017-01-07 14:00.
 * email:qxinhai@yeah.net
 */

/**
 * 群数据Groups操作类
 */
public class GroupsDaoUtil {

    /**
     * 加载所有的Groups数据
     */
    public static List<Groups> loadAll() {

        return DaoSessionUtil.getGroupsDao().loadAll();
    }

    /**
     * 删除所有的Groups数据
     */
    public static void deleteAll() {
        DaoSessionUtil.getGroupsDao().deleteAll();
    }

    /**
     * 保存Groups数据
     *
     * @param groups Groups对象
     */
    public static void saveGroups(Groups groups) {
        DaoSessionUtil.getGroupsDao().save(groups);
    }

    /**
     * 更新Groups数据
     */
    public static void updateGroups(Groups groups) {
        DaoSessionUtil.getGroupsDao().update(groups);
    }

    /**
     * 根据群id得到Groups数据
     *
     * @param groupId 群id
     */
    public static Groups getGroups(int groupId) {

        return DaoSessionUtil.getGroupsDao().queryBuilder()
                .where(GroupsDao.Properties.Groupid.eq(groupId))
                .build().unique();
    }

    /**
     * genuine群id把对应的Groups对象移除掉
     *
     * @param groupId 群id
     */
    public static void deleteByGroupId(int groupId) {

        Groups groups = getGroups(groupId);
        DaoSessionUtil.getGroupsDao().delete(groups);
    }
}
