package edu.csuft.chentao.pojo.bean;

import java.io.Serializable;

/**
 * Created by Chalmers on 2017-02-23 21:53.
 * email:qxinhai@yeah.net
 */

/**
 * 通过EventBus向Presenter传输数据
 */
public class EBToPreObject implements Serializable {

    /**
     * 标签，避免传输混乱
     */
    private String tag;

    /**
     * 传输的对象
     */
    private Object object;

    public EBToPreObject(String tag, Object object) {
        this.tag = tag;
        this.object = object;
    }

    public EBToPreObject() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "EBToPreObject{" +
                "tag='" + tag + '\'' +
                ", object=" + object +
                '}';
    }
}
