package edu.csuft.chentao.controller.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.MainActivity;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.fragment.FragmentFactory;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-28 17:26.
 * email:qxinhai@yeah.net
 */

public class ActivityMainPresenter {

    private ActivityMainBinding mActivityBinding = null;
    private static MainActivity mActivity = null;
    private Fragment mChattingListFragment = null;
    private Fragment mGroupListFragment = null;
    private Fragment mMineFragment = null;

    public ActivityMainPresenter(ActivityMainBinding binding) {
        this.mActivityBinding = binding;
        mActivity = (MainActivity) mActivityBinding.getRoot().getContext();
    }

    public void init() {
        initFragmentData();
        addAllFragmentData();
        setBottomNavigationBarData();
        setBottomNavigationBarListener();
    }

    /**
     * 把所有的Fragment都加入到Manager中，并且同时显示ChattingListFragment
     */
    private void addAllFragmentData() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_content, mChattingListFragment)
                .add(R.id.fl_main_content, mGroupListFragment)
                .add(R.id.fl_main_content, mMineFragment)
                .commit();
        showChattingListFragment();
    }

    /**
     * 初始化Fragment数据
     */
    private void initFragmentData() {
        mChattingListFragment = FragmentFactory.getInstance(FragmentFactory.CHATTING_LIST_FRAGMENT);
        mGroupListFragment = FragmentFactory.getInstance(FragmentFactory.GROUP_LIST_FRAGMENT);
        mMineFragment = FragmentFactory.getInstance(FragmentFactory.MINE_FRAGMENT);
    }

    /**
     * 设置BNB的数据
     */
    private void setBottomNavigationBarData() {
        //设置模式
        mActivityBinding.bnbMainTab.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置样式
        mActivityBinding.bnbMainTab.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //添加选项
        mActivityBinding.bnbMainTab
                .addItem(new BottomNavigationItem(R.drawable.ic_chat_cancel, "聊天")).setActiveColor(R.color.bnbActive)
                .addItem(new BottomNavigationItem(R.drawable.ic_list_cancel, "通讯录")).setActiveColor(R.color.bnbActive)
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_cancel, "我")).setActiveColor(R.color.bnbActive)
                //默认选中第0个
                .setFirstSelectedPosition(0)
                //实例化，一定要加上该方法
                .initialise();
    }

    /**
     * 设置BNB的监听器
     */
    private void setBottomNavigationBarListener() {
        //选择监听器
        mActivityBinding.bnbMainTab.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        showChattingListFragment();
                        break;
                    case 1:
                        showGroupListFragment();
                        break;
                    case 2:
                        showMineFragment();
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

    /**
     * 显示ChattingListFragment
     */
    private void showChattingListFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .show(mChattingListFragment)
                .hide(mGroupListFragment)
                .hide(mMineFragment)
                .commit();
    }

    /**
     * 显示GroupListFragment
     */
    private void showGroupListFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .show(mGroupListFragment)
                .hide(mChattingListFragment)
                .hide(mMineFragment)
                .commit();
    }

    /**
     * 显示MineFragment
     */
    private void showMineFragment() {
        mActivity.getSupportFragmentManager().beginTransaction()
                .show(mMineFragment)
                .hide(mChattingListFragment)
                .hide(mGroupListFragment)
                .commit();
    }

    public static class MainReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN)) {
                boolean isSuccess = intent.getBooleanExtra(Constant.IS_LOGIN_SUCCESS, false);
                if (isSuccess) {
                    Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
