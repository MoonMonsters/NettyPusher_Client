package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;

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
        this.mActivityBinding.setPresenter(mActivityPresenter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityPresenter.unregisterEventBus();
    }
}
