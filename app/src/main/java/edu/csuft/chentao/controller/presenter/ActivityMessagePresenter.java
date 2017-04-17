package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.ui.activity.CutViewActivity;
import edu.csuft.chentao.ui.activity.GroupDetailActivity;
import edu.csuft.chentao.ui.activity.MessageActivity;
import edu.csuft.chentao.ui.adapter.MessageAdapter;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
import edu.csuft.chentao.utils.daoutil.LocalAnnouncementDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 13:51.
 * email:qxinhai@yeah.net
 */

/**
 * MessageActivity的Presenter类
 */
public class ActivityMessagePresenter extends BasePresenter {

    private ActivityMessageBinding mActivityBinding = null;
    private MessageAdapter mAdapter = null;
    private List<ChattingMessage> mChattingMessageList = null;
    private Context mContext = null;
    private int mOffset = 0;
    private int mGroupId;

    /**
     * 新的公告集合
     */
    private List<LocalAnnouncement> mPopupAnnouncementList;
    private int mAnnouncementIndex = 0;

    public ActivityMessagePresenter(ActivityMessageBinding activityBinding, Object object1) {
        mActivityBinding = activityBinding;
        mContext = mActivityBinding.getRoot().getContext();
        this.mGroupId = (int) object1;

        init();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //接收到了添加ChattingMessage数据的命令
        if (ebObj.getTag().equals(Constant.TAG_ADD_CHATTING_MESSAGE)) {
            //得到要添加的数据
            ChattingMessage chattingMessage = (ChattingMessage) ebObj.getObject();
            //添加进集合
            mChattingMessageList.add(chattingMessage);
            //刷新界面
            mAdapter.notifyDataSetChanged();
            //选中最后一行
            mActivityBinding.rvMessageContent.getLayoutManager()
                    .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, mAdapter.getItemCount() - 1);
        }
    }

    @Override
    protected void initData() {
        //获得该群的聊天记录
        mChattingMessageList = reverse(ChattingMessageDaoUtil.getChattingMessageListWithOffset(mGroupId, mOffset));
        //适配器
        mAdapter = new MessageAdapter(mActivityBinding.getRoot().getContext(), mChattingMessageList);

        //设置布局方式
        mActivityBinding.rvMessageContent.setLayoutManager(new LinearLayoutManager(mContext));
        mActivityBinding.rvMessageContent.getLayoutManager()
                .smoothScrollToPosition(mActivityBinding.rvMessageContent,
                        null, (mChattingMessageList.size() == 0 ? 0 : mChattingMessageList.size()));
        mActivityBinding.setVariable(BR.adapter, mAdapter);
        mActivityBinding.setVariable(BR.title, GroupsDaoUtil.getGroups(mGroupId).getGroupname());

        //弹出公告框
        showPopupAnnouncement();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail detail) {
        if (detail.getTag().equals(Constant.CUT_VIEW_MESSAGE_ACTIVITY)) {
            byte[] buf = detail.getImage();
            //发送图片消息
            Message message = OperationUtil.sendChattingMessage(mGroupId, Constant.TYPE_MSG_IMAGE, null, buf);
            //发送完成后，自动添加到集合中
            mChattingMessageList.add(CopyUtil.saveMessageReqToChattingMessage(message));
            //刷新界面
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 输入框内容改变，改变按钮的显示
     */
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            mActivityBinding.btnMessageSend.setVisibility(View.GONE);
            mActivityBinding.ivMessageSelectImage.setVisibility(View.VISIBLE);
        } else {
            mActivityBinding.btnMessageSend.setVisibility(View.VISIBLE);
            mActivityBinding.ivMessageSelectImage.setVisibility(View.GONE);
        }
    }

    /**
     * 发送图片消息
     */
    public void onClickToSendImageMessage() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), CutViewActivity.class);
        intent.putExtra(Constant.EXTRA_CUT_VIEW, Constant.CUT_VIEW_MESSAGE_ACTIVITY);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * 发送文字消息
     */
    public void onClickToSendTextMessage() {
        String content = mActivityBinding.etMessageInput.getText().toString();
        if (TextUtils.isEmpty(content)) {   //发送内容为空
            Toast.makeText(mContext, OperationUtil.getString(mActivityBinding, R.string.string_not_send_empty_message), Toast.LENGTH_SHORT).show();
        } else {
            //发送文字消息
            Message message = OperationUtil.sendChattingMessage(mGroupId, Constant.TYPE_MSG_TEXT,
                    mActivityBinding.etMessageInput.getText().toString(), null);

            //发送完成后，自动添加到集合中
            mChattingMessageList.add(CopyUtil.saveMessageReqToChattingMessage(message));
            //刷新界面
            mAdapter.notifyDataSetChanged();
            //清空输入框
            mActivityBinding.etMessageInput.setText(null);
        }
    }

    @Override
    public void initListener() {
        //刷新方法
        mActivityBinding.srlMessageRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mOffset++;
                List<ChattingMessage> list = ChattingMessageDaoUtil.getChattingMessageListWithOffset(mGroupId, mOffset);
                mChattingMessageList.addAll(0, reverse(list));
                mAdapter.notifyDataSetChanged();
                mActivityBinding.rvMessageContent.getLayoutManager()
                        .smoothScrollToPosition(mActivityBinding.rvMessageContent,
                                null, list.size());
                mActivityBinding.srlMessageRefresh.setRefreshing(false);
            }
        });

        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MessageActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    /**
     * 翻转
     */
    private List<ChattingMessage> reverse(List<ChattingMessage> list) {

        Collections.reverse(list);
        return list;
    }

    private void showPopupAnnouncement() {
        mPopupAnnouncementList = LocalAnnouncementDaoUtil.getAllLocalAnnouncementsWithNew(mGroupId, true);
        if (mPopupAnnouncementList.size() != 0) {
            //显示公告框
            mActivityBinding.includePopupAnnouncement.layoutPopupAnnouncement.setVisibility(View.VISIBLE);
            mActivityBinding.includePopupAnnouncement.setVariable(BR.presenter, this);

            bindLocalAnnouncement();
            showNextAndPrevious();
        }
    }

    /**
     * 绑定数据
     */
    private void bindLocalAnnouncement() {
        mActivityBinding.includePopupAnnouncement.setVariable(BR.announcement, mPopupAnnouncementList.get(mAnnouncementIndex));
    }

    /**
     * 关闭弹出公告框
     */
    public void doClickToCloseOrOpen() {
        mActivityBinding.includePopupAnnouncement.layoutPopupAnnouncement.setVisibility(View.GONE);
    }

    /**
     * 点击显示下一条公告
     */
    public void doClickToNext() {

        if (mAnnouncementIndex < mPopupAnnouncementList.size() - 1) {
            mAnnouncementIndex++;
        }

        bindLocalAnnouncement();
        showNextAndPrevious();
    }

    /**
     * 点击显示上一条公告
     */
    public void doClickToPrevious() {

        if (mAnnouncementIndex > 0) {
            mAnnouncementIndex--;
        }

        bindLocalAnnouncement();
        showNextAndPrevious();
    }

    /**
     * 控制下一条和上一条开关的显示
     */
    private void showNextAndPrevious() {
        if (mPopupAnnouncementList.size() == 1) {
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementLeft.setVisibility(View.GONE);
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementRight.setVisibility(View.GONE);
        } else if (mAnnouncementIndex == 0) {  //如果当前是第一条公告，则隐藏上翻
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementLeft.setVisibility(View.GONE);
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementRight.setVisibility(View.VISIBLE);
        } else if (mAnnouncementIndex == mPopupAnnouncementList.size() - 1) {
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementLeft.setVisibility(View.VISIBLE);
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementRight.setVisibility(View.GONE);
        } else {
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementLeft.setVisibility(View.VISIBLE);
            mActivityBinding.includePopupAnnouncement.ivPopupAnnouncementRight.setVisibility(View.VISIBLE);
        }
    }

}