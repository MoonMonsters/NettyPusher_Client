package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-01-07 13:59.
 * email:qxinhai@yeah.net
 */

/**
 * 聊天框界面GroupChattingItem操作类
 */
public class GroupChattingItemDaoUtil {

    /**
     * 根据群id得到聊天界面GroupChattingItem对象
     *
     * @param groupId 群id
     * @return GroupChattingItem对象
     */
    public static GroupChattingItem getGroupChattingItem(int groupId) {

        return DaoSessionUtil.getGroupChattingItemDao().queryBuilder()
                .where(GroupChattingItemDao.Properties.Groupid.eq(groupId))
                .unique();
    }

    /**
     * 保存GroupChattingItem对象
     *
     * @param chattingItem GroupChattingItem对象
     */
    public static void saveGroupChattingItem(GroupChattingItem chattingItem) {
        chattingItem.setFlag(getMaxFlagValue() + 1);
        DaoSessionUtil.getGroupChattingItemDao().save(chattingItem);
    }

    /**
     * 更新GroupChattingItem对象
     *
     * @param chattingItem GroupChattingItem对象
     */
    public static void updateGroupChattingItem(GroupChattingItem chattingItem) {
        chattingItem.setFlag(getMaxFlagValue() + 1);
        DaoSessionUtil.getGroupChattingItemDao().update(chattingItem);
    }

    /**
     * 删除GroupChattingItem数据对象
     *
     * @param chattingItem GroupChattingItem对象
     */
    public static void removeGroupChattingItem(GroupChattingItem chattingItem) {
        DaoSessionUtil.getGroupChattingItemDao().delete(chattingItem);
    }

    /**
     * 加载所有GroupChattingItem数据
     */
    public static List<GroupChattingItem> loadAll() {
        return DaoSessionUtil.getGroupChattingItemDao().loadAll();
    }

    /**
     * 删除所有的GroupChattingItem数据
     */
    public static void deleteAll() {
        DaoSessionUtil.getGroupChattingItemDao().deleteAll();
    }

    /**
     * 根据群id得到所有的聊天数据
     *
     * @param groupId 群id
     */
    public static List<GroupChattingItem> loadAllWithGroupId(int groupId) {

        return DaoSessionUtil.getGroupChattingItemDao().queryBuilder()
                .where(GroupChattingItemDao.Properties.Groupid.eq(groupId))
                .build().list();
    }

    /**
     * 根据群id移除聊天框的聊天项
     *
     * @param groupId 群id
     */
    public static void removeByGroupId(int groupId) {

        GroupChattingItem item = getGroupChattingItem(groupId);
        DaoSessionUtil.getGroupChattingItemDao().delete(item);
    }

    /**
     * 获得当前flag的最大值
     */
    private static int getMaxFlagValue() {
        List<GroupChattingItem> itemList = DaoSessionUtil.getGroupChattingItemDao()
                .queryBuilder().orderDesc(GroupChattingItemDao.Properties.Flag)
                .list();
        int result = (itemList == null || itemList.size() == 0) ? 0 : itemList.get(0).getFlag();
        LoggerUtil.logger(GroupChattingItemDaoUtil.class, "result = " + result);
        return result;
    }
}
