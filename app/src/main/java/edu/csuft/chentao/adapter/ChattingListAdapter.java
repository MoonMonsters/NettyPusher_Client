package edu.csuft.chentao.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.MessageActivity;
import edu.csuft.chentao.databinding.ItemChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-22 17:57.
 * email:qxinhai@yeah.net
 */

public class ChattingListAdapter extends RecyclerView.Adapter<ChattingListAdapter.GroupChattingViewHandler> {

    private ArrayList<GroupChattingItem> mGroupChattingItemList;
    private Context mContext;

    public ChattingListAdapter(Context context, ArrayList<GroupChattingItem> groupChattingItemList) {
        this.mGroupChattingItemList = groupChattingItemList;
        this.mContext = context;
    }

    @Override
    public GroupChattingViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chatting_list, parent, false);

        return new GroupChattingViewHandler(view);
    }

    @Override
    public void onBindViewHolder(GroupChattingViewHandler holder, int position) {
        holder.bindData(mGroupChattingItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroupChattingItemList.size();
    }

    class GroupChattingViewHandler extends RecyclerView.ViewHolder {

        private ItemChattingListBinding mItemBinding;

        GroupChattingViewHandler(View itemView) {
            super(itemView);
            mItemBinding = DataBindingUtil.bind(itemView);
        }

        void bindData(final GroupChattingItem item) {
            mItemBinding.setItem(item);
            mItemBinding.setItemPresenter(new ItemChattingListPresenter(item));
        }
    }

    /**
     * 聊天框界面的Item的Presenter
     */
    public class ItemChattingListPresenter {

        private GroupChattingItem mChattingItem;

        ItemChattingListPresenter(GroupChattingItem chattingItem) {
            this.mChattingItem = chattingItem;
        }

        /**
         * 整个Item的点击事件
         */
        public void onClickToEnterMessageActivity() {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, mChattingItem.getGroupid());
            mContext.startActivity(intent);
        }
    }
}