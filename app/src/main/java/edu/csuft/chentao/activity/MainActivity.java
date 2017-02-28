package edu.csuft.chentao.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMainPresenter;
import edu.csuft.chentao.controller.presenter.ItemGroupOperationPresenter;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.databinding.ItemGroupOperationBinding;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityBinding = null;
    private ActivityMainPresenter mPresenter = null;
    private PopupWindow mPopupWindow;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.tlMainBar);

        mPresenter = new ActivityMainPresenter(mActivityBinding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_group_operation, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterEventBus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //菜单
        if (item.getItemId() == R.id.action_group) {

            View view = LayoutInflater.from(this)
                    .inflate(R.layout.item_group_operation, null);
            //弹出菜单
            mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.showAsDropDown(mActivityBinding.tlMainBar);

            //绑定ItemPresenter
            ItemGroupOperationBinding binding =
                    DataBindingUtil.bind(view);
            binding.setItemPresenter(new ItemGroupOperationPresenter(mActivityBinding));
        }

        return true;
    }
}