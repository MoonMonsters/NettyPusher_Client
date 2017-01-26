package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import edu.csuft.chentao.BR;

/**
 * Created by csuft.chentao on 2016-12-24 20:45.
 * email:qxinhai@yeah.net
 */

/**
 * 存储用户信息
 */
@Entity
public class UserInfo extends BaseObservable {

    @Id(autoincrement = true)
    private Long _id;
    /**
     * 用户id
     */
    @Unique
    private int userid;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 个性签名
     */
    private String signature;

    @Bindable
    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
        notifyPropertyChanged(BR.signature);
    }

    @Bindable
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
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

    @Generated(hash = 950903872)
    public UserInfo(Long _id, int userid, String nickname, String signature) {
        this._id = _id;
        this.userid = userid;
        this.nickname = nickname;
        this.signature = signature;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

}
