package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.dao.ChattingMessageDao;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-01-07 13:59.
 * email:qxinhai@yeah.net
 */

/**
 * 聊天信息ChattingMessage对象操作类
 */
public class ChattingMessageDaoUtil {

    /**
     * 根据群id获得该群的所有聊天记录
     *
     * @param groupId 群id
     * @return ChattingMessage对象集合
     */
    public static List<ChattingMessage> getChattingMessageList(int groupId) {

        return DaoSessionUtil.getChattingMessageDao().queryBuilder()
                .where(ChattingMessageDao.Properties.Groupid.eq(groupId))
                .build().list();
    }

    /**
     * 删除所有数据
     */
    public static void deleteAll() {
        DaoSessionUtil.getChattingMessageDao().deleteAll();
    }

    /**
     * 保存数据
     */
    public static boolean saveChattingMessage(ChattingMessage chattingMessage) {
        LoggerUtil.logger(Constant.TAG, "保存ChattingMessage到本地");

        /*
        如果该条消息在数据表中不存在，那么则插入
        增加if数据，是为了同步服务端消息数据功能
         */
        if (!isMessageExistBySerialNumber(chattingMessage.getSerial_number())) {
            DaoSessionUtil.getChattingMessageDao().insert(chattingMessage);
        }

        return true;
    }

    /**
     * 根据用户id和群id获得聊天数据
     *
     * @param groupId 群id
     * @param userId  用户id
     * @return ChattingMessage集合
     */
    public static List<ChattingMessage> getChattingMessageListWithGroupIdAndUserId(int groupId, int userId, int offset) {

        return DaoSessionUtil.getChattingMessageDao().queryBuilder()
                .where(ChattingMessageDao.Properties.Groupid.eq(groupId),
                        ChattingMessageDao.Properties.Userid.eq(userId))
                .orderDesc(ChattingMessageDao.Properties.Time)
                //offset表示从第几条数据开始
                //limit表示读取多少条数据
                .offset(20 * offset).limit(20).list();
    }

    /**
     * 根据群id得到第offset页的20条数据
     *
     * @param groupId 群id
     * @param offset  分页数
     * @return ChattingMessage集合
     */
    public static List<ChattingMessage> getChattingMessageListWithOffset(int groupId, int offset) {

        return DaoSessionUtil.getChattingMessageDao().queryBuilder()
                .where(ChattingMessageDao.Properties.Groupid.eq(groupId))
                .orderDesc(ChattingMessageDao.Properties.Time)
                .offset(offset * 20).limit(20).list();
    }

    /**
     * 更新字段
     */
    public static void updateChattingMessage(ChattingMessage chattingMessage) {
        ChattingMessage cm = DaoSessionUtil.getChattingMessageDao()
                .queryBuilder()
                .where(ChattingMessageDao.Properties.Groupid.eq(chattingMessage.getGroupid()),
                        ChattingMessageDao.Properties.Serial_number.eq(chattingMessage.getSerial_number()))
                .unique();
        DaoSessionUtil.getChattingMessageDao().delete(cm);
        saveChattingMessage(chattingMessage);
    }

    /**
     * 判断该条消息是否已经在数据表中存在
     *
     * @param serialNumber 消息的序列号
     * @return 是否存在
     */
    private static boolean isMessageExistBySerialNumber(int serialNumber) {
        ChattingMessage chattingMessage =
                DaoSessionUtil.getChattingMessageDao().queryBuilder()
                        .where(ChattingMessageDao.Properties.Serial_number.eq(serialNumber))
                        .unique();
        return !(chattingMessage == null);
    }

    /**
     * 根据群id，得到该群中最大的时间
     *
     * @param groupId 群id
     * @return 时间
     */
    public static String getMaxTimeByGroupId(int groupId) {
        List<ChattingMessage> chattingMessageList = DaoSessionUtil.getChattingMessageDao().queryBuilder()
                .where(ChattingMessageDao.Properties.Groupid.eq(groupId))
                .orderDesc(ChattingMessageDao.Properties.Time)
                .offset(0).limit(1)
                .list();
        ChattingMessage cm = null;
        if (chattingMessageList != null && chattingMessageList.size() != 0) {
            cm = chattingMessageList.get(0);
        }
        return cm == null ? null : cm.getTime();
    }


}
