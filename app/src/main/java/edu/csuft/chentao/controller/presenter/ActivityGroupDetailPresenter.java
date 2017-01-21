package edu.csuft.chentao.controller.presenter;

import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.adapter.UserInGroupAdapter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.resp.UserCapitalResp;
import edu.csuft.chentao.pojo.resp.UserIdsInGroupResp;
import edu.csuft.chentao.utils.Constant;
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_GROUP_DETAIL) {
                UserIdsInGroupResp resp = (UserIdsInGroupResp) msg.obj;
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
    };

    public ActivityGroupDetailPresenter(ActivityGroupDetailBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void init() {
        EventBus.getDefault().post(new HandlerMessage(mHandler, "GroupDetailActivity"));
        initData();
    }

    private void initData() {

    }
}