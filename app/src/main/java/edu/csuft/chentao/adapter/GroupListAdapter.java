package edu.csuft.chentao.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ItemGroupsBinding;
import edu.csuft.chentao.pojo.bean.Groups;

/**
 * Created by Chalmers on 2016-12-23 15:32.
 * email:qxinhai@yeah.net
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder> {

    private List<Groups> mGroupsList;

    private OnItemClick mOnItemClick;

    public GroupListAdapter(List<Groups> groupsList, OnItemClick onItemClick) {
        this.mGroupsList = groupsList;
        this.mOnItemClick = onItemClick;
    }

    @Override
    public GroupListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_groups, parent, false);

        return new GroupListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListViewHolder holder, int position) {
        holder.bindData(mGroupsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroupsList.size();
    }

    class GroupListViewHolder extends RecyclerView.ViewHolder {

        private ItemGroupsBinding mItemBinding = null;
        private View mView = null;

        GroupListViewHolder(View itemView) {
            super(itemView);
            mItemBinding = DataBindingUtil.bind(itemView);
            this.mView = itemView;
        }

        void bindData(final Groups groups) {
            mItemBinding.setItem(groups);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClick.onItemClickListener(mGroupsList.indexOf(groups));
                }
            });
        }
    }

    public interface OnItemClick {
        void onItemClickListener(int position);
    }


}
