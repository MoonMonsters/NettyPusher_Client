package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuItem;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMainPresenter;
import edu.csuft.chentao.databinding.ActivityMainBinding;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityBinding = null;
    private ActivityMainPresenter mPresenter = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.tlMainBar);

        mPresenter = new ActivityMainPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_group_operation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //菜单
        if (item.getItemId() == R.id.action_group) {
            mPresenter.showOperationPopupWindow();
        }

        return true;
    }

    @Override
    public void executeOnStop() {
        mPresenter.dismissPopupWindow();
    }

    @Override
    public void executeOnDestroy() {
        mPresenter.unregisterEventBus();
    }

    @Override
    public void executeOnStart() {
        mPresenter.setNetStatusFlag();
    }
}