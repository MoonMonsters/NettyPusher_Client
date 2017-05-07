package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityUserInfoPresenter;
import edu.csuft.chentao.databinding.ActivityUserInfoBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * @author csuft.chentao
 *         用户信息界面
 */
public class UserInfoActivity extends BaseActivity {

    private ActivityUserInfoBinding mActivityBinding = null;
    private ActivityUserInfoPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivityUserInfoBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);

        int userId = getIntent().getIntExtra(Constant.EXTRA_USER_ID, -1);
        int groupId = getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);

        mPresenter = new ActivityUserInfoPresenter(mActivityBinding, groupId, userId);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unregisterEventBus();
    }
}