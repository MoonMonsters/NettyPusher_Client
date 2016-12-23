package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.Arrays;

import edu.csuft.chentao.BR;

/**
 * Created by Chalmers on 2016-12-22 17:50.
 * email:qxinhai@yeah.net
 */

@Entity
public class GroupChattingItem extends BaseObservable {

    @Index
    @Id(autoincrement = true)
    private Long _id;
    /**
     * 群名称
     */
    private String groupname;
    /**
     * 群id
     */
    private int groupid;
    /**
     * 最后一条消息
     */
    private String lastmessage;
    /**
     * 图片byte数组
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
    public String getLastmessage() {
        return this.lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
        notifyPropertyChanged(BR.lastmessage);
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
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
        notifyPropertyChanged(BR.groupname);
    }

    @Bindable
    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Generated(hash = 116598526)
    public GroupChattingItem(Long _id, String groupname, int groupid,
                             String lastmessage, byte[] image) {
        this._id = _id;
        this.groupname = groupname;
        this.groupid = groupid;
        this.lastmessage = lastmessage;
        this.image = image;
    }

    @Generated(hash = 1037461491)
    public GroupChattingItem() {
    }

    @Override
    public String toString() {
        return "GroupChattingItem{" +
                "_id=" + _id +
                ", groupname='" + groupname + '\'' +
                ", groupid=" + groupid +
                ", lastmessage='" + lastmessage + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
