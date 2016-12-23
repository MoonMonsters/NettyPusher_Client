package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.req.HeadImage;

/**
 * Created by Chalmers on 2016-12-22 14:22.
 * email:qxinhai@yeah.net
 */

public class HeadImageHandler implements Handler {
    @Override
    public void handle(Object object) {
        HeadImage headImage = (HeadImage) object;
    }
}
