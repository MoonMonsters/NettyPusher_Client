package edu.csuft.chentao.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.ImageActivity;
import edu.csuft.chentao.activity.UserInfoActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ItemMessageLeftBinding;
import edu.csuft.chentao.databinding.ItemMessageRightBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 15:17.
 * email:qxinhai@yeah.net
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<ChattingMessage> mChattingMessageList = null;

    public MessageAdapter(List<ChattingMessage> chattingMessageList) {
        this.mChattingMessageList = chattingMessageList;
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

            //聊天图片
            ImageView chattingImage = null;
            //头像图片
            ImageView headImage = null;

            if (msg.getType() == Constant.TYPE_MSG_RECV) {  //如果是接收消息
                ItemMessageLeftBinding leftBinding =
                        DataBindingUtil.bind(mView);
                //绑定数据
                leftBinding.setMsg(msg);
                leftBinding.setUserHead(userHead);
                leftBinding.setUserInfo(userInfo);

                chattingImage = leftBinding.ivMessageLeftContentImage;
                headImage = leftBinding.ivMessageLeftHead;
            } else if (msg.getType() == Constant.TYPE_MSG_SEND) {   //发送消息
                ItemMessageRightBinding rightBinding =
                        DataBindingUtil.bind(mView);
                //绑定数据
                rightBinding.setMsg(msg);
                rightBinding.setUserHead(userHead);
                rightBinding.setUserInfo(userInfo);

                chattingImage = rightBinding.ivMessageRightContentImage;
                headImage = rightBinding.ivMessageRightHead;
            }

            //查看大图
            onClickForImageDetail(chattingImage, msg);
            //查看用户详细信息
            onClickForUserDetailInfo(headImage, msg);
        }

        /**
         * 点击放大聊天对话中的图片
         */
        private void onClickForImageDetail(ImageView iv, final ChattingMessage msg) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getInstance(), ImageActivity.class);
                    ImageDetail detail = new ImageDetail(msg.getImage());
                    //传递数据
                    intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getInstance().startActivity(intent);
                }
            });
        }

        /**
         * 点击头像，查看用户详细信息，及最近聊天记录
         *
         * @param iv  头像
         * @param msg 该条消息
         */
        private void onClickForUserDetailInfo(ImageView iv, final ChattingMessage msg) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mView.getContext(), UserInfoActivity.class);
                    intent.putExtra(Constant.EXTRA_GROUP_ID, msg.getGroupid());
                    intent.putExtra(Constant.EXTRA_USER_ID, msg.getUserid());
                    mView.getContext().startActivity(intent);
                }
            });
        }
    }
}