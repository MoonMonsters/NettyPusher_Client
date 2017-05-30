package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuItem;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityAnnouncementPresenter;
import edu.csuft.chentao.databinding.ActivityAnnouncementBinding;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

public class AnnouncementActivity extends BaseActivity {

    private ActivityAnnouncementBinding mActivityBinding;

    private ActivityAnnouncementPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_announcement;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityAnnouncementBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mPresenter = new ActivityAnnouncementPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        LoggerUtil.logger("TAG", getIntent().getIntExtra(Constant.EXTRA_USER_CAPITAL, -1) + "");

        if (getIntent().getIntExtra(Constant.EXTRA_USER_CAPITAL, -1) == Constant.TYPE_GROUP_CAPITAL_ADMIN
                || getIntent().getIntExtra(Constant.EXTRA_USER_CAPITAL, -1) == Constant.TYPE_GROUP_CAPITAL_OWNER) {
            getMenuInflater().inflate(R.menu.menu_announcement, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_publish_announcement) {
            //发布新公告
            mPresenter.doMenuToPublishNewAnnouncement();
        }

        return true;
    }

}
