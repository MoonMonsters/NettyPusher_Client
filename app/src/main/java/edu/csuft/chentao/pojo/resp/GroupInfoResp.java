/**
 * 在进入群后，返回该群信息
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;
import java.util.Arrays;


/**
 * @author csuft.chentao
 *         <p>
 *         2016年12月11日 下午8:56:28
 */
public class GroupInfoResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群数据类型
     */
    private int type;
    /**
     * 群id
     */
    private int groupid;
    /**
     * 群名称
     */
    private String groupname;
    /**
     * 群标签
     */
    private String tag;
    /**
     * 群人数
     */
    private int number;
    /**
     * 群头像
     */
    private byte[] headImage;

    public GroupInfoResp(int type, int groupid, String groupname,
                         String tag, int number, byte[] headImage) {
        this.type = type;
        this.groupid = groupid;
        this.groupname = groupname;
        this.tag = tag;
        this.number = number;
        this.headImage = headImage;
    }

    public GroupInfoResp() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public byte[] getHeadImage() {
        return headImage;
    }

    public void setHeadImage(byte[] headImage) {
        this.headImage = headImage;
    }

    @Override
    public String toString() {
        return "GroupInfoResp{" +
                "type=" + type +
                ", groupid=" + groupid +
                ", groupname='" + groupname + '\'' +
                ", tag='" + tag + '\'' +
                ", number=" + number +
                ", headImage=" + Arrays.toString(headImage) +
                '}';
    }
}
