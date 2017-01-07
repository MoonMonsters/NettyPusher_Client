package edu.csuft.chentao.pojo.bean;

import android.os.Handler;

import java.io.Serializable;

/**
 * Created by csuft.chentao on 2017-01-02 12:47.
 * email:qxinhai@yeah.net
 */

/**
 * 在Activity or Fragment与Presenter之间传递Handler，避免数据混乱
 */
public class HandlerMessage implements Serializable {

    private String tag;
    private Handler handler;

    public HandlerMessage(Handler handler, String tag) {
        this.tag = tag;
        this.handler = handler;
    }

    public HandlerMessage() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
