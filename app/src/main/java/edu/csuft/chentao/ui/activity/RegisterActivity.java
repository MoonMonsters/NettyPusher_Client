package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.utils.OperationUtil;

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

        OperationUtil.fullScreen(this);

        mActivityPresenter = new ActivityRegisterPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mActivityPresenter);
    }

    @Override
    public void executeOnDestroy() {
        mActivityPresenter.unregisterEventBus();
    }

    @Override
    public void onBackPressed() {
        mActivityPresenter.onClickForCancel();
    }

}
