package edu.csuft.chentao.ui.activity;

import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityGroupDetailPresenter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;

/**
 * 详细群数据
 */
public class GroupDetailActivity extends BaseActivity {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private PopupWindow mPopupWindow;
    private ActivityGroupDetailPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_group_detail;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivityGroupDetailBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        //设置toolbar
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        mPresenter = new ActivityGroupDetailPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnStop() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void executeOnDestroy() {
        mPresenter.unregisterEventBus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.mene_group_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_group_detail_setting) {
            //点击菜单栏进入下一个界面去
            mPresenter.enterGroupSettingActivity();
        }

        return true;
    }
}