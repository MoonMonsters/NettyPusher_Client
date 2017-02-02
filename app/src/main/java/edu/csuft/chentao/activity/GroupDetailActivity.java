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
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

public class GroupDetailActivity extends BaseActivity {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private int mGroupId = -1;

    private Handler mHandler = null;
    private BroadcastReceiver mReceiver = null;

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
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("GroupDetailActivity")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new GroupDetailReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_GET_USERINFO);
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
            if (action.equals(Constant.ACTION_GET_USERINFO)) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_PRESENTER_REFRESH;
                msg.arg1 = intent.getIntExtra(Constant.EXTRA_USER_ID, -1);
                mHandler.sendMessage(msg);
            }
        }
    }
}