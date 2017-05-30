package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivitySearchGroupPresenter;
import edu.csuft.chentao.databinding.ActivitySearchGroupBinding;

public class SearchGroupActivity extends BaseActivity {

    private ActivitySearchGroupBinding mActivityBinding = null;
    private ActivitySearchGroupPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_search_group;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivitySearchGroupBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mPresenter = new ActivitySearchGroupPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnDestroy() {
        mPresenter.unregisterEventBus();
    }

}