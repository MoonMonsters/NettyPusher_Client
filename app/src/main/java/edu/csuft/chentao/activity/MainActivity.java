package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mMainBinding = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mMainBinding = (ActivityMainBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        mMainBinding.bnbMainTab.setMode(BottomNavigationBar.MODE_DEFAULT);
        mMainBinding.bnbMainTab.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mMainBinding.bnbMainTab
                .addItem(new BottomNavigationItem(R.drawable.ic_chat_cancel, "聊天")).setActiveColor(R.color.bnbActive)
                .addItem(new BottomNavigationItem(R.drawable.ic_list_cancel, "通讯录")).setActiveColor(R.color.bnbActive)
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_cancel, "我")).setActiveColor(R.color.bnbActive)
                .setFirstSelectedPosition(0)
                .initialise();
    }

    @Override
    public void initListener() {
        mMainBinding.bnbMainTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
