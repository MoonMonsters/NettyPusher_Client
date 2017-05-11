package edu.csuft.chentao.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chalmers on 2017-05-11 15:31.
 * email:qxinhai@yeah.net
 */

public class ActivityManager {

    private static List<BaseActivity> mBaseActivityList = new ArrayList<>();

    /**
     * 当Activity创建时添加
     *
     */
    public static void add(BaseActivity activity) {
        mBaseActivityList.add(activity);
    }

    /**
     * 当Activity销毁时移除
     */
    public static void removeActivity(BaseActivity activity) {
        if (mBaseActivityList.contains(activity)) {
            mBaseActivityList.remove(activity);
        }
    }

    /**
     * 清空所有的Activity
     */
    public static void clearActivities() {
        for (BaseActivity activity : mBaseActivityList) {
            activity.finish();
        }

        mBaseActivityList.clear();

    }

}
