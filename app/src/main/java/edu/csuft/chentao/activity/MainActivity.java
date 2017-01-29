package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMainPresenter;
import edu.csuft.chentao.controller.presenter.ItemGroupOperationPresenter;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.databinding.ItemGroupOperationBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityBinding = null;

    private MainReceiver mReceiver = null;

    PopupWindow mPopupWindow;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOGIN);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
        if(mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.tlMainBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new ActivityMainPresenter(mActivityBinding).init();
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
            binding.setItemPresenter(new ItemGroupOperationPresenter());
        }

        return true;
    }

    public class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) {
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                if (isSuccess) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}