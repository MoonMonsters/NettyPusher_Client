package edu.csuft.chentao.pojo.bean;

/**
 * Created by Chalmers on 2017-01-06 13:47.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 图片的详细信息
 */
public class ImageDetail implements Serializable {

    private byte[] image;

    public ImageDetail(byte[] image) {
        this.image = image;
    }

    public ImageDetail() {
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
