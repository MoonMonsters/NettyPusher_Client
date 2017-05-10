package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityUserInfoBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.ui.activity.UserInfoActivity;
import edu.csuft.chentao.ui.adapter.RecentMessageAdapter;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
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
public class ActivityUserInfoPresenter extends BasePresenter {

    private ActivityUserInfoBinding mActivityBinding = null;
    private UserHead mUserHead = null;
    private RecentMessageAdapter mAdapter = null;

    /**
     * ChattingMessage集合
     */
    private List<ChattingMessage> mChattingMessageList = null;
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
                    Toast.makeText(mActivityBinding.getRoot().getContext(), OperationUtil.getString(mActivityBinding, R.string.string_not_have_more_data), Toast.LENGTH_SHORT).show();
                    break;
            }
            mActivityBinding.ptrlvUserinfoRecentMsg.onRefreshComplete();
        }
    };

    public ActivityUserInfoPresenter(ActivityUserInfoBinding activityBinding,
                                     Object object1, Object object2) {
        this.mActivityBinding = activityBinding;
        this.mGroupId = (int) object1;
        this.mUserId = (int) object2;

        init();
    }

    @Override
    protected void initData() {
        setUserInfo();

//        mActivityBinding.tvUserInfoSignature.setMovementMethod(new ScrollingMovementMethod());
        mChattingMessageList =
                ChattingMessageDaoUtil.getChattingMessageListWithGroupIdAndUserId(mGroupId, mUserId, mOffset);

        mAdapter = new RecentMessageAdapter(mActivityBinding.getRoot().getContext(),
                mChattingMessageList, R.layout.item_recent_msg, BR.chattingMessage);
        //设置刷新模式为上拉刷新
        mActivityBinding.ptrlvUserinfoRecentMsg.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mActivityBinding.setVariable(BR.adapter, mAdapter);
        mActivityBinding.setVariable(BR.title, "用户信息");

        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_USERINFO);
        req.setArg1(mUserId);
        SendMessageUtil.sendMessage(req);
    }

    @Override
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_PRESENTER_UPDATE_USERINFO)) {
            setUserInfo();
        }
    }

    /**
     * 设置显示的用户信息
     */
    private void setUserInfo() {
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(mUserId);
        mUserHead = UserHeadDaoUtil.getUserHead(mUserId);

        mActivityBinding.setUserInfo(userInfo);
        mActivityBinding.setUserHead(mUserHead);
    }

    /**
     * 初始化监听器
     */
    @Override
    public void initListener() {
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

        //返回键的点击事件
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserInfoActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    /**
     * 点击查看大头像
     */
    public void onClickToBigImage() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), ImageActivity.class);
        ImageDetail detail = new ImageDetail(null, mUserHead.getImage());
        intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
        mActivityBinding.getRoot().getContext()
                .startActivity(intent);
    }
}