package edu.csuft.chentao.ui.activity;

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

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityGroupDetailPresenter;
import edu.csuft.chentao.controller.presenter.ItemGroupDetailPopupPresenter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.databinding.ItemGroupDetailPopupBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

/**
 * 详细群数据
 */
public class GroupDetailActivity extends BaseActivity {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private int mGroupId = -1;
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
        setSupportActionBar(mActivityBinding.tlGroupDetailBar);
        mGroupId = getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        Groups groups = GroupsDaoUtil.getGroups(mGroupId);
        mPresenter = new ActivityGroupDetailPresenter(mActivityBinding, mGroupId);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
        mActivityBinding.setVariable(BR.groups, groups);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        if (item.getItemId() == R.id.action_group_detail_more) {
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.item_group_detail_popup, null);
            //弹出菜单
            mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.showAsDropDown(mActivityBinding.tlGroupDetailBar);

            ItemGroupDetailPopupBinding binding =
                    DataBindingUtil.bind(view);
            ItemGroupDetailPopupPresenter itemPresenter =
                    new ItemGroupDetailPopupPresenter(mActivityBinding, mPopupWindow);
            itemPresenter.setGroupId(mGroupId);
            binding.setVariable(BR.itemPresenter, itemPresenter);
        }

        return true;
    }
}