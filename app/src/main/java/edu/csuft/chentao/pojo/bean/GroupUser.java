package edu.csuft.chentao.pojo.bean;

/**
 * Created by csuft.chentao on 2016-12-29 12:51.
 * email:qxinhai@yeah.net
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 群与用户对应表
 */
@Entity
public class GroupUser {
    
    @Id(autoincrement = true)
    private Long _id;
    /**
     * 群id
     */
    @Unique
    private int groupid;
    /**
     * 用户id
     */
    @Unique
    private int userid;
    public int getUserid() {
        return this.userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public int getGroupid() {
        return this.groupid;
    }
    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    @Generated(hash = 1673886264)
    public GroupUser(Long _id, int groupid, int userid) {
        this._id = _id;
        this.groupid = groupid;
        this.userid = userid;
    }
    @Generated(hash = 1548903865)
    public GroupUser() {
    }

}
