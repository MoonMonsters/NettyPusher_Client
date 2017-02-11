package edu.csuft.chentao.utils.daoutil;

import java.util.List;

import edu.csuft.chentao.pojo.bean.Hint;

/**
 * Created by Chalmers on 2017-02-10 11:33.
 * email:qxinhai@yeah.net
 */

public class HintDaoUtil {

    /**
     * 插入数据
     */
    public static void insert(Hint hint) {
        DaoSessionUtil.getHintDao().insert(hint);
    }

    /**
     * 移除数据
     */
    public static void remove(Hint hint) {
        DaoSessionUtil.getHintDao().delete(hint);
    }

    /**
     * 加载所有数据
     */
    public static List<Hint> loadAll() {
        return DaoSessionUtil.getHintDao().loadAll();
    }

    /**
     * 删除所有数据
     */
    public static void deleteAll() {
        DaoSessionUtil.getHintDao().deleteAll();
    }

    /**
     * 更新
     */
    public static void update(List<Hint> hintList) {
//        DaoSessionUtil.getHintDao().deleteByKey(hint.get_id());
//        insert(hint);
        deleteAll();
        DaoSessionUtil.getHintDao().insertInTx(hintList);
    }

}
