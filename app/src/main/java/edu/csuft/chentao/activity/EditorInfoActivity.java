package edu.csuft.chentao.activity;

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
        mPresenter =
                new ActivityEditorInfoPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        mPresenter.unregisterEventBus();
    }
}