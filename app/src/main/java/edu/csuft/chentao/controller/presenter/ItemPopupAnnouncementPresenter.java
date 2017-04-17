package edu.csuft.chentao.controller.presenter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.databinding.ItemPopupAnnouncementsBinding;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.utils.LoggerUtil;

public class ItemPopupAnnouncementPresenter {

    private ActivityMessageBinding mActivityBinding;
    private ItemPopupAnnouncementsBinding mItemBinding;
    private List<LocalAnnouncement> mLocalAnnouncementList;
    private int mAnnouncementIndex;

    public ItemPopupAnnouncementPresenter(ActivityMessageBinding activityBinding, List<LocalAnnouncement> localAnnouncementList) {
        this.mActivityBinding = activityBinding;
        mItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivityBinding.getRoot().getContext()), R.layout.item_popup_announcements,
                null, false);
        this.mLocalAnnouncementList = localAnnouncementList;

    }

}
