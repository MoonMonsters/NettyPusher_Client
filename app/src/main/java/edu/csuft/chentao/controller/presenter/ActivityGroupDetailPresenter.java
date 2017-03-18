package edu.csuft.chentao.controller.presenter;

import android.annotation.SuppressLint;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.ui.activity.GroupDetailActivity;
import edu.csuft.chentao.ui.adapter.UserInGroupAdapter;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.GetUserAndGroupInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.pojo.resp.UserCapitalResp;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.pojo.resp.UserInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2017-01-19 13:57.
 * email:qxinhai@yeah.net
 */

/**
 * 详细的群数据的Presenter
 */
public class ActivityGroupDetailPresenter extends BasePresenter {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private UserInGroupAdapter mAdapter = null;
    /**
     * 群id
     */
    private int mGroupId;
    private List<UserInfo> mUserInfoList;

    public ActivityGroupDetailPresenter(ActivityGroupDetailBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    @Override
    protected void initData() {
        this.mGroupId = ((GroupDetailActivity) mActivityBinding.getRoot().getContext())
                .getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        Groups groups = GroupsDaoUtil.getGroups(mGroupId);
        mActivityBinding.setVariable(BR.groups, groups);

        getUserAndGroupInfo();
    }

    /**
     * 发送消息，获取群用户信息
     */
    private void getUserAndGroupInfo() {
        /*
        向服务端发送数据，获得群中用户数据
         */
        GetUserAndGroupInfoReq req = new GetUserAndGroupInfoReq();
        req.setType(Constant.TYPE_USER_GROUP_INFO_GROUP);
        req.setId(mGroupId);
        SendMessageUtil.sendMessage(req);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //删除用户，数据类型设置错误了，最开始没想到这么多
        //就是接收了从Handler中传递过来的数据
        if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            if (resp.getType() == Constant.TYPE_RETURN_INFO_REMOVE_USER_SUCCESS) {
                Toast.makeText(mActivityBinding.getRoot().getContext(), resp.getDescription(), Toast.LENGTH_SHORT).show();
                mAdapter.removeUserAndNotifyChanged();
            }
            //刷新用户的身份值，从Handler传递过来的数据
        } else if (ebObj.getTag().equals(Constant.TAG_REFRESH_USER_CAPITAL)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            Toast.makeText(mActivityBinding.getRoot().getContext(), resp.getDescription(), Toast.LENGTH_SHORT).show();
            //刷新
            if (resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS) {
                mAdapter.notifyChanged();
            }
            //接收从Handler传递过来的数据，是用户身份信息值
            // 接收来自Handler的UserIdsInGroupResp数据
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER2)) {
            UserIdsInGroupResp resp = (UserIdsInGroupResp) ebObj.getObject();
            @SuppressLint("UseSparseArrays") Map<Integer, Integer> idCapital = new HashMap<>();
            for (UserCapitalResp ucr : resp.getUserIdCapitalList()) {
                idCapital.put(ucr.getUserId(), ucr.getCapital());
            }
            mUserInfoList = UserInfoDaoUtil.getAllUserInfosWithGroupIdMap(idCapital);
            mAdapter = new UserInGroupAdapter(mActivityBinding.getRoot().getContext(),
                    mUserInfoList, idCapital, mGroupId);
            mActivityBinding.setAdapter(mAdapter);
        /*
        当查看群中数据时，而此时没有该用户，则向服务端请求用户数据，此时通过
        Handler传递过来
         */
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_GROUP_DETAIL_PRESENTER_ADD_USER)) {
            UserInfoResp resp = (UserInfoResp) ebObj.getObject();
            UserInfo userInfo = UserInfoDaoUtil
                    .getUserInfo(resp.getUserid());
            //避免存在相同的数据
            if (mUserInfoList.contains(userInfo)) {
                return;
            }
            mUserInfoList.add(userInfo);
            mAdapter.notifyDataSetChanged();
        }
    }
}