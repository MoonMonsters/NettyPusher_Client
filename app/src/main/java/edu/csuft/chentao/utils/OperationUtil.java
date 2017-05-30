package edu.csuft.chentao.utils;

import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.Message;

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
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        return sdf.format(date);
    }

    /**
     * 发送消息
     *
     * @param groupId 群id
     * @param msgType 消息类型
     * @param content 文字内容
     * @param image   图片
     */
    public static Message sendChattingMessage(int groupId, int msgType,
                                              String content, byte[] image) {
        //发送数据，构造对象
        Message message = new Message();
        //消息类型，文字还是图片
        message.setTypeMsg(msgType);
        //发送类型
        message.setType(Constant.TYPE_MSG_SEND);
        message.setGroupid(groupId);
        //因为是文字类型，所以图片为空
        message.setPicFile(image);
        //文字内容
        message.setMessage(content);
        //用户id
        message.setUserid(SharedPrefUserInfoUtil.getUserId());
        //设置时间
        message.setTime(OperationUtil.getCurrentTime());
        message.setSerial_number(OperationUtil.getSerialNumberIntegerValue());
        //发送数据
        SendMessageUtil.sendMessage(message);

        return message;
    }

    /**
     * 把偶才能的图片的文件名称
     */
    public static String getImageName() {
        String str = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(str.charAt((int) (Math.random() * str.length())));
        }

        return sb.toString() + ".jpg";
    }

    /**
     * 得到随机的16位字符串
     */
    public static String getSerialnumber() {
        String str = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(str.charAt((int) (Math.random() * str.length())));
        }

        return sb.toString();
    }

    /**
     * 获取随机的int类型的序列号
     */
    public static int getSerialNumberIntegerValue() {

        return (int) (Math.random() * Integer.MAX_VALUE);
    }

    /**
     * 发送消息请求用户数据
     *
     * @param userId 用户id
     */
    public static void getUserInfoFromServerByUserId(int userId) {
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_USERINFO);
        req.setArg1(userId);
        SendMessageUtil.sendMessage(req);
    }

    /**
     * 将bitmap转化成byte[]数组
     *
     * @param bm Bitmap对象
     * @return 转化后的byte[]数组
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 发送EBToObjectPresenter对象
     *
     * @param tag    标志
     * @param object 实例对象
     */
    public static void sendEBToObjectPresenter(String tag, Object object) {
        EBToPreObject ebObj = new EBToPreObject(tag, object);
        EventBus.getDefault().post(ebObj);
    }

    /**
     * 发送ImageDetail对象
     *
     * @param tag 标志
     * @param buf 图片的byte[]数组
     */
    public static void sendImageDetail(String tag, byte[] buf) {
        ImageDetail imageDetail = new ImageDetail(tag, buf);
        EventBus.getDefault().post(imageDetail);
    }

    /**
     * 创建文件夹
     *
     * @param path 文件夹路径
     * @return 是否创建成功
     */
    private static boolean createDirectory(String path) {
        boolean isExist = true;
        File file = new File(path);
        if (!file.exists()) {
            //创建
            isExist = file.mkdirs();
        }

        return isExist;
    }

    /**
     * 创建文件
     *
     * @param path     文件夹路径
     * @param fileName 文件路径
     */
    public static File createFile(String path, String fileName) {

        File file = null;

        if (createDirectory(path)) {
            file = new File(path, fileName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    /**
     * 获得id对应的String内容
     */
    public static String getString(ViewDataBinding viewDataBinding, int stringId) {
        return viewDataBinding.getRoot().getContext().getString(stringId);
    }

    /**
     * 将File转换成byte[]类型
     *
     * @param file File对象
     * @return byte[]数组
     */
    public static byte[] file2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 将byte[]数据类型转换成File对象
     *
     * @param buf byte[]数据
     * @return File对象
     */
    public static File byte2File(byte[] buf) {
        File file = createFile(Constant.PATH_IMAGE, "imageFileCache.jpeg");

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    /**
     * 将byte[]转换成bitmap
     *
     * @param b byte[]数组
     * @return Bitmap对象
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 压缩图片
     *
     * @param pathName  图片地址
     * @param reqWidth  图片宽度
     * @param reqHeight 图片高度
     * @return 压缩后的图片对象
     */
    public static Bitmap decodeSampledBitmapFromFd(String pathName,
                                                   int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 全屏显示
     */
    public static void fullScreen(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.hide();
    }

    /**
     * 获得图片的宽度
     *
     * @param buf 图片资源
     * @return 图片宽度
     */
    public static int getImageWidth(byte[] buf) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length, options);

        return options.outWidth;
    }

    /**
     * 获得图片高度
     *
     * @param buf 图片资源
     * @return 图片高度
     */
    public static int getImageHeight(byte[] buf) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /*
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length, options);

        return options.outHeight;
    }
}
