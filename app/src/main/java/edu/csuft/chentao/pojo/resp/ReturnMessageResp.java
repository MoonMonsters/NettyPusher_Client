/**
 *
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *         <p>
 *         2016年12月11日 下午8:52:29
 */
public class ReturnMessageResp implements Serializable {

    /**
     * 类型，成功or失败
     */
    private int type;
    /**
     * 对类型的描述
     */
    private String description;

    public ReturnMessageResp(int type, String description) {
        super();
        this.type = type;
        this.description = description;
    }

    public ReturnMessageResp() {
        super();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReturnMessageResp [type=" + type + ", description="
                + description + "]";
    }

}
