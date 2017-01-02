package edu.csuft.chentao.controller.presenter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import edu.csuft.chentao.adapter.GroupChattingAdapter;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.DaoSessionUtil;

/**
 * Created by Chalmers on 2017-01-01 13:58.
 * email:qxinhai@yeah.net
 */

public class FragmentChattingListPresenter {

    private FragmentChattingListBinding mFragmentBinding = null;

    private ArrayList<GroupChattingItem> mGroupChattingItemList = null;
    private GroupChattingAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_CHATTING_LIST) {
                int groupId = msg.arg1;
                String lastMessage = (String) msg.obj;



            }
        }
    };

    public FragmentChattingListPresenter(FragmentChattingListBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
        EventBus.getDefault().post(mHandler);
    }

    public void init() {
        initData();
        initListener();
    }

    private void initData() {
        //读取所有的记录
        mGroupChattingItemList = (ArrayList<GroupChattingItem>) DaoSessionUtil.getGroupChattingItemDao()
                .queryBuilder().list();
        //设置adapter
        mAdapter = new GroupChattingAdapter(mGroupChattingItemList);
        //设置RecyclerView的属性
        mFragmentBinding.rvChattingListContent.setLayoutManager(new LinearLayoutManager(mFragmentBinding.getRoot().getContext()));
        mFragmentBinding.rvChattingListContent.setAdapter(mAdapter);
    }

    private void initListener() {

    }
}
