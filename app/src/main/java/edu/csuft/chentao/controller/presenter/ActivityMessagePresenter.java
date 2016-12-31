package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import edu.csuft.chentao.adapter.MessageAdapter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2016-12-29 13:51.
 * email:qxinhai@yeah.net
 */

public class ActivityMessagePresenter {

    private ActivityMessageBinding mActivityBinding = null;

    private static MessageAdapter mAdapter = null;
    private static List<ChattingMessage> mChattingMessageList = null;
    private Context mContext = null;
    private static final int MSG_CHATTING_MESSAGE = 0;

    private int mGroupId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_CHATTING_MESSAGE) {
                //获得数据
                ChattingMessage chattingMessage = (ChattingMessage) msg.obj;
                //添加进集合
                mChattingMessageList.add(chattingMessage);
                //刷新界面
                mAdapter.notifyDataSetChanged();
                //选中最后一行
                mActivityBinding.rvMessageContent.getLayoutManager()
                        .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, mAdapter.getItemCount() - 1);
            }
        }
    };

    public ActivityMessagePresenter(ActivityMessageBinding activityBinding) {
        mActivityBinding = activityBinding;
        mContext = mActivityBinding.getRoot().getContext();
    }

    public void init(int groupId) {

        //将Handler发送到MessageActivity中
        EventBus.getDefault().post(mHandler);

        initData(groupId);
        initListener();
    }

    private void initData(int groupId) {
        //群id
        this.mGroupId = groupId;

        //获得该群的聊天记录
        mChattingMessageList = DaoSessionUtil.getChattingMessageDao()
                .queryBuilder().list();
        //适配器
        mAdapter = new MessageAdapter(mChattingMessageList);

        //设置布局方式
        mActivityBinding.rvMessageContent.setLayoutManager(new LinearLayoutManager(mContext));
        mActivityBinding.rvMessageContent.setAdapter(mAdapter);
        mActivityBinding.rvMessageContent.getLayoutManager()
                .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, mAdapter.getItemCount() - 1);
    }

    private void initListener() {
        mActivityBinding.btnMessageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mActivityBinding.etMessageInput.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(mContext, "不能发送空内容", Toast.LENGTH_SHORT).show();
                } else {
                    //发送数据，构造对象
                    Message message = new Message();
                    //消息类型，文字还是图片
                    message.setTypeMsg(Constant.TYPE_MSG_TEXT);
                    //发送类型
                    message.setType(Constant.TYPE_MSG_SEND);
                    message.setGroupid(mGroupId);
                    //因为是文字类型，所以图片为空
                    message.setPicFile(null);
                    //文字内容
                    message.setMessage(content);
                    //用户id
                    message.setUserid(SharedPrefUserInfoUtil.getUserId());
                    //设置时间
                    message.setTime(OperationUtil.getCurrentTime());

                    //发送数据
                    SendMessageUtil.sendMessage(message);

                    //发送完成后，自动添加到集合中
                    mChattingMessageList.add(CopyUtil.saveMessageReqToChattingMessage(message));
                    //刷新界面
                    mAdapter.notifyDataSetChanged();
                    mActivityBinding.etMessageInput.setText(null);
                }
            }
        });
    }


}