package edu.csuft.chentao.controller.presenter;

import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import edu.csuft.chentao.adapter.ChattingListAdapter2;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;

/**
 * Created by Chalmers on 2017-01-01 13:58.
 * email:qxinhai@yeah.net
 */

public class FragmentChattingListPresenter {

    private FragmentChattingListBinding mFragmentBinding = null;

    private ArrayList<GroupChattingItem> mGroupChattingItemList = null;
    private ChattingListAdapter2 mAdapter;

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
                    //群id
                    int groupId = mGroupChattingItemList.get(position).getGroupid();
                    //对象
                    GroupChattingItem groupChattingItem =
                            GroupChattingItemDaoUtil.getGroupChattingItem(groupId);
                    //从集合中移除
                    mGroupChattingItemList.remove(position);
                    //从数据库中删除
                    GroupChattingItemDaoUtil.removeGroupChattingItem(groupChattingItem);
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
        mGroupChattingItemList = (ArrayList<GroupChattingItem>) GroupChattingItemDaoUtil.loadAll();
        //设置adapter
        mAdapter = new ChattingListAdapter2(mFragmentBinding.getRoot().getContext(), mGroupChattingItemList);
        //设置RecyclerView的属性
        mFragmentBinding.slvChattingListContent.setAdapter(mAdapter);
    }

    private void initListener() {

    }
}
