package edu.csuft.chentao.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Chalmers on 2016-12-16 15:22.
 * email:qxinhai@yeah.net
 */

/**
 * 打印日志类
 */
public class LoggerUtil {

    private static boolean isPrint = true;

    /**
     * 打印日志
     * @param key 输出key值
     * @param value 输出value值
     */
    public static void logger(String key, String value) {
        if (isPrint) {
            Log.i(key, value);
        }
    }

    /**
     * 弹出提示框
     *
     * @param context 上下文对象
     * @param content 显示内容
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

}
