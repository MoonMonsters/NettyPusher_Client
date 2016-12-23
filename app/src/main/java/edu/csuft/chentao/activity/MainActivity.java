package edu.csuft.chentao.activity;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.fragment.FragmentFactory;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityBinding = null;

    private Fragment mChattingListFragment = null;
    private Fragment mGroupListFragment = null;
    private Fragment mMineFragment = null;

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
        setBottomNavigationBarData();
        initFragmentData();
        addAllFragmentData();
    }

    @Override
    public void initListener() {
        setBottomNavigationBarListener();
    }

    /**
     * 把所有的Fragment都加入到Manager中，并且同时显示ChattingListFragment
     */
    public void addAllFragmentData() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_main_content, mChattingListFragment)
                .add(R.id.fl_main_content, mGroupListFragment)
                .add(R.id.fl_main_content, mMineFragment)
                .commit();
        showChattingListFragment();
    }

    /**
     * 初始化Fragment数据
     */
    public void initFragmentData() {
        mChattingListFragment = FragmentFactory.getInstance(FragmentFactory.CHATTING_LIST_FRAGMENT);
        mGroupListFragment = FragmentFactory.getInstance(FragmentFactory.GROUP_LIST_FRAGMENT);
        mMineFragment = FragmentFactory.getInstance(FragmentFactory.MINE_FRAGMENT);
    }

    /**
     * 设置BNB的数据
     */
    public void setBottomNavigationBarData() {
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
        getSupportFragmentManager().beginTransaction()
                .show(mChattingListFragment)
                .hide(mGroupListFragment)
                .hide(mMineFragment)
                .commit();
    }

    /**
     * 显示GroupListFragment
     */
    private void showGroupListFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mGroupListFragment)
                .hide(mChattingListFragment)
                .hide(mMineFragment)
                .commit();
    }

    /**
     * 显示MineFragment
     */
    private void showMineFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mMineFragment)
                .hide(mChattingListFragment)
                .hide(mGroupListFragment)
                .commit();
    }
}
