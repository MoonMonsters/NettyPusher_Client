package edu.csuft.chentao.utils;

import java.text.DecimalFormat;

import edu.csuft.chentao.pojo.resp.GroupInfoResp;

/**
 * Created by Chalmers on 2017-05-11 18:24.
 * email:qxinhai@yeah.net
 */

public class XMLUtil {

    /**
     * 字符串的拼接,用空格隔开
     *
     * @param values 字符串的所有值
     * @return 拼接后的字符串
     */
    public static String appendString(String... values) {
        StringBuilder sb = new StringBuilder();
        for (String str : values) {
            sb.append(str);
            sb.append(" ");
        }
        sb.replace(sb.length() - 1, sb.length(), "");

        return sb.toString();
    }

    /**
     * 拼接文件大小和用户昵称
     *
     * @param fileSize 文件大小
     * @param nickname 用户昵称
     * @return 拼接后的字符串
     */
    public static String spliceFileSizeAndNickName(String fileSize, String nickname) {
        return fileSize + " 来自 " + nickname;
    }

    /**
     * 根据类型的值，返回对应的String值
     */
    public static String getTextWithHintType(int type) {
        String result = null;
        if (type == Constant.TYPE_HINT_SHOW_AGREE) {
            result = "已同意";
        } else if (type == Constant.TYPE_HINT_SHOW_REFUSE) {
            result = "已拒绝";
        }

        return result;
    }

    /**
     * 拼凑数量和标签
     *
     * @param resp GroupInfoResp对象
     * @return 拼凑的字符串
     */
    public static String getTagAndNumber(GroupInfoResp resp) {

        return resp.getNumber() + "人/" + resp.getTag();
    }

    /**
     * 获得身份信息
     *
     * @param capital 身份值
     */
    public static String getCapital(int capital) {
        String result = null;

        switch (capital) {
            case Constant.TYPE_GROUP_CAPITAL_ADMIN:
                result = Constant.ADMIN;
                break;
            case Constant.TYPE_GROUP_CAPITAL_OWNER:
                result = Constant.OWNER;
                break;
            case Constant.TYPE_GROUP_CAPITAL_USER:
                result = Constant.USER;
                break;
        }

        return result;
    }

    /**
     * 得到文件大小
     */
    public static String getFileSize(String fileSize) {
        String result = null;

        long KB = 1024;
        long MB = 1024 * KB;
        long GB = 1024 * MB;

        try {
            long size = Long.valueOf(fileSize);

            if (size > GB) {
                result = DecimalFormat.getInstance().format(size * 1.0 / GB);
                result += "G";
            } else if (size > MB) {
                result = DecimalFormat.getInstance().format(size * 1.0 / MB);
                result += "M";
            } else {
                result = DecimalFormat.getInstance().format(size * 1.0 / KB);
                result += "K";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
