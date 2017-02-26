package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import edu.csuft.chentao.activity.HintActivity;
import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
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
            if (msg.what == Constant.HANDLER_REMOVE_GROUP) {
                int groupId = msg.arg1;
                int index = -1;
                for (Groups g : mGroupsList) {
                    if (g.getGroupid() == groupId) {
                        index = mGroupsList.indexOf(g);
                        break;
                    }
                }
                if (index != -1) {
                    //从显示列表中移除掉该数据
                    mGroupsList.remove(index);
                    //刷新
                    mAdapter.notifyDataSetChanged();
                    //移除掉
                    GroupsDaoUtil.deleteByGroupId(groupId);
                }
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
    public void getEBToPresenterObject(EBToPreObject ebObj) {
        LoggerUtil.logger(Constant.TAG, "FragmentGroupListPresenter-->接收到群数据");
        //接收到了groups数据，在列表中增加一个群数据
        if (ebObj.getTag().equals(Constant.TAG_FRAGMENT_GROUP_LIST_PRESENTER)) {
            Groups groups = (Groups) ebObj.getObject();
            mGroupsList.add(groups);
            mAdapter.notifyDataSetChanged();
            //接收到了移除群数据，从列表中把groups数据移除掉
        } else if (ebObj.getTag().equals(Constant.TAG_REMOVE_GROUPS)) {
            GroupReminderResp resp = (GroupReminderResp) ebObj.getObject();
            //得到需要移除的群id
            int groupId = resp.getGroupId();
            int index = -1;
            //遍历
            for (Groups g : mGroupsList) {
                if (g.getGroupid() == groupId) {
                    index = mGroupsList.indexOf(g);
                    break;
                }
            }
            if (index != -1) {
                //从显示列表中移除掉该数据
                mGroupsList.remove(index);
                //刷新
                mAdapter.notifyDataSetChanged();
                //移除掉
                GroupsDaoUtil.deleteByGroupId(groupId);
            }
        }
    }

    /**
     * 点击进入HintActivity
     */
    public void onClickToEnterHintActivity() {
        Intent intent = new Intent(mFragmentBinding.getRoot().getContext(), HintActivity.class);
        mFragmentBinding.getRoot().getContext().startActivity(intent);
    }
}