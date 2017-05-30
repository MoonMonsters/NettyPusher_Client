package edu.csuft.chentao.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 打印日志类
 * Created by Chalmers on 2016-12-16 15:22.
 * email:qxinhai@yeah.net
 */
public class LoggerUtil {

    private static boolean isPrint = true;

    /**
     * 打印日志
     *
     * @param object key值
     * @param value  value值
     */
    public static void logger(Object object, String value) {
        String name;
        if (isPrint) {
            if (object instanceof String) {
                name = (String) object;
            } else if (object instanceof Class) {
                name = ((Class) object).getSimpleName();
            } else {
                name = object.getClass().getSimpleName();
            }

            Log.i(name, value);
        }
    }

    /**
     * 弹出提示框
     *
     * @param context 上下文对象
     * @param content 显示内容
     */
    public static void showToast(final Context context, final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        }).start();
    }

}
