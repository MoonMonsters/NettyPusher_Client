package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import edu.csuft.chentao.R;
import edu.csuft.chentao.adapter.GroupChattingAdapter;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.utils.Constant;
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

    @Override
    public void initListener() {
        this.mFragmentBinding.btnChattingListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupChattingItem item = new GroupChattingItem();
                item.setGroupname("Hello");
                item.setLastmessage("World");
                try {
                    File file = new File(Constant.PATH, "splash.jpg");
                    FileInputStream fis = new FileInputStream(file);
                    int length = fis.available();
                    byte[] buf = new byte[length];
                    fis.read(buf, 0, length);
                    item.setImage(buf);
                    item.setGroupid(100000);
                    mItemDao.insert(item);
                    mGroupChattingItemList.add(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.mFragmentBinding.btnChattingListUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(Constant.PATH, "splash2.jpg");
                    FileInputStream fis = new FileInputStream(file);
                    int length = fis.available();
                    byte[] buf = new byte[length];
                    fis.read(buf, 0, length);
                    for (GroupChattingItem item : mGroupChattingItemList) {
                        item.setImage(buf);
                    }
                    mItemDao.updateInTx(mGroupChattingItemList);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
