package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 发送的消息
 *
 * @author cusft.chentao
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private int userid;
    /**
     * 群id
     */
    private int groupid;
    /**
     * TYPE_MSG_TEXT 0 TYPE_MSG_PIC 1
     */
    private int typeMsg; // 消息内容，是文字消息还是图片消息
    /**
     * TYPE_MSG_SEND 0 TYPE_MSG_RECV 1
     */
    private int type; // 是发送还是接收
    /**
     * 消息发送时间
     */
    private String time;
    /**
     * 发送的文字内容
     */
    private String message;
    /**
     * 发送的图片
     */
    private byte[] picFile;
    /**
     * 标志位，判断是否发送成功
     */
    private int serial_number;

    public Message() {
    }

    public Message(int userid, int groupid, int typeMsg, int type, String time,
                   String message, byte[] picFile, int serial_number) {
        this.userid = userid;
        this.groupid = groupid;
        this.typeMsg = typeMsg;
        this.type = type;
        this.time = time;
        this.message = message;
        this.picFile = picFile;
        this.serial_number = serial_number;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getTypeMsg() {
        return typeMsg;
    }

    public void setTypeMsg(int typeMsg) {
        this.typeMsg = typeMsg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getPicFile() {
        return picFile;
    }

    public void setPicFile(byte[] picFile) {
        this.picFile = picFile;
    }

    public int getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(int serial_number) {
        this.serial_number = serial_number;
    }
}
