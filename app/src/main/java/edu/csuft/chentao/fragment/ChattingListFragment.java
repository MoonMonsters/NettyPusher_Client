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
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

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
        //注册广播
        mReceiver = new ChattingListReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CHATTING_LIST);
        filter.addAction(Constant.ACTION_REMOVE_GROUP);
        this.getActivity().registerReceiver(mReceiver, filter);

        EventBus.getDefault().register(this);
        FragmentChattingListPresenter presenter =
                new FragmentChattingListPresenter(mFragmentBinding);
        presenter.init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("ChattingListFragment")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //注销广播
        this.getActivity().unregisterReceiver(mReceiver);
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    private class ChattingListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(); //广播
            if (action.equals(Constant.ACTION_CHATTING_LIST)) {
                LoggerUtil.logger(Constant.TAG, "接收到广播->" + action);
                int type = intent.getIntExtra(Constant.EXTRA_MESSAGE_TYPE, -1);
                //1表示是添加数据
                if (type == 1) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_CHATTING_LIST_ADD;
                    msg.obj = intent.getSerializableExtra(Constant.EXTRA_GROUPSITEM);
                    mHandler.sendMessage(msg);
                } else if (type == 2) { //2表示是更新数据
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_CHATTING_LIST_REFRESH;
                    msg.obj = intent.getSerializableExtra(Constant.EXTRA_GROUPSITEM);
                    mHandler.sendMessage(msg);
                } else if (type == 3) { //3表示是删除数据
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_CHATTING_LIST_DELETE;
                    msg.arg1 = intent.getIntExtra(Constant.EXTRA_POSITION, -1);
                    mHandler.sendMessage(msg);
                }
                //此移除不同于上面的删除，这个是退出群后，在ChattingList中把数据项移除
            } else if (action.equals(Constant.ACTION_REMOVE_GROUP)) {
                int groupId = intent.getIntExtra(Constant.EXTRA_GROUP_ID, -1);
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_REMOVE_GROUP;
                msg.arg1 = groupId;
                mHandler.sendMessage(msg);
            }
        }
    }
}