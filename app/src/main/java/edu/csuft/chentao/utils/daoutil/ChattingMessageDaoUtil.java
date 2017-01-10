package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.dao.ChattingMessageDao;
import edu.csuft.chentao.pojo.bean.ChattingMessage;

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
    public static void saveChattingMessage(ChattingMessage chattingMessage) {
        DaoSessionUtil.getChattingMessageDao().insert(chattingMessage);
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
}
