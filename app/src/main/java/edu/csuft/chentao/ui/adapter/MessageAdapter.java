package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ItemMessageLeftBinding;
import edu.csuft.chentao.databinding.ItemMessageRightBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.ui.activity.UserInfoActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 15:17.
 * email:qxinhai@yeah.net
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<ChattingMessage> mChattingMessageList = null;
    private Context mContext = null;

    public MessageAdapter(Context context, List<ChattingMessage> chattingMessageList) {
        this.mChattingMessageList = chattingMessageList;
        this.mContext = context;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == Constant.TYPE_MSG_RECV) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_left, parent, false);
        } else if (viewType == Constant.TYPE_MSG_SEND) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_right, parent, false);
        }

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bindData(mChattingMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChattingMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return mChattingMessageList.get(position).getType();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private View mView = null;

        MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        void bindData(final ChattingMessage msg) {

            //根据用户id获得用户信息
            UserInfo userInfo = UserInfoDaoUtil.getUserInfo(msg.getUserid());
            UserHead userHead = UserHeadDaoUtil.getUserHead(msg.getUserid());
            ItemMessagePresenter itemPresenter =
                    new ItemMessagePresenter(msg);

            boolean isShowTime = mChattingMessageList.indexOf(msg) % 20 == 0;

            if (msg.getType() == Constant.TYPE_MSG_RECV) {  //如果是接收消息
                ItemMessageLeftBinding leftBinding =
                        DataBindingUtil.bind(mView);
                //绑定数据
                leftBinding.setVariable(BR.msg, msg);
                leftBinding.setVariable(BR.userHead, userHead);
                leftBinding.setVariable(BR.userInfo, userInfo);
                leftBinding.setVariable(BR.itemPresenter, itemPresenter);
                if (isShowTime) {
                    leftBinding.tvMessageLeftTime.setVisibility(View.VISIBLE);
                }

            } else if (msg.getType() == Constant.TYPE_MSG_SEND) {   //发送消息
                ItemMessageRightBinding rightBinding =
                        DataBindingUtil.bind(mView);
                //绑定数据
                rightBinding.setVariable(BR.msg, msg);
                rightBinding.setVariable(BR.userHead, userHead);
                rightBinding.setVariable(BR.userInfo, userInfo);
                rightBinding.setVariable(BR.itemPresenter, itemPresenter);
                if (isShowTime) {
                    rightBinding.tvMessageRightTime.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class ItemMessagePresenter {
        private ChattingMessage mChattingMessage;

        ItemMessagePresenter(ChattingMessage chattingMessage) {
            this.mChattingMessage = chattingMessage;
        }

        /**
         * 点击放大聊天对话中的图片
         */
        public void onClickForImageDetail() {
            Intent intent = new Intent(MyApplication.getInstance(), ImageActivity.class);
            ImageDetail detail = new ImageDetail(null, mChattingMessage.getImage());
            //传递数据
            intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getInstance().startActivity(intent);
        }

        /**
         * 点击头像，查看用户详细信息，及最近聊天记录
         */
        public void onClickForUserDetailInfo() {
            Intent intent = new Intent(mContext, UserInfoActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, mChattingMessage.getGroupid());
            intent.putExtra(Constant.EXTRA_USER_ID, mChattingMessage.getUserid());
            mContext.startActivity(intent);
        }

        /**
         * 点击重新发送消息
         */
        public void doClickToSendMessageAgain() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            AlertDialog dialog = builder.setTitle("提示")
                    .setMessage("重发该消息？")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Message message = new Message();
                            message.setSerial_number(mChattingMessage.getSerial_number());
                            message.setGroupid(mChattingMessage.getGroupid());
                            message.setMessage(mChattingMessage.getMessage());
                            message.setPicFile(mChattingMessage.getImage());
                            message.setTime(mChattingMessage.getTime());
                            message.setType(mChattingMessage.getType());
                            message.setTypeMsg(mChattingMessage.getTypemsg());
                            message.setUserid(mChattingMessage.getUserid());
                            SendMessageUtil.sendMessage(message);

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
    }
}