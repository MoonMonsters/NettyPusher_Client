package edu.csuft.chentao.controller.presenter;

import android.support.v7.app.AlertDialog;

import edu.csuft.chentao.databinding.ItemPopupAnnouncementsBinding;
import edu.csuft.chentao.utils.LoggerUtil;

public class ItemPopupAnnouncementPresenter {

    private ItemPopupAnnouncementsBinding mItemBinding;
    private AlertDialog mDialog;

    public ItemPopupAnnouncementPresenter(ItemPopupAnnouncementsBinding itemBinding, AlertDialog dialog) {
        this.mItemBinding = itemBinding;
        this.mDialog = dialog;
    }

    /**
     * 进入公告界面
     */
    public void doClickToEnterAnnouncementActivity() {
        LoggerUtil.showToast(mItemBinding.getRoot().getContext(), "进入公告界面");
    }

    /**
     * 隐藏对话框
     */
    public void doClickToHideDialog() {
        mDialog.dismiss();
    }


}
