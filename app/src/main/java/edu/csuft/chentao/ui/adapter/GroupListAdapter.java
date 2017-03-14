package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.MessageActivity;
import edu.csuft.chentao.databinding.ItemGroupsBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2016-12-23 15:32.
 * email:qxinhai@yeah.net
 */

/**
 * 所有群列表
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder> {

    private List<Groups> mGroupsList;
    private Context mContext;

    public GroupListAdapter(Context context, List<Groups> groupsList) {
        this.mGroupsList = groupsList;
        this.mContext = context;
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

        void bindData(final Groups groups) {
            mItemBinding.setItem(groups);
            //设置Presenter
            mItemBinding.setItemPresenter(new ItemGroupListPresenter(groups));
        }
    }

    /**
     * ItemGroupList的Presenter
     */
    public class ItemGroupListPresenter {
        private Groups mGroups;

        ItemGroupListPresenter(Groups groups) {
            this.mGroups = groups;
        }

        /**
         * 点击进入MessageActivity
         */
        public void onClickToEnterMessageActivity() {
            Intent intent = new Intent(mContext, MessageActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, mGroups.getGroupid());
            mContext.startActivity(intent);
        }
    }
}
