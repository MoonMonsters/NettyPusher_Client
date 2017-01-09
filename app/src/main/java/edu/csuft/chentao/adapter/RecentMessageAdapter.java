package edu.csuft.chentao.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.ImageActivity;
import edu.csuft.chentao.databinding.ItemRecentMsgBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2017-01-08 14:21.
 * email:qxinhai@yeah.net
 */

public class RecentMessageAdapter extends BaseAdapter {

    private List<ChattingMessage> mChattingMessageList = null;
    private Context mContext;

    public RecentMessageAdapter(Context context, List<ChattingMessage> chattingMessageList) {
        this.mContext = context;
        this.mChattingMessageList = chattingMessageList;
    }

    @Override
    public int getCount() {
        return mChattingMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChattingMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RecentMessageViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_recent_msg, parent, false);
            viewHolder = new RecentMessageViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecentMessageViewHolder) convertView.getTag();
        }

        viewHolder.bindData(mChattingMessageList.get(position));

        return convertView;
    }

    private class RecentMessageViewHolder {
        private ItemRecentMsgBinding mItemBinding = null;

        RecentMessageViewHolder(View view) {
            mItemBinding = DataBindingUtil.bind(view);
        }

        void bindData(ChattingMessage message) {
            mItemBinding.setChattingMessage(message);

            onClickToBigImage(mItemBinding.ivRecentMsgImage, message.getImage());
        }

        /**
         * 点击查看大图
         */
        private void onClickToBigImage(ImageView iv, final byte[] buf) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    ImageDetail detail = new ImageDetail(buf);
                    intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
