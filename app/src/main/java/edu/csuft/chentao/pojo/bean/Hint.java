package edu.csuft.chentao.pojo.bean;

/**
 * Created by Chalmers on 2017-02-07 16:05.
 * email:qxinhai@yeah.net
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import edu.csuft.chentao.BR;

/**
 * 提示消息
 */
@Entity
public class Hint extends BaseObservable {

    @Id(autoincrement = true)
    @Index
    private Long _id;
    /**
     * 头像
     */
    private byte[] image;
    /**
     * 数据类型
     */
    private int type;
    /**
     * 显示类型
     */
    private int show;
    /**
     * 群名称
     */
    private String groupname;
    /**
     * 描述
     */
    private String description;
    /**
     * 群id
     */
    private int groupid;
    /**
     * 待处理消息的用户id
     */
    private int userid;

    @Bindable
    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
        notifyPropertyChanged(BR.userid);
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
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
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
    public int getShow() {
        return this.show;
    }

    public void setShow(int show) {
        this.show = show;
        notifyPropertyChanged(BR.show);
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
    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Generated(hash = 1498400779)
    public Hint(Long _id, byte[] image, int type, int show, String groupname,
                String description, int groupid, int userid) {
        this._id = _id;
        this.image = image;
        this.type = type;
        this.show = show;
        this.groupname = groupname;
        this.description = description;
        this.groupid = groupid;
        this.userid = userid;
    }

    @Generated(hash = 184314319)
    public Hint() {
    }


}
