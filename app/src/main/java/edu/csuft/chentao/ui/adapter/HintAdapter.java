package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ItemHintMessageBinding;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;

/**
 * Created by Chalmers on 2017-02-09 23:31.
 * email:qxinhai@yeah.net
 */

public class HintAdapter extends RecyclerView.Adapter<HintAdapter.HintViewHolder> {
    private Context mContext;
    private List<Hint> mHintList;

    public HintAdapter(Context context, List<Hint> hintList) {
        this.mContext = context;
        this.mHintList = hintList;
    }

    @Override
    public HintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_hint_message, parent, false);

        return new HintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HintViewHolder holder, int position) {
        holder.bindData(mHintList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHintList.size();
    }


    class HintViewHolder extends RecyclerView.ViewHolder {

        private ItemHintMessageBinding mBinding;

        HintViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        void bindData(Hint hint) {
            ItemHintPresenter itemPresenter = new ItemHintPresenter(hint);
            mBinding.setVariable(BR.hintData, hint);
            mBinding.setVariable(BR.itemPresenter, itemPresenter);
        }
    }

    public class ItemHintPresenter {

        private Hint mHint;

        ItemHintPresenter(Hint hint) {
            this.mHint = hint;
        }

        /**
         * 同意按钮
         */
        public void onClickToAgree() {

            //得到当前Hint对象在List中位置
            int position = mHintList.indexOf(mHint);

            //有人申请加入群
            if (mHint.getType() == Constant.TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP) {
                GroupOperationReq req = new GroupOperationReq();
                req.setUserId1(mHint.getUserid());
                req.setType(Constant.TYPE_GROUP_OPERATION_AGREE_ADD_GROUP);
                req.setGroupid(mHint.getGroupid());
                //同意申请人加入群
                SendMessageUtil.sendMessage(req);

                //有人邀请加入群
            } else if (mHint.getType() == Constant.TYPE_GROUP_REMINDER_INVITE_GROUP) {
                GroupOperationReq req = new GroupOperationReq();
                req.setGroupid(mHint.getGroupid());
                req.setType(Constant.TYPE_GROUP_OPERATION_ADD_BY_MYSELF);
                req.setUserId1(SharedPrefUserInfoUtil.getUserId());
                //同意邀请加入，同意后，便相当于自己重新申请加入群
                SendMessageUtil.sendMessage(req);
            }

            //修改Hint信息
            mHint.setShow(Constant.TYPE_HINT_SHOW_AGREE);
            //在数据表中修改
            HintDaoUtil.update(mHintList);
            //在List中修改
            mHintList.set(position, mHint);
            //刷新
            HintAdapter.this.notifyDataSetChanged();
        }

        /**
         * 拒绝按钮
         */
        public void onClickToRefuse() {

            int position = mHintList.indexOf(mHint);

            //有人申请加入群
            if (mHint.getType() == Constant.TYPE_GROUP_REMINDER_WANT_TO_ADD_GROUP) {
                GroupOperationReq req = new GroupOperationReq();
                req.setType(Constant.TYPE_GROUP_OPERATION_REFUSE_ADD_GROUP);
                req.setUserId1(mHint.getUserid());
                req.setUserId2(SharedPrefUserInfoUtil.getUserId());
                req.setGroupid(mHint.getGroupid());
                SendMessageUtil.sendMessage(req);

                //有人邀请加入群
            } else if (mHint.getType() == Constant.TYPE_GROUP_REMINDER_INVITE_GROUP) {
                Toast.makeText(mContext, "已拒绝", Toast.LENGTH_SHORT).show();
            }

            //修改Hint信息
            mHint.setShow(Constant.TYPE_HINT_SHOW_REFUSE);
            //在数据表中修改
            HintDaoUtil.update(mHintList);
//            mHintList.remove(position);
            //在List中修改
            mHintList.set(position, mHint);
            //刷新
            HintAdapter.this.notifyDataSetChanged();
        }

        /**
         * 长按点击事件
         */
        public boolean onLongClick(View v) {

            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_textview_delete, null, false);

            final PopupWindow popupWindow = new PopupWindow(mContext);
            popupWindow.setContentView(view);
            popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_back_gray));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(RecyclerView.LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, 0);
            //删除数据
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HintDaoUtil.remove(mHint);
                    mHintList.remove(mHint);
                    HintAdapter.this.notifyDataSetChanged();
                    popupWindow.dismiss();
                }
            });

            return true;
        }
    }
}
