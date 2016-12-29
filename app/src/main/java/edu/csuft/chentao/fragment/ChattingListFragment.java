package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import edu.csuft.chentao.R;
import edu.csuft.chentao.adapter.GroupChattingAdapter;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.utils.GreenDaoUtil;

public class ChattingListFragment extends BaseFragment {

    private FragmentChattingListBinding mFragmentBinding = null;
    private ArrayList<GroupChattingItem> mGroupChattingItemList = null;
    private GroupChattingItemDao mItemDao = GreenDaoUtil.getInstance().getDaoSession().getGroupChattingItemDao();

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_chatting_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentChattingListBinding) viewDataBinding;
    }

    GroupChattingAdapter mAdapter;

    @Override
    public void initData() {
        //读取所有的记录
        mGroupChattingItemList = (ArrayList<GroupChattingItem>) mItemDao.queryBuilder().list();

        //设置adapter
        mAdapter = new GroupChattingAdapter(mGroupChattingItemList);
        //设置RecyclerView的属性
        mFragmentBinding.rvChattingListContent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mFragmentBinding.rvChattingListContent.setAdapter(mAdapter);
    }


}
