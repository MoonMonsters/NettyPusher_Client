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
import edu.csuft.chentao.controller.presenter.ActivitySearchGroupPresenter;
import edu.csuft.chentao.databinding.ActivitySearchGroupBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;

public class SearchGroupActivity extends BaseActivity {

    private ActivitySearchGroupBinding mActivityBinding = null;
    private Handler mHandler;
    private BroadcastReceiver mReceiver;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_search_group;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivitySearchGroupBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        mActivityBinding.setPresenter(new ActivitySearchGroupPresenter(mActivityBinding));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new SearchGroupReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_SEARCH_GROUP);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getActivityPresenter(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("SearchGroupActivity")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    /**
     * 广播
     */
    private class SearchGroupReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_SEARCH_GROUP)) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_SEARCH_GROUP_SIZE_0;
                msg.obj = intent.getSerializableExtra(Constant.EXTRA_RETURN_INFO);
                mHandler.sendMessage(msg);
            }
        }
    }
}