package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
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
            Groups groups = (Groups) msg.obj;

            int index = -1;
            for (int i = 0; i < mGroupsList.size(); i++) {
                if (groups.getGroupid() == mGroupsList.get(i).getGroupid()) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                mGroupsList.add(groups);
            } else {
                mGroupsList.remove(index);
                mGroupsList.add(groups);
            }

            mAdapter.notifyDataSetChanged();
        }
    };

    public FragmentGroupListPresenter(FragmentGroupListBinding fragmentBinding) {
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

    /**
     * 菜单栏的Presenter,负责加入群，搜索群和创建群操作
     */
    public static class ItemGroupOperationPresenter {

        public void onClickToAddGroup() {
            Toast.makeText(MyApplication.getInstance(), "onClickToAddGroup", Toast.LENGTH_SHORT).show();
        }

        public void onClickToSearchGroup() {
            Toast.makeText(MyApplication.getInstance(), "onClickToSearchGroup", Toast.LENGTH_SHORT).show();
        }

        public void onClickToCreateGroup() {
            Toast.makeText(MyApplication.getInstance(), "onClickToCreateGroup", Toast.LENGTH_SHORT).show();
        }

    }
}