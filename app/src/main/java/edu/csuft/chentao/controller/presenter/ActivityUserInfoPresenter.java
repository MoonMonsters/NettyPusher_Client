package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.ImageActivity;
import edu.csuft.chentao.adapter.RecentMessageAdapter;
import edu.csuft.chentao.databinding.ActivityUserInfoBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by csuft.chentao on 2017-01-08 11:20.
 * email:qxinhai@yeah.net
 */

/**
 * UserInfoActivity的Presenter类
 */
public class ActivityUserInfoPresenter {

    private ActivityUserInfoBinding mActivityBinding = null;
    private UserHead mUserHead = null;
    RecentMessageAdapter mAdapter = null;

    /**
     * ChattingMessage集合
     */
    List<ChattingMessage> mChattingMessageList = null;
    /**
     * 分页
     */
    private int mOffset = 0;
    /**
     * 群id
     */
    private int mGroupId = -1;
    /**
     * 用户id
     */
    private int mUserId = -1;

    /**
     * 有数据刷新
     */
    private final int HANDLER_ONREFRESH_ADD_DATA = 0;
    /**
     * 无数据刷新
     */
    private final int HANDLER_ONREFRESH_NO_DATA = 1;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_ONREFRESH_ADD_DATA:
                    List<ChattingMessage> list = (List<ChattingMessage>) msg.obj;
                    int count = mChattingMessageList.size();
                    mChattingMessageList.addAll(list);
                    mActivityBinding.ptrlvUserinfoRecentMsg.getRefreshableView()
                            .setSelection(count);
                    mAdapter.notifyDataSetChanged();
                    break;
                case HANDLER_ONREFRESH_NO_DATA:
                    Toast.makeText(mActivityBinding.getRoot().getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    break;
            }
            mActivityBinding.ptrlvUserinfoRecentMsg.onRefreshComplete();
        }
    };

    public ActivityUserInfoPresenter(ActivityUserInfoBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void init(int groupId, int userId) {
        initData(groupId, userId);
        initListener();
    }

    private void initData(int groupId, int userId) {
        this.mGroupId = groupId;
        this.mUserId = userId;

        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(userId);
        mUserHead = UserHeadDaoUtil.getUserHead(userId);

        mActivityBinding.setUserInfo(userInfo);
        mActivityBinding.setUserHead(mUserHead);

        mChattingMessageList =
                ChattingMessageDaoUtil.getChattingMessageListWithGroupIdAndUserId(groupId, userId, mOffset);

        mAdapter = new RecentMessageAdapter(mActivityBinding.getRoot().getContext(),
                mChattingMessageList, R.layout.item_recent_msg, BR.chattingMessage);
        //设置刷新模式为上拉刷新
        mActivityBinding.ptrlvUserinfoRecentMsg.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        mActivityBinding.ptrlvUserinfoRecentMsg.setAdapter(mAdapter);
        mActivityBinding.setAdapter(mAdapter);
    }


    /**
     * 初始化监听器
     */
    private void initListener() {
        mActivityBinding.ptrlvUserinfoRecentMsg.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                mOffset++;
                List<ChattingMessage> list =
                        ChattingMessageDaoUtil.getChattingMessageListWithGroupIdAndUserId(mGroupId, mUserId, mOffset);
                if (list.size() == 0) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = HANDLER_ONREFRESH_NO_DATA;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg2 = mHandler.obtainMessage();
                    msg2.what = HANDLER_ONREFRESH_ADD_DATA;
                    msg2.obj = list;
                    mHandler.sendMessage(msg2);
                }
            }
        });
    }

    /**
     * 点击查看大头像
     */
    public void onClickToBigImage() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), ImageActivity.class);
        ImageDetail detail = new ImageDetail(mUserHead.getImage());
        intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
        mActivityBinding.getRoot().getContext()
                .startActivity(intent);
    }
}