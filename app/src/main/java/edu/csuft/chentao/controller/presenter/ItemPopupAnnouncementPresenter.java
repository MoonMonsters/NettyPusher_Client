package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;

import edu.csuft.chentao.databinding.ItemPopupAnnouncementsBinding;
import edu.csuft.chentao.ui.activity.AnnouncementActivity;
import edu.csuft.chentao.utils.Constant;

public class ItemPopupAnnouncementPresenter {

    private ItemPopupAnnouncementsBinding mItemBinding;
    private AlertDialog mDialog;
    private int mGroupId;

    public ItemPopupAnnouncementPresenter(ItemPopupAnnouncementsBinding itemBinding, AlertDialog dialog, int groupId) {
        this.mItemBinding = itemBinding;
        this.mDialog = dialog;
        this.mGroupId = groupId;
    }

    /**
     * 进入公告界面
     */
    public void doClickToEnterAnnouncementActivity() {
        Intent intent = new Intent(mItemBinding.getRoot().getContext(), AnnouncementActivity.class);
        intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
        mItemBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * 隐藏对话框
     */
    public void doClickToHideDialog() {
        mDialog.dismiss();
    }


}
