package edu.csuft.chentao.controller.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import edu.csuft.chentao.activity.MessageActivity;
import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.DaoSessionUtil;

/**
 * Created by Chalmers on 2016-12-28 17:46.
 * email:qxinhai@yeah.net
 */

public class FragmentGroupListPresenter implements GroupListAdapter.OnItemClick {

    private FragmentGroupListBinding mFragmentBinding = null;
    private Context mContext;

    private static List<Groups> mGroupsList = null;
    private static GroupListAdapter mAdapter = null;

    public FragmentGroupListPresenter(FragmentGroupListBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
        mContext = mFragmentBinding.getRoot().getContext();
    }

    public void init() {
        initData();
    }

    private void initData() {
        mGroupsList = DaoSessionUtil.getGroupsList();
        mAdapter = new GroupListAdapter(mGroupsList, this);

        mFragmentBinding.rvGroupListContent.setLayoutManager(new LinearLayoutManager(mContext));
        mFragmentBinding.rvGroupListContent.setAdapter(mAdapter);
    }

    @Override
    public void onItemClickListener(int position) {
        Intent intent = new Intent(mContext, MessageActivity.class);
        intent.putExtra(Constant.EXTRA_GROUPID, mGroupsList.get(position).getGroupid());
        mContext.startActivity(intent);
    }

    public static class GroupListReceiver extends BroadcastReceiver {

        private Activity mActivity = null;

        public GroupListReceiver(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_GROUPS)) {
                Groups groups = (Groups) intent.getSerializableExtra(Constant.EXTRA_GROUPS);
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

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        }
    }
}