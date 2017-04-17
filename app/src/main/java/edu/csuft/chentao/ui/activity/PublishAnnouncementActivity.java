package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityPublishAnnouncementPresenter;
import edu.csuft.chentao.databinding.ActivityPublishAnnouncementBinding;

public class PublishAnnouncementActivity extends BaseActivity {

    private ActivityPublishAnnouncementBinding mActivityBinding;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_publish_announcement;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityPublishAnnouncementBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mActivityBinding.setVariable(BR.presenter, new ActivityPublishAnnouncementPresenter(mActivityBinding));
    }
}
