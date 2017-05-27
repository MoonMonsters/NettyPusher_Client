package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.databinding.ItemPopupAnnouncementsBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.ui.activity.CutViewActivity;
import edu.csuft.chentao.ui.activity.MessageActivity;
import edu.csuft.chentao.ui.adapter.MessageAdapter;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;
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
    private int mOffset = 0;
    private int mGroupId;

    public ActivityMessagePresenter(ActivityMessageBinding activityBinding, Object object1) {
        mActivityBinding = activityBinding;
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

            LoggerUtil.logger("TAG", "ActivityMessagePresenter.getEBToObjectPresenter---" + chattingMessage.getSerial_number());

            //添加进集合
            mChattingMessageList.add(chattingMessage);
            //刷新界面
            mAdapter.notifyDataSetChanged();
            //选中最后一行
            mActivityBinding.rvMessageContent.getLayoutManager()
                    .smoothScrollToPosition(mActivityBinding.rvMessageContent, null, mAdapter.getItemCount() - 1);
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_MESSAGE_PRESENTER_SEND_SUCCESS)) {

            //消息发送成功，返回字段，隐藏未发送成功图片
            final int serial_number = ((ReturnInfoResp) ebObj.getObject()).getArg1();
            String sendTime = (String) ((ReturnInfoResp) ebObj.getObject()).getObj();

            LoggerUtil.logger("TAG", "ActivityMessagePresenter-->serialNumber = " + serial_number);

            for (final ChattingMessage chattingMessage : mChattingMessageList) {
                if (chattingMessage.getSerial_number() == serial_number) {
                    chattingMessage.setSend_success(View.GONE);
                    chattingMessage.setTime(sendTime);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
                            ChattingMessageDaoUtil.updateChattingMessage(chattingMessage);
                        }
                    }).start();

                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_PRESENTER_UPDATE_USERINFO)) {
            //用户数据已更新，刷新界面即可
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initData() {
        //获得该群的聊天记录
        mChattingMessageList = reverse(ChattingMessageDaoUtil.getChattingMessageListWithOffset(mGroupId, mOffset));
        //适配器
        mAdapter = new MessageAdapter(mActivityBinding.getRoot().getContext(), mChattingMessageList);

        //设置布局方式
        mActivityBinding.rvMessageContent.setLayoutManager(new LinearLayoutManager(mActivityBinding.getRoot().getContext()));
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
            CopyUtil.saveMessageReqToChattingMessage(message);
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
            Toast.makeText(mActivityBinding.getRoot().getContext(), OperationUtil.getString(mActivityBinding, R.string.string_not_send_empty_message), Toast.LENGTH_SHORT).show();
        } else {
            //发送文字消息
            Message message = OperationUtil.sendChattingMessage(mGroupId, Constant.TYPE_MSG_TEXT,
                    mActivityBinding.etMessageInput.getText().toString(), null);
            //发送完成后，自动添加到集合中
            CopyUtil.saveMessageReqToChattingMessage(message);
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

    private AlertDialog mDialog;

    /**
     * 弹出对话框
     */
    private void showPopupAnnouncement() {
        //在子线程中操作，避免数据延迟，带来界面长期空白
        new Thread(new Runnable() {
            @Override
            public void run() {
                ((MessageActivity) mActivityBinding.getRoot().getContext())
                        .runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<LocalAnnouncement> localAnnouncementList = LocalAnnouncementDaoUtil.getAllLocalAnnouncementsWithNew(mGroupId, true);

                                //用户已读，更新消息，将所有公告状态置为已读状态
                                for (LocalAnnouncement la : localAnnouncementList) {
                                    la.setIsnew(false);
                                }
                                LocalAnnouncementDaoUtil.update(localAnnouncementList);

                                if (localAnnouncementList.size() != 0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivityBinding.getRoot().getContext());
                                    if (mDialog == null) {    //如果Dialog对象为空才创建
                                        ItemPopupAnnouncementsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivityBinding.getRoot().getContext()), R.layout.item_popup_announcements, null, false);
                                        mDialog = builder.setView(binding.getRoot())
                                                .setCancelable(false).create();
                                        //显示对话框
                                        mDialog.show();
                                        binding.setVariable(BR.announcement, localAnnouncementList.get(0));
                                        binding.setVariable(BR.itemPresenter, new ItemPopupAnnouncementPresenter(binding, mDialog, mGroupId));
                                    }
                                }
                            }
                        });
            }
        }).start();
    }

    /**
     * 关闭公告对话框
     */
    public void closeAnnouncementDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 更新界面
     */
    public void updateGroupChattingItems() {
        //根据群id得到GroupChattingItem对象
        GroupChattingItem chattingItem = GroupChattingItemDaoUtil.getGroupChattingItem(mGroupId);
        //如果在对话框界面，存在该群的入口，同时未读消息不为0，则发送消息，刷新界面
        if (chattingItem != null && chattingItem.getNumber() != 0) {
            //将数据清0
            chattingItem.setNumber(0);
            //更新
            GroupChattingItemDaoUtil.updateGroupChattingItem(chattingItem);

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_UPDATE_ITEM, chattingItem);
        }
    }
}