package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.dao.LocalAnnouncementDao;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * @author edu.csuft.chentao
 *         LocalAnnouncementDao的工具类
 */

public class LocalAnnouncementDaoUtil {

    /**
     * 获得所有的公告数据，按时间先后顺序排序，最新的公告显示在最上面
     *
     * @param groupId 需要获取的群公告的群id
     * @return 公告列表
     */
    public static List<LocalAnnouncement> getAllLocalAnnouncements(int groupId) {

        LoggerUtil.logger("TAG", "LocalAnnouncementDaoUtil-->getAllLocalAnnouncements.groupId=" + groupId);

        return DaoSessionUtil.getLocalAnnouncementDao().queryBuilder()
                //查询条件
                .where(LocalAnnouncementDao.Properties.Groupid.eq(groupId))
                //按时间排序
                .orderDesc(LocalAnnouncementDao.Properties.Time)
                .list();
    }

    /**
     * 获取最新的公告，按时间排序
     *
     * @param isNew 没有操作过的公告
     * @return 公告集合
     */
    public static List<LocalAnnouncement> getAllLocalAnnouncementsWithNew(int groupId, boolean isNew) {
        return DaoSessionUtil.getLocalAnnouncementDao().queryBuilder()
                //查询条件
                .where(LocalAnnouncementDao.Properties.Groupid.eq(groupId))
                .where(LocalAnnouncementDao.Properties.Isnew.eq(isNew))
                //按时间排序
                .orderDesc(LocalAnnouncementDao.Properties.Time)
                .list();
    }

    /**
     * 插入数据
     */
    public static void insert(LocalAnnouncement la) {

        LoggerUtil.logger("TAG", "LocalAnnouncementDaoUtil-->insert.groupId=" + la.getGroupid());

        DaoSessionUtil.getLocalAnnouncementDao().insert(la);
    }

    /**
     * 删除数据
     */
    public static void delete(LocalAnnouncement la) {
        LoggerUtil.logger("TAG", "LocalAnnouncementDaoUtil-->delete=" + la.getGroupid());
        DaoSessionUtil.getLocalAnnouncementDao().delete(la);
    }

    /**
     * 根据_id获取对应的数据
     *
     * @param _id 唯一值
     * @return LocalAnnouncement对象
     */
    public static LocalAnnouncement query(Long _id) {
        LoggerUtil.logger("TAG", "LocalAnnouncementDaoUtil-->query");
        return DaoSessionUtil.getLocalAnnouncementDao().queryBuilder()
                .where(LocalAnnouncementDao.Properties._id.eq(_id))
                .build().list().get(0);
    }

    /**
     * 更新
     *
     * @param la 需要更新的数据
     */
    public static void update(LocalAnnouncement la) {
        //可以直接使用update进行更新操作
        DaoSessionUtil.getLocalAnnouncementDao().update(la);
    }
}
