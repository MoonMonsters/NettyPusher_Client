package edu.csuft.chentao.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.Menu;
import android.view.MenuItem;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMessagePresenter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * @author csuft.chentao
 *         聊天界面
 */
public class MessageActivity extends BaseActivity {

    private ActivityMessageBinding mActivityBinding = null;
    //该聊天群的id
    private int groupId = -1;

    private ActivityMessagePresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_message;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMessageBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        //设置toolbar
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        //获得传递进来的群id
        groupId = this.getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);

        mPresenter = new ActivityMessagePresenter(mActivityBinding, groupId);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public void executeOnDestroy() {
        mPresenter.unregisterEventBus();
    }

    @Override
    public void executeOnStop() {
        /*
        退出聊天界面后，需要把聊天栏的该项的数量置为0，表示该项已经被阅读完成
         */
        mPresenter.updateGroupChattingItems();
        //关闭对话框
        mPresenter.closeAnnouncementDialog();
    }

    @Override
    public void executeOnStart() {
        /*
        进入聊天界面后，需要把聊天栏的该项的数量置为0，表示该项已经被阅读完成
         */
        mPresenter.updateGroupChattingItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_enter_group_detail) {
            Intent intent = new Intent(this, GroupDetailActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, groupId);
            this.startActivity(intent);
        }
        return true;
    }

}