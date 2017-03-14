package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.databinding.ItemRecentMsgBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2017-01-08 14:21.
 * email:qxinhai@yeah.net
 */

/**
 * DataBinding式Adapter
 */
public class RecentMessageAdapter extends BaseAdapter {

    private List<ChattingMessage> mChattingMessageList = null;
    private Context mContext;
    private int mLayoutId;
    private int mVariableId;

    public RecentMessageAdapter(Context context,
                                List<ChattingMessage> chattingMessageList,
                                int layoutId,
                                int variableId) {
        this.mContext = context;
        this.mChattingMessageList = chattingMessageList;
        this.mLayoutId = layoutId;
        this.mVariableId = variableId;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemRecentMsgBinding viewDataBinding = null;
        if (convertView == null) {
            viewDataBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(mContext),
                            mLayoutId, parent, false);
        } else {
            viewDataBinding = DataBindingUtil.getBinding(convertView);
        }

        //设置Bean类
        viewDataBinding.setVariable(mVariableId, mChattingMessageList.get(position));

        //设置Presenter
        ItemRecentMessagePresenter itemPresenter = new
                ItemRecentMessagePresenter(mChattingMessageList.get(position));
        viewDataBinding.setItemPresenter(itemPresenter);

        return viewDataBinding.getRoot();
    }

    /**
     * ItemRecentMsg的Presenter类
     */
    public class ItemRecentMessagePresenter {

        private ChattingMessage mChattingMessage = null;

        ItemRecentMessagePresenter(ChattingMessage chattingMessage) {
            this.mChattingMessage = chattingMessage;
        }

        /**
         * 点击查看大图
         */
        public void onClickToBigImage() {
            Intent intent = new Intent(mContext, ImageActivity.class);
            ImageDetail detail = new ImageDetail(null,mChattingMessage.getImage());
            intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
            mContext.startActivity(intent);
        }
    }
}