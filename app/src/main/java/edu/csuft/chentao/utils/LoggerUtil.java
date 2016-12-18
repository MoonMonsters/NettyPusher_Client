package edu.csuft.chentao.utils;

import android.util.Log;

/**
 * Created by Chalmers on 2016-12-16 15:22.
 * email:qxinhai@yeah.net
 */

/**
 * 打印日志类
 */
public class LoggerUtil {

    private static boolean isPrint = true;

    public static void logger(String key, String value) {
        if (isPrint) {
            Log.i(key, value);
        }
    }

}
