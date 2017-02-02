package edu.csuft.chentao.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;

/**
 * Created by Chalmers on 2017-02-01 22:44.
 * email:qxinhai@yeah.net
 */

public class UserDataUtil {

    public static String getUserName(UserInfo userInfo) {
        return userInfo == null ? null : userInfo.getNickname();
    }

    public static String getUserSignature(UserInfo userInfo) {
        return userInfo == null ? null : userInfo.getSignature();
    }

    /**
     * 获得头像
     *
     * @param userHead 用户头像类
     */
    public static byte[] getUserImage(UserHead userHead) {
        byte[] buf;
        if (userHead == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                    R.drawable.ic_add);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            buf = baos.toByteArray();
        } else {
            buf = userHead.getImage();
        }

        return buf;
    }

}

