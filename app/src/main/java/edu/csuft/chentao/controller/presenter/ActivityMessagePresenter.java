package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import edu.csuft.chentao.activity.MessageActivity;
import edu.csuft.chentao.adapter.MessageAdapter;
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
            LoggerUtil.logger(Constant.TAG, "接收到内容是->" + chattingMessage.toString());
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
//        mActivityBinding.rvMessageContent.setAdapter(mAdapter);
        mActivityBinding.rvMessageContent.getLayoutManager()
                .smoothScrollToPosition(mActivityBinding.rvMessageContent,
                        null, (mChattingMessageList.size() == 0 ? 0 : mChattingMessageList.size()));
        mActivityBinding.setAdapter(mAdapter);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail detail) {
        if (detail.getTag().equals(Constant.IMAGE_ACTIVITY_MESSAGE_PRESENTER)) {
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
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(Constant.IMAGE_TYPE);
        ((MessageActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, Constant.IMAGE_CODE);
    }

    /**
     * 发送文字消息
     */
    public void onClickToSendTextMessage() {
        String content = mActivityBinding.etMessageInput.getText().toString();
        if (TextUtils.isEmpty(content)) {   //发送内容为空
            Toast.makeText(mContext, "不能发送空内容", Toast.LENGTH_SHORT).show();
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
    }

    /**
     * 翻转
     */
    private List<ChattingMessage> reverse(List<ChattingMessage> list) {

        Collections.reverse(list);
        return list;
    }
}