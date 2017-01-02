package edu.csuft.chentao.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

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
        this.getActivity().registerReceiver(mReceiver, filter);

        this.getActivity().registerForContextMenu(mFragmentBinding.rvChattingListContent);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = this.getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_chatting_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_chatting_list_delete) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //选中项的位置
            int position = menuInfo.position;
            //将位置发送过去，然后删除
            Message msg = mHandler.obtainMessage();
            msg.what = Constant.HANDLER_CHATTING_LIST_DELETE;
            msg.arg1 = position;
            mHandler.sendMessage(msg);
        }

        return true;
    }

    private class ChattingListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(); //广播
            if (action.equals(Constant.ACTION_CHATTING_LIST)) {
                LoggerUtil.logger(Constant.TAG, "接收到广播->" + action);
                int type = intent.getIntExtra(Constant.EXTRA_MESSAGE_TYPE, 1);
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
                }

            }
        }
    }
}