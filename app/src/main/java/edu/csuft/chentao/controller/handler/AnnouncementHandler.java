package edu.csuft.chentao.controller.handler;

import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.LocalAnnouncementDaoUtil;

/**
 * Announcement的处理
 */

public class AnnouncementHandler implements Handler {

    @Override
    public void handle(Object object) {
        Announcement announcement = (Announcement) object;
        LocalAnnouncement la = CopyUtil.announcementToLocalAnnouncement(announcement);
        LocalAnnouncementDaoUtil.insert(la);

        OperationUtil.sendEBToObjectPresenter(Constant.TAG_ANNOUNCEMENT_PRESENTER, la);
    }
}
