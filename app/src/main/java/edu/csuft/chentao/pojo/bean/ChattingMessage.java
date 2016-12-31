package edu.csuft.chentao.pojo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

import edu.csuft.chentao.BR;

/**
 * 聊天信息
 * 
 * @author cusft.chentao
 *
 */
@Entity
public class ChattingMessage extends BaseObservable implements Serializable{

	@Index
	@Id(autoincrement = true)
	Long _id;

	/** 用户id */
	int userid;
	/**
	 * 群id
	 */
	int groupid;
	/**
	 * TYPE_MSG_TEXT 0 TYPE_MSG_PIC 1
	 */
	int typemsg; // 消息内容，是文字消息还是图片消息
	/**
	 * TYPE_MSG_SEND 0 TYPE_MSG_RECV 1
	 */
	int type; // 是发送还是接收
	/** 消息发送时间 */
	String time;
	/** 发送的文字内容 */
	String message;
	/** 发送的图片 */
	byte[] image;

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
	@Generated(hash = 2002286337)
	public ChattingMessage(Long _id, int userid, int groupid, int typemsg,
			int type, String time, String message, byte[] image) {
		this._id = _id;
		this.userid = userid;
		this.groupid = groupid;
		this.typemsg = typemsg;
		this.type = type;
		this.time = time;
		this.message = message;
		this.image = image;
	}
	@Generated(hash = 898012662)
	public ChattingMessage() {
	}

}
