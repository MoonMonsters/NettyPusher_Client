package edu.csuft.chentao.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chalmers on 2016-12-29 17:43.
 * email:qxinhai@yeah.net
 */

public class OperationUtil {

    /**
     * 得到当前时间
     *
     * @return 时间
     */
    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTime().toString();
    }

}
