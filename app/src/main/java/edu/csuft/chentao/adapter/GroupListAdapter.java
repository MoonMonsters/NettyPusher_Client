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

    public GroupListAdapter(List<Groups> groupsList) {
        this.mGroupsList = groupsList;
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

        GroupListViewHolder(View itemView) {
            super(itemView);
            mItemBinding = DataBindingUtil.bind(itemView);
        }

        void bindData(Groups groups) {
            mItemBinding.setItem(groups);
        }
    }

}
