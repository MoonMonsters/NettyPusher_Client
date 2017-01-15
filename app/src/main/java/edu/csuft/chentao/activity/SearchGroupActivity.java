package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivitySearchGroupBinding;

public class SearchGroupActivity extends BaseActivity {

    private ActivitySearchGroupBinding mActivityBinding = null;

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

    }
}