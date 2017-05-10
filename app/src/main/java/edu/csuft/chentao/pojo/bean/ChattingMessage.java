package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.io.Serializable;

import edu.csuft.chentao.BR;

/**
 * 聊天信息
 *
 * @author cusft.chentao
 */
@Entity
public class ChattingMessage extends BaseObservable implements Serializable {

    @Id(autoincrement = true)
    private Long _id;

    /**
     * 用户id
     */
    @Index
    private int userid;
    /**
     * 群id
     */
    @Index
    private int groupid;
    /**
     * TYPE_MSG_TEXT 0 TYPE_MSG_PIC 1
     */
    private int typemsg; // 消息内容，是文字消息还是图片消息
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
    private byte[] image;
    /**
     * 是否发送成功，是的值为View.GONE,否的值为View.VISIBLE
     */
    private int send_success;
    /**
     * 序列号，用来处理是否发送消息成功的信息
     */
    String serial_number;

    @Bindable
    public int getSend_success() {
        return this.send_success;
    }

    public void setSend_success(int send_success) {
        this.send_success = send_success;
        notifyPropertyChanged(BR.send_success);
    }

    @Bindable
    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public int getTypemsg() {
        return this.typemsg;
    }

    public void setTypemsg(int typemsg) {
        this.typemsg = typemsg;
        notifyPropertyChanged(BR.typemsg);
    }

    @Bindable
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
        notifyPropertyChanged(BR.groupid);
    }

    @Bindable
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
        notifyPropertyChanged(BR.userid);
    }

    @Bindable
    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Bindable
    public String getSerial_number() {
        return this.serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
        notifyPropertyChanged(BR.serial_number);
    }

    @Generated(hash = 662744827)
    public ChattingMessage(Long _id, int userid, int groupid, int typemsg, int type, String time,
                           String message, byte[] image, int send_success, String serial_number) {
        this._id = _id;
        this.userid = userid;
        this.groupid = groupid;
        this.typemsg = typemsg;
        this.type = type;
        this.time = time;
        this.message = message;
        this.image = image;
        this.send_success = send_success;
        this.serial_number = serial_number;
    }

    @Generated(hash = 898012662)
    public ChattingMessage() {
    }
}
