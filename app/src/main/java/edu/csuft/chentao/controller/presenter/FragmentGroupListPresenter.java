package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.GreenDaoUtil;

/**
 * Created by Chalmers on 2016-12-28 17:46.
 * email:qxinhai@yeah.net
 */

public class FragmentGroupListPresenter {

    private FragmentGroupListBinding mFragmentBinding = null;
    private Context mContext;

    private List<Groups> mGroupsList = null;
    private GroupsDao mGroupsDao = null;
    private GroupListAdapter mAdapter = null;

    public FragmentGroupListPresenter(FragmentGroupListBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
        mContext = mFragmentBinding.getRoot().getContext();
    }

    public void init() {
        initData();
    }

    private void initData() {
        mGroupsDao = GreenDaoUtil.getInstance().getDaoSession().getGroupsDao();
        mGroupsList = mGroupsDao.queryBuilder().list();
        mAdapter = new GroupListAdapter(mGroupsList);

        mFragmentBinding.rvGroupListContent.setLayoutManager(new LinearLayoutManager(mContext));
        mFragmentBinding.rvGroupListContent.setAdapter(mAdapter);
    }
}