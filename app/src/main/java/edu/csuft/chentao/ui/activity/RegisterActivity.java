package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * @author csuft.chentao
 *         注册界面
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mActivityBinding = null;
    private ActivityRegisterPresenter mActivityPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityRegisterBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mActivityPresenter = new ActivityRegisterPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mActivityPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoggerUtil.logger(Constant.TAG, "RegisterActivity----onDestroy");
        mActivityPresenter.unregisterEventBus();
    }
}
