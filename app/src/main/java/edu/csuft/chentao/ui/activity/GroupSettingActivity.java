package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityGroupSettingPresenter;
import edu.csuft.chentao.databinding.ActivityGroupSettingBinding;

public class GroupSettingActivity extends BaseActivity {

    private ActivityGroupSettingBinding mActivityBinding;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_group_setting;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityGroupSettingBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mActivityBinding.setVariable(BR.presenter, new ActivityGroupSettingPresenter(mActivityBinding));
    }
}
