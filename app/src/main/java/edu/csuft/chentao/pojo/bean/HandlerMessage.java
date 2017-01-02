package edu.csuft.chentao.pojo.bean;

import android.os.Handler;

import java.io.Serializable;

/**
 * Created by Chalmers on 2017-01-02 12:47.
 * email:qxinhai@yeah.net
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
