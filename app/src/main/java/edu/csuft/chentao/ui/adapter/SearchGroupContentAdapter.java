package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ItemSearchGroupInfoBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2017-02-04 16:10.
 * email:qxinhai@yeah.net
 */

public class SearchGroupContentAdapter extends BaseAdapter {

    private List<GroupInfoResp> mGroupInfoList;
    private Context mContext;

    public SearchGroupContentAdapter(Context context, List<GroupInfoResp> groupInfoList) {
        this.mContext = context;
        this.mGroupInfoList = groupInfoList;
    }

    @Override
    public int getCount() {
        return mGroupInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroupInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSearchGroupInfoBinding binding;

        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_search_group_info,
                    parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        GroupInfoResp groupInfo = mGroupInfoList.get(position);
        ItemSearchGroupContentPresenter presenter = new ItemSearchGroupContentPresenter(groupInfo);
        binding.setVariable(BR.itemPresenter, presenter);
        binding.setVariable(BR.groupInfo, groupInfo);

        return binding.getRoot();
    }

    public class ItemSearchGroupContentPresenter {
        private GroupInfoResp mGroupInfo;

        ItemSearchGroupContentPresenter(GroupInfoResp groupInfo) {
            this.mGroupInfo = groupInfo;
        }

        /**
         * 点击加入群
         */
        public void onClickToEnterGroup() {
            GroupOperationReq req = new GroupOperationReq();
            req.setType(Constant.TYPE_GROUP_OPERATION_ADD_BY_MYSELF);
            req.setGroupid(mGroupInfo.getGroupid());
            req.setUserId1(SharedPrefUserInfoUtil.getUserId());
            req.setUserId2(-1);
            SendMessageUtil.sendMessage(req);
        }

        /**
         * 查看大图
         */
        public void onClickToBigImage() {
            Intent intent = new Intent(MyApplication.getInstance(), ImageActivity.class);
            ImageDetail detail = new ImageDetail(null,mGroupInfo.getHeadImage());
            //传递数据
            intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getInstance().startActivity(intent);
        }
    }
}