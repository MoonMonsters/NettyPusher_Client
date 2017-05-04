package edu.csuft.chentao.pojo.req;

import java.io.Serializable;
import java.util.Arrays;
/**
 * @author csuft.chentao
 * @date 2017-05-01
 */

/**
 * 文件上传下载类
 */
public class FileZip implements Serializable {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件的序列号,可以用来充当下载序列，根据序列来下载对应的文件，而不是根据文件名
     */
    private String serialNumber;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 文件内容
     */
    private byte[] zip;
    /**
     * 文件所在的群id
     */
    private int groupId;
    /**
     * 文件上传时间
     */
    private String time;
    /**
     * 文件大小，该属性在客户端向服务端发送数据时，不需要使用
     */
    private String fileSize;

    public FileZip(String fileName, String serialNumber, String nickname,
                   int userId, byte[] zip, int groupId, String time,
                   String fileSize) {
        this.fileName = fileName;
        this.serialNumber = serialNumber;
        this.nickname = nickname;
        this.userId = userId;
        this.zip = zip;
        this.groupId = groupId;
        this.time = time;
        this.fileSize = fileSize;
    }

    public FileZip() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getZip() {
        return zip;
    }

    public void setZip(byte[] zip) {
        this.zip = zip;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileZip{" +
                "fileName='" + fileName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userId=" + userId +
                ", zip=" + Arrays.toString(zip) +
                ", groupId=" + groupId +
                ", time='" + time + '\'' +
                ", fileSize='" + fileSize + '\'' +
                '}';
    }
}