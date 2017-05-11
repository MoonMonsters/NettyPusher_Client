package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityEditorInfoPresenter;
import edu.csuft.chentao.databinding.ActivityEditorInfoBinding;

public class EditorInfoActivity extends BaseActivity {

    private ActivityEditorInfoBinding mActivityBinding = null;
    private ActivityEditorInfoPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_editor_info;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityEditorInfoBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mPresenter = new ActivityEditorInfoPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnDestroy() {
        //注销EventBus
        mPresenter.unregisterEventBus();
    }
}