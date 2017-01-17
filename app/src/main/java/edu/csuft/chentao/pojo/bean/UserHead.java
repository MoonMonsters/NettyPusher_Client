package edu.csuft.chentao.pojo.bean;

/**
 * Created by csuft.chentao on 2016-12-24 20:43.
 * email:qxinhai@yeah.net
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import edu.csuft.chentao.BR;

/**
 * 存储用户头像
 */
@Entity
public class UserHead extends BaseObservable {

    @Id(autoincrement = true)
    private Long _id;
    /**
     * 用户id
     */
    @Index
    private int userid;
    /**
     * 用户头像
     */
    private byte[] image;

    @Bindable
    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
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

    @Generated(hash = 1868728870)
    public UserHead(Long _id, int userid, byte[] image) {
        this._id = _id;
        this.userid = userid;
        this.image = image;
    }

    @Generated(hash = 2093598119)
    public UserHead() {
    }

}
