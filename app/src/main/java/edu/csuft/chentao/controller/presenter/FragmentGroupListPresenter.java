package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

/**
 * Created by Chalmers on 2016-12-28 17:46.
 * email:qxinhai@yeah.net
 */

/**
 * 群列表--GroupListFragment的Presenter
 */
public class FragmentGroupListPresenter {

    private FragmentGroupListBinding mFragmentBinding = null;
    private Context mContext;

    private List<Groups> mGroupsList = null;
    private GroupListAdapter mAdapter = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_GROUPLIST) {

            }
        }
    };

    public FragmentGroupListPresenter(FragmentGroupListBinding fragmentBinding) {
        EventBus.getDefault().register(this);
        this.mFragmentBinding = fragmentBinding;
        mContext = mFragmentBinding.getRoot().getContext();
        EventBus.getDefault().post(new HandlerMessage(mHandler, "GroupListFragment"));
    }

    public void init() {
        initData();
    }

    private void initData() {
        mGroupsList = GroupsDaoUtil.loadAll();
        mAdapter = new GroupListAdapter(mFragmentBinding.getRoot().getContext(), mGroupsList);

        mFragmentBinding.rvGroupListContent.setLayoutManager(new LinearLayoutManager(mContext));
        mFragmentBinding.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusGroups(Groups groups) {
        LoggerUtil.logger(Constant.TAG, "FragmentGroupListPresenter-->接收到群数据");
        mGroupsList.add(groups);
        mAdapter.notifyDataSetChanged();
    }
}