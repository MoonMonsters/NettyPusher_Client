package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import edu.csuft.chentao.BR;

/**
 * 存储在手机端的公告
 */
@Entity
public class LocalAnnouncement extends BaseObservable {

    @Index
    @Id(autoincrement = true)
    private Long _id;

    /**
     * 编号，预留，如果以后做删除操作用的着
     */
    private String serialnumber;

    /**
     * 群id
     */
    private int groupid;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布者名字，用作预备名字，如果根据用户id在用户数据库中没有搜索到，则使用该名字
     */
    private String username;

    /**
     * 发布者用户id
     */
    private int userid;

    /**
     * 发布时间
     */
    private String time;

    /**
     * 是否为最新的公告
     */
    private boolean isnew;

    @Bindable
    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
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
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getSerialnumber() {
        return this.serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
        notifyPropertyChanged(BR.serialnumber);
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
    public boolean getIsnew() {
        return this.isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
        notifyPropertyChanged(BR.isnew);
    }

    @Bindable
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
        notifyPropertyChanged(BR.groupid);
    }

    @Generated(hash = 1129315953)
    public LocalAnnouncement(Long _id, String serialnumber, int groupid, String title,
                             String content, String username, int userid, String time, boolean isnew) {
        this._id = _id;
        this.serialnumber = serialnumber;
        this.groupid = groupid;
        this.title = title;
        this.content = content;
        this.username = username;
        this.userid = userid;
        this.time = time;
        this.isnew = isnew;
    }

    @Generated(hash = 1666673170)
    public LocalAnnouncement() {
    }
}
