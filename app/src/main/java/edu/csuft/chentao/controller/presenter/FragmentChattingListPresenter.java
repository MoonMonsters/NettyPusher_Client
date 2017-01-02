package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import edu.csuft.chentao.activity.MessageActivity;
import edu.csuft.chentao.adapter.GroupChattingAdapter;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-01-01 13:58.
 * email:qxinhai@yeah.net
 */

public class FragmentChattingListPresenter implements GroupChattingAdapter.OnItemClick {

    private FragmentChattingListBinding mFragmentBinding = null;

    private ArrayList<GroupChattingItem> mGroupChattingItemList = null;
    private GroupChattingAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (this) {
                //添加数据
                if (msg.what == Constant.HANDLER_CHATTING_LIST_ADD) {
                    LoggerUtil.logger(Constant.TAG, "刷新数据");
                    GroupChattingItem chattingItem = (GroupChattingItem) msg.obj;
                    mGroupChattingItemList.add(chattingItem);
                    mAdapter.notifyDataSetChanged();
                } else if (msg.what == Constant.HANDLER_CHATTING_LIST_DELETE) { //删除数据
                    //得到需要删除的位置
                    int position = msg.arg1;
                    //得到要删除的群的id
                    int groupId = mGroupChattingItemList.get(position).getGroupid();
                    //得到对象
                    GroupChattingItem item = DaoSessionUtil.getGroupChattingItemDao()
                            .queryBuilder().where(GroupChattingItemDao.Properties.Groupid.eq(groupId))
                            .list().get(0);
                    //从数据表中移除
                    DaoSessionUtil.getGroupChattingItemDao().delete(item);
                    //从集合中移除
                    mGroupChattingItemList.remove(item);
                    //刷新
                    mAdapter.notifyDataSetChanged();
                } else if (msg.what == Constant.HANDLER_CHATTING_LIST_REFRESH) {    //更新数据
                    GroupChattingItem chattingItem = (GroupChattingItem) msg.obj;
                    int index = -1;
                    for (GroupChattingItem item : mGroupChattingItemList) {
                        if (item.getGroupid() == chattingItem.getGroupid()) {
                            index = mGroupChattingItemList.indexOf(item);
                            break;
                        }
                    }
                    if (index != -1) {
                        mGroupChattingItemList.remove(index);
                        mGroupChattingItemList.add(chattingItem);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public FragmentChattingListPresenter(FragmentChattingListBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
        EventBus.getDefault().post(new HandlerMessage(mHandler, "ChattingListFragment"));
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
        mAdapter = new GroupChattingAdapter(mGroupChattingItemList, this);
        //设置RecyclerView的属性
        mFragmentBinding.rvChattingListContent.setLayoutManager(new LinearLayoutManager(mFragmentBinding.getRoot().getContext()));
        mFragmentBinding.rvChattingListContent.setAdapter(mAdapter);
    }

    private void initListener() {

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(mFragmentBinding.getRoot().getContext(), MessageActivity.class);
        intent.putExtra(Constant.EXTRA_GROUPID, mGroupChattingItemList.get(position).getGroupid());
        mFragmentBinding.getRoot().getContext().startActivity(intent);
    }
}
