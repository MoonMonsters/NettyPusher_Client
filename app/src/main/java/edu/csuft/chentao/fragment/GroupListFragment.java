package edu.csuft.chentao.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentGroupListPresenter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * @author cusft.chentao
 */
public class GroupListFragment extends BaseFragment {

    private FragmentGroupListBinding mFragmentBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_group_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentGroupListBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        EventBus.getDefault().register(this);
        FragmentGroupListPresenter presenter = new FragmentGroupListPresenter(mFragmentBinding);
        presenter.init();
        mFragmentBinding.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mReceiver = new GroupListReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_REMOVE_GROUP);
        this.getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        this.getActivity().unregisterReceiver(mReceiver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("GroupListFragment")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    public class GroupListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LoggerUtil.logger(Constant.TAG, "GroupListFragment-->" + action);
            if (action.equals(Constant.ACTION_REMOVE_GROUP)) {
                int groupId = intent.getIntExtra(Constant.EXTRA_GROUP_ID, -1);
                android.os.Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_REMOVE_GROUP;
                msg.arg1 = groupId;
                mHandler.sendMessage(msg);
            }
        }
    }
}