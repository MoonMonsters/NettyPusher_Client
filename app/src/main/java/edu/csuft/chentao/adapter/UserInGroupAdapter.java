package edu.csuft.chentao.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.UserInfoActivity;
import edu.csuft.chentao.databinding.ItemUserInGroupBinding;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;

/**
 * Created by Chalmers on 2017-01-19 15:44.
 * email:qxinhai@yeah.net
 */

public class UserInGroupAdapter extends BaseAdapter {

    private List<UserInfo> mUserInfoList;
    private Context mContext;
    private Map<Integer, Integer> mCapitalMap;
    private int mGroupId;

    public UserInGroupAdapter(Context context, List<UserInfo> userInfoList,
                              Map<Integer, Integer> capitalMap, int groupId) {
        this.mContext = context;
        this.mUserInfoList = userInfoList;
        this.mCapitalMap = capitalMap;
        this.mGroupId = groupId;
    }

    @Override
    public int getCount() {
        return mUserInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemUserInGroupBinding itemBinding;

        if (convertView == null) {
            itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.item_user_in_group, parent, false);
        } else {
            itemBinding = DataBindingUtil.getBinding(convertView);
        }

        final UserInfo userInfo = mUserInfoList.get(position);
        UserHead userHead = UserHeadDaoUtil.getUserHead(userInfo.getUserid());
        itemBinding.setVariable(BR.capital, mCapitalMap.get(userInfo.getUserid()));
        itemBinding.setVariable(BR.userInfo, userInfo);
        itemBinding.setVariable(BR.userHead, userHead);
        //用户选项的点击事件
        itemBinding.layoutUserInGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
                intent.putExtra(Constant.EXTRA_USER_ID, userInfo.getUserid());
                mContext.startActivity(intent);
            }
        });
        return itemBinding.getRoot();
    }
}