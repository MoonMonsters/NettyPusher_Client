package edu.csuft.chentao.fragment;


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
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentChattingListPresenter;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.utils.Constant;

public class ChattingListFragment extends BaseFragment {

    private FragmentChattingListBinding mFragmentBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_chatting_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentChattingListBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        FragmentChattingListPresenter presenter =
                new FragmentChattingListPresenter(mFragmentBinding);
        presenter.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        mReceiver = new ChattingListReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CHATTING_LIST);
        this.getActivity().registerReceiver(mReceiver, filter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.getActivity().unregisterReceiver(mReceiver);
        EventBus.getDefault().unregister(this);
    }

    private class ChattingListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_CHATTING_LIST)) {
                int groupId = intent.getIntExtra(Constant.EXTRA_GROUPID, -1);
                String lastMessage = intent.getStringExtra(Constant.EXTRA_LAST_MESSAGE);

                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_CHATTING_LIST;
                msg.obj = lastMessage;
                msg.arg1 = groupId;
                mHandler.sendMessage(msg);
            }
        }
    }
}
