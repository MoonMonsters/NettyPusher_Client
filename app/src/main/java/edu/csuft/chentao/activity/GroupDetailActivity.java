package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityGroupDetailPresenter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

public class GroupDetailActivity extends BaseActivity {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private int mGroupId = -1;
    private Handler mHandler = null;
    private BroadcastReceiver mReceiver;

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
        EventBus.getDefault().register(this);
        mGroupId = getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        Groups groups = GroupsDaoUtil.getGroups(mGroupId);
        ActivityGroupDetailPresenter presenter =
                new ActivityGroupDetailPresenter(mActivityBinding);
        presenter.init();
        mActivityBinding.setPresenter(presenter);
        mActivityBinding.setGroups(groups);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getActivityPresenter(HandlerMessage message) {
        if (message.getTag().equals("GroupDetailActivity")) {
            mHandler = message.getHandler();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new GroupDetailReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_GROUP_DATAIL);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    private class GroupDetailReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LoggerUtil.logger(Constant.TAG, action);
            if (action.equals(Constant.ACTION_GROUP_DATAIL)) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_GROUP_DETAIL;
                msg.obj = intent.getSerializableExtra(Constant.EXTRA_USER_IDS_IN_GROUP);
                mHandler.sendMessage(msg);
            }
        }
    }

}