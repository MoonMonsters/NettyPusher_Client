package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * Created by csuft.chentao on 2016-12-23 15:20.
 * email:qxinhai@yeah.net
 */

/**
 * 群信息
 */
@Entity
public class Groups extends BaseObservable implements Serializable {

    @Id(autoincrement = true)
    private Long _id;
    /**
     * 群名
     */
    private String groupname;
    /**
     * 头像
     */
    private byte[] image;
    /**
     * 群id
     */
    @Unique
    private int groupid;
    /**
     * 群人数
     */
    private int number;
    /**
     * 标签
     */
    private String tag;

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    @Generated(hash = 850153698)
    public Groups(Long _id, String groupname, byte[] image, int groupid,
                  int number, String tag) {
        this._id = _id;
        this.groupname = groupname;
        this.image = image;
        this.groupid = groupid;
        this.number = number;
        this.tag = tag;
    }

    @Generated(hash = 893039872)
    public Groups() {
    }


}
