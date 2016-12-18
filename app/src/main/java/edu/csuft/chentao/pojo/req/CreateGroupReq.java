package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 *
 * @author csuft.chentao
 *
 * 2016年12月8日 下午9:49:23
 */
public class CreateGroupReq implements Serializable{

    private static final long serialVersionUID = 1L;
    /** 群名 */
    private String groupname;
    /** 群标签 */
    private String tag;
    /** 群头像 */
    private HeadImage headImage;

    public CreateGroupReq(String groupname, String tag, HeadImage headImage) {
        super();
        this.groupname = groupname;
        this.tag = tag;
        this.headImage = headImage;
    }

    public CreateGroupReq() {
        super();
    }

    public final String getGroupname() {
        return groupname;
    }

    public final void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public final String getTag() {
        return tag;
    }

    public final void setTag(String tag) {
        this.tag = tag;
    }

    public final HeadImage getHeadImage() {
        return headImage;
    }

    public final void setHeadImage(HeadImage headImage) {
        this.headImage = headImage;
    }

    @Override
    public String toString() {
        return "CreateGrourpReq [groupname=" + groupname + ", tag=" + tag
                + ", headImage=" + headImage + "]";
    }

}
