package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.UserInfoActivity;
import edu.csuft.chentao.databinding.ItemManagerUserBinding;
import edu.csuft.chentao.databinding.ItemUserInGroupBinding;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.req.ManagerUserReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;

/**
 * Created by Chalmers on 2017-01-19 15:44.
 * email:qxinhai@yeah.net
 */

/**
 * 显示群成员列表的Adapter
 */
public class UserInGroupAdapter extends BaseAdapter {

    /**
     * 群成员信息列表
     */
    private List<UserInfo> mUserInfoList;
    private Context mContext;
    /**
     * id对应的身份值
     */
    private Map<Integer, Integer> mCapitalMap;
    /**
     * 群id
     */
    private int mGroupId;

    /**
     * 改变用户身份值用到的变量
     */
    private int mCapitalChanged;
    /**
     * 因为不是一次性就加载了群成员的信息，这个是当接收到新的成员信息时，加载的成员信息的id
     */
    private int mUserIdChanged;
    /**
     * 把用户踢出去用到的变量
     */
    private int mUserRemoved;

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
        final ItemManagerUserPresenter itemPresenter = new ItemManagerUserPresenter(userInfo);

        /*
        设置属性值
         */
        itemBinding.setVariable(BR.capital, mCapitalMap.get(userInfo.getUserid()));
        itemBinding.setVariable(BR.userInfo, userInfo);
        itemBinding.setVariable(BR.userHead, userHead);
        itemBinding.setVariable(BR.itemPresenter, itemPresenter);
        itemBinding.setVariable(BR.userId, userInfo.getUserid());

        itemBinding.layoutUserInGroup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按事件
                itemPresenter.onLongClick(v);

                return true;
            }
        });

        return itemBinding.getRoot();
    }

    /**
     * 刷新
     */
    public void notifyChanged() {
        mCapitalMap.put(mUserIdChanged, mCapitalChanged);
        this.notifyDataSetChanged();
    }

    /**
     * 踢出用户，如果踢出成功，则刷新一下界面
     */
    public void removeUserAndNotifyChanged() {
        mCapitalMap.remove(mUserRemoved);
        UserInfo userInfo = null;
        for (UserInfo ui : mUserInfoList) {
            if (ui.getUserid() == mUserRemoved) {
                userInfo = ui;
                break;
            }
        }
        mUserInfoList.remove(userInfo);
        this.notifyDataSetChanged();
    }

    public class ItemManagerUserPresenter {
        private UserInfo mUserInfo;
        private PopupWindow mPopupWindow;

        ItemManagerUserPresenter(UserInfo userInfo) {
            this.mUserInfo = userInfo;
        }

        /**
         * 点击查看用户详细信息
         */
        public void onClickToUserDetail() {
            Intent intent = new Intent(mContext, UserInfoActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
            intent.putExtra(Constant.EXTRA_USER_ID, mUserInfo.getUserid());
            mContext.startActivity(intent);
        }

        /**
         * 点击长按操作用户
         */
        public boolean onLongClick(View v) {

            //记录下需要修改的用户id
            mUserIdChanged = mUserInfo.getUserid();

            mPopupWindow = new PopupWindow(mContext);
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_manager_user, null);
            mPopupWindow.setContentView(view);
//            popupWindow.showAsDropDown(v,0,0,Gravity.CENTER);
            //此处必须设置，否则触摸无效
            mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_back_gray));
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setTouchable(true);
            //显示位置
            mPopupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL, 0, (int) (v.getY() + v.getHeight() / 2));
//            mPopupWindow.showAsDropDown(v);
            //DataBinding
            ItemManagerUserBinding binding = DataBindingUtil.bind(view);
            binding.setVariable(BR.itemPresenter, this);

            return true;
        }

        /**
         * 设置管理员
         */
        public void onClickToSetAdmin() {
            mCapitalChanged = Constant.TYPE_GROUP_CAPITAL_ADMIN;
            dismissPopupWindow();

            //群主不能修改自己身份
            if (mUserIdChanged == SharedPrefUserInfoUtil.getUserId()) {
                Toast.makeText(mContext, "不能修改自己身份", Toast.LENGTH_SHORT).show();
                return;
            }

            //得到该群中，用户的身份值
            int capital = mCapitalMap.get(SharedPrefUserInfoUtil.getUserId());
            //能改成admin的只有owner
            if (capital == Constant.TYPE_GROUP_CAPITAL_OWNER) {
                //发送消息改变值
                ManagerUserReq req = new ManagerUserReq();
                req.setCapital(Constant.TYPE_GROUP_CAPITAL_ADMIN);
                req.setUserId(mUserIdChanged);
                req.setGroupId(mGroupId);
                SendMessageUtil.sendMessage(req);
            } else {
                Toast.makeText(mContext, "你没有该权限", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 设置为普通用户
         */
        public void onClickToSetCommonUser() {
            mCapitalChanged = Constant.TYPE_GROUP_CAPITAL_USER;
            dismissPopupWindow();

            //群主不能修改自己身份
            if (mUserIdChanged == SharedPrefUserInfoUtil.getUserId()) {
                Toast.makeText(mContext, "不能修改自己身份", Toast.LENGTH_SHORT).show();
                return;
            }

            int myCapital = mCapitalMap.get(SharedPrefUserInfoUtil.getUserId());
            int hisCapital = mCapitalMap.get(mUserIdChanged);
            //如果用户的身份值小于要修改用户的身份值，则权限足够
            if (myCapital < hisCapital) {
                ManagerUserReq req = new ManagerUserReq();
                req.setCapital(Constant.TYPE_GROUP_CAPITAL_USER);
                req.setUserId(mUserIdChanged);
                req.setGroupId(mGroupId);
                SendMessageUtil.sendMessage(req);
            } else {
                Toast.makeText(mContext, "你没有该权限", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 移除用户
         */
        public void onClickToRemoveUser() {
            int myCapital = mCapitalMap.get(SharedPrefUserInfoUtil.getUserId());
            int hisCapital = mCapitalMap.get(mUserInfo.getUserid());

            //如果身份高于，则踢出群
            if (myCapital < hisCapital) {
                //得到被踢出的用户的id
                mUserRemoved = mUserInfo.getUserid();
                GroupOperationReq req = new GroupOperationReq();
                req.setType(Constant.TYPE_GROUP_OPERATION_EXIT_BY_ADMIN);
                req.setUserId1(mUserInfo.getUserid());
                req.setUserId2(SharedPrefUserInfoUtil.getUserId());
                req.setGroupid(mGroupId);
                SendMessageUtil.sendMessage(req);
            } else {
                Toast.makeText(mContext, "你没有该权限", Toast.LENGTH_SHORT).show();
            }

            dismissPopupWindow();
        }

        /**
         * 当点击PopupWindow时，关闭
         */
        private void dismissPopupWindow() {
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
        }
    }
}