package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityCreateGroupPresenter;
import edu.csuft.chentao.databinding.ActivityCreateGroupBinding;

public class CreateGroupActivity extends BaseActivity {

    private ActivityCreateGroupBinding mActivityBinding;
    private ActivityCreateGroupPresenter mPresenter;


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivityCreateGroupBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new ActivityCreateGroupPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterEventBus();
    }
}
