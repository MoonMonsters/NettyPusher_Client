package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.activity.MessageActivity;
import edu.csuft.chentao.adapter.MessageAdapter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2016-12-29 13:51.
 * email:qxinhai@yeah.net
 */

public class ActivityMessagePresenter {

    public static final int IMAGE_CODE = 0;

    private ActivityMessageBinding mActivityBinding = null;
    private static MessageAdapter mAdapter = null;
    private static List<ChattingMessage> mChattingMessageList = null;
    private Context mContext = null;
    private int index = 0;

    private int mGroupId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == Constant.HANDLER_MESSAGE_CHATTING_MESSAGE) {
                //获得数据
                ChattingMessage chattingMessage = (ChattingMessage) msg.obj;
                //添加进集合
                mChattingMessageList.add(chattingMessage);
                //刷新界面
                mAdapter.notifyDataSetChanged();
                //选中最后一行
                mActivityBinding.rvMessageContent.getLayoutManager()
                        .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, mAdapter.getItemCount() - 1);
            } else if (msg.what == Constant.HANDLER_CHATTING_MESSAGE_REFRESH) {
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    public ActivityMessagePresenter(ActivityMessageBinding activityBinding) {
        mActivityBinding = activityBinding;
        mContext = mActivityBinding.getRoot().getContext();
    }

    public void init(int groupId) {
        //将Handler发送到MessageActivity中
        EventBus.getDefault().post(new HandlerMessage(mHandler, "MessageActivity"));
        EventBus.getDefault().register(this);
        initData(groupId);
        initListener();
    }

    private void initData(final int groupId) {
        //群id
        this.mGroupId = groupId;

        //获得该群的聊天记录
        mChattingMessageList = new ArrayList<>();
        //适配器
        mAdapter = new MessageAdapter(mChattingMessageList);

        //设置布局方式
        mActivityBinding.rvMessageContent.setLayoutManager(new LinearLayoutManager(mContext));
        mActivityBinding.rvMessageContent.setAdapter(mAdapter);
        mActivityBinding.rvMessageContent.getLayoutManager()
                .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, (mAdapter.getItemCount() == 0 ? 0 : mAdapter.getItemCount() - 1));

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ChattingMessage> list = DaoSessionUtil.getChattingMessageList(groupId);
                int allCount = list.size();
                index = allCount >= 20 ? allCount - 20 : 0;
                mChattingMessageList.addAll(list.subList(index, allCount));
                list.clear();

                android.os.Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_CHATTING_MESSAGE_REFRESH;
                mHandler.sendMessage(msg);
            }
        }).start();
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
        getAlbum.setType("image/*");
        ((MessageActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, IMAGE_CODE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBitmapBytes(byte[] buf) {

        //发送图片消息
        Message message = OperationUtil.sendChattingMessage(mGroupId, Constant.TYPE_MSG_IMAGE, null, buf);
        //发送完成后，自动添加到集合中
        mChattingMessageList.add(CopyUtil.saveMessageReqToChattingMessage(message));
        //刷新界面
        mAdapter.notifyDataSetChanged();

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
            mActivityBinding.etMessageInput.setText(null);
        }
    }

    private void initListener() {
        //刷新方法
        mActivityBinding.srlMessageRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int index2 = index >= 20 ? index - 20 : 0;
                List<ChattingMessage> list2 = DaoSessionUtil.getChattingMessageList(mGroupId).subList(index2, index);
                mChattingMessageList.addAll(0, list2);
                mAdapter.notifyDataSetChanged();
                index = index2;
                mActivityBinding.rvMessageContent.getLayoutManager()
                        .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, list2.size() <= 20 ? list2.size() : 20 - 4);
                mActivityBinding.srlMessageRefresh.setRefreshing(false);
            }
        });
    }
}