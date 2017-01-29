package edu.csuft.chentao.controller.presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.adapter.UserInGroupAdapter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.resp.UserCapitalResp;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2017-01-19 13:57.
 * email:qxinhai@yeah.net
 */

public class ActivityGroupDetailPresenter {

    private ActivityGroupDetailBinding mActivityBinding = null;
    private UserInGroupAdapter mAdapter = null;
    /**
     * 群id
     */
    private int mGroupId;
    private List<UserInfo> mUserInfoList;

    public ActivityGroupDetailPresenter(ActivityGroupDetailBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void init() {
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserIdsInGroupResp(UserIdsInGroupResp resp){
        LoggerUtil.logger(Constant.TAG, "UserIdsInGroupResp-->" + resp.toString());
        mGroupId = resp.getGroupId();
        Map<Integer, Integer> idCapital = new HashMap<>();
        for (UserCapitalResp ucr : resp.getUserIdCapitalList()) {
            idCapital.put(ucr.getUserId(), ucr.getCapital());
        }
        mUserInfoList = UserInfoDaoUtil.getAllUserInfosWithGroupIdMap(idCapital);
        mAdapter = new UserInGroupAdapter(mActivityBinding.getRoot().getContext(),
                mUserInfoList, idCapital, mGroupId);
        mActivityBinding.setAdapter(mAdapter);
    }
}