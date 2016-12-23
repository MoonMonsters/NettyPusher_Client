package edu.csuft.chentao.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ItemChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;

/**
 * Created by Chalmers on 2016-12-22 17:57.
 * email:qxinhai@yeah.net
 */

public class GroupChattingAdapter extends RecyclerView.Adapter<GroupChattingAdapter.GroupChattingViewHandler> {

    private ArrayList<GroupChattingItem> mGroupChattingItemList;

    public GroupChattingAdapter(ArrayList<GroupChattingItem> groupChattingItemList) {
        this.mGroupChattingItemList = groupChattingItemList;
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

        void bindData(GroupChattingItem item) {
            mItemBinding.setItem(item);
        }
    }
}
