package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Arrays;

/**
 * Created by Chalmers on 2016-12-23 15:20.
 * email:qxinhai@yeah.net
 */

@Entity
public class Groups extends BaseObservable {

    @Index
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
    private int groupid;

    @Bindable
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
        notifyPropertyChanged(BR.groupid);
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
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
        notifyPropertyChanged(edu.csuft.chentao.BR.groupname);
    }

    @Bindable
    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Generated(hash = 30997816)
    public Groups(Long _id, String groupname, byte[] image, int groupid) {
        this._id = _id;
        this.groupname = groupname;
        this.image = image;
        this.groupid = groupid;
    }

    @Generated(hash = 893039872)
    public Groups() {
    }

    @Override
    public String toString() {
        return "Groups{" +
                "_id=" + _id +
                ", groupname='" + groupname + '\'' +
                ", image=" + Arrays.toString(image) +
                ", groupid=" + groupid +
                '}';
    }
}
