package edu.csuft.chentao.controller.presenter;

import android.view.View;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityPublishAnnouncementBinding;
import edu.csuft.chentao.pojo.req.Announcement;
import edu.csuft.chentao.ui.activity.PublishAnnouncementActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * @author edu.csuft.chentao
 *         PublishAnnouncementActivity的处理事务类
 */

public class ActivityPublishAnnouncementPresenter extends BasePresenter {

    private ActivityPublishAnnouncementBinding mActivityBinding;
    private int mGroupId;

    public ActivityPublishAnnouncementPresenter(ActivityPublishAnnouncementBinding activityBinding) {
        this.mActivityBinding = activityBinding;

        init();
    }

    @Override
    protected void initData() {
        mGroupId = ((PublishAnnouncementActivity) mActivityBinding.getRoot().getContext())
                .getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        mActivityBinding.setVariable(BR.title, OperationUtil.getString(mActivityBinding, R.string.string_publish_new_annouoncement));
    }

    /**
     * 标题内容改变监听事件，主要用来判断最小字数
     */
    public void onTextChangedForTitle(CharSequence s, int start, int before, int count) {
        if (s.length() < 4) {
            mActivityBinding.etPublishAnnouncementTitle.setError(OperationUtil.getString(mActivityBinding, R.string.string_publish_announcement_error_title));
            mActivityBinding.etPublishAnnouncementTitle.setErrorEnabled(true);
        } else {
            mActivityBinding.etPublishAnnouncementTitle.setError(null);
            mActivityBinding.etPublishAnnouncementTitle.setErrorEnabled(false);
        }
    }

    /**
     * 正文内容改变监听事件，主要用来判断最小字数
     */
    public void onTextChangedForContent(CharSequence s, int start, int before, int count) {
        if (s.length() < 15) {
            mActivityBinding.etPublishAnnouncementContent.setError(OperationUtil.getString(mActivityBinding, R.string.string_publish_announcement_error_content));
            mActivityBinding.etPublishAnnouncementContent.setErrorEnabled(true);
        } else {
            mActivityBinding.etPublishAnnouncementContent.setError(null);
            mActivityBinding.etPublishAnnouncementContent.setErrorEnabled(false);
        }
    }

    /**
     * 点击按钮发布新公告
     */
    public void doClickToPublishNewAnnouncement() {
        String title = mActivityBinding.etPublishAnnouncementTitle.getEditText().getText().toString();
        String content = mActivityBinding.etPublishAnnouncementContent.getEditText().getText().toString();
        if (title.length() < 4 || content.length() < 15) {
            LoggerUtil.showToast(mActivityBinding.getRoot().getContext(), "输入不符合要求");

            return;
        }

        /*
        构建Announcement对象
         */
        Announcement announcement = new Announcement();
        announcement.setUserid(SharedPrefUserInfoUtil.getUserId());
        announcement.setTitle(title);
        announcement.setTime(OperationUtil.getCurrentTime());
        announcement.setGroupid(mGroupId);
        announcement.setSerialnumber(OperationUtil.getImageName());
        announcement.setUsername(SharedPrefUserInfoUtil.getUsername());
        announcement.setContent(content);

        //发送消息到服务端
        SendMessageUtil.sendMessage(announcement);

        finishActivity();
    }

    @Override
    public void initListener() {
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    /**
     * 关闭当前界面
     */
    private void finishActivity() {
        ((PublishAnnouncementActivity) mActivityBinding.getRoot().getContext()).finish();
    }
}
