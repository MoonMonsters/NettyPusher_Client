package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ItemChattingListBinding;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.ui.activity.MessageActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;

/**
 * Created by Chalmers on 2017-01-10 20:42.
 * email:qxinhai@yeah.net
 */

public class ChattingListAdapter2 extends BaseAdapter {

    private List<GroupChattingItem> mGroupChattingItemList;
    private Context mContext;

    private ChattingListAdapter2.ICloseOpenedItems mListener;

    public ChattingListAdapter2(Context context, ICloseOpenedItems listener, List<GroupChattingItem> groupChattingItemList) {
        this.mContext = context;
        this.mGroupChattingItemList = groupChattingItemList;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mGroupChattingItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupChattingItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemChattingListBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_chatting_list, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setItem(mGroupChattingItemList.get(position));
        binding.setItemPresenter(new ItemChattingListPresenter(mGroupChattingItemList.get(position)));
        return binding.getRoot();
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

        /**
         * 点击移除红点，表名该群消息不需要读取
         */
        public void onClickToRemoveRedDot() {
            mChattingItem.setNumber(0);
            GroupChattingItemDaoUtil.updateGroupChattingItem(mChattingItem);
        }

        /**
         * 删除选中项
         */
        public void onClickToDeleteItem() {
            //需要删除的位置
            int position = mGroupChattingItemList.indexOf(mChattingItem);
            //发送消息到FragmentChattingListPresenter去移除掉position位置的数据项
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_REMOVE_ITEM, position);
            mListener.closedOpenedItems();
        }
    }

    /**
     * 关闭所有打开的对话框
     */
    public interface ICloseOpenedItems {
        void closedOpenedItems();
    }
}