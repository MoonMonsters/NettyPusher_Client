package edu.csuft.chentao.controller.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.csuft.chentao.adapter.UserInGroupAdapter;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //刷新界面
            if (msg.what == Constant.HANDLER_PRESENTER_REFRESH_USERINFO) {
                int userId = msg.arg1;
                UserInfo userInfo = UserInfoDaoUtil
                        .getUserInfo(userId);
                //避免存在相同的数据
                if (mUserInfoList.contains(userInfo)) {
                    return;
                }
                mUserInfoList.add(userInfo);
                mAdapter.notifyDataSetChanged();
                //用户身份值改变
            } else if (msg.what == Constant.HANDLER_PRESENTER_REFRESH_CAPITAL) {
                ReturnInfoResp resp = (ReturnInfoResp) msg.obj;
                Toast.makeText(mActivityBinding.getRoot().getContext(), resp.getDescription(), Toast.LENGTH_SHORT).show();
                //刷新
                if (resp.getType() == Constant.TYPE_RETURN_INFO_UPDATE_USER_CAPITAL_SUCCESS) {
                    mAdapter.notifyChanged();
                }
            }
        }
    };

    public ActivityGroupDetailPresenter(ActivityGroupDetailBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void init() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new HandlerMessage(mHandler, "GroupDetailActivity"));
        initData();
    }

    private void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserIdsInGroupResp(UserIdsInGroupResp resp) {
        LoggerUtil.logger(Constant.TAG, "UserIdsInGroupResp-->" + resp.toString());
        mGroupId = resp.getGroupId();
        @SuppressLint("UseSparseArrays") Map<Integer, Integer> idCapital = new HashMap<>();
        for (UserCapitalResp ucr : resp.getUserIdCapitalList()) {
            idCapital.put(ucr.getUserId(), ucr.getCapital());
        }
        mUserInfoList = UserInfoDaoUtil.getAllUserInfosWithGroupIdMap(idCapital);
        mAdapter = new UserInGroupAdapter(mActivityBinding.getRoot().getContext(),
                mUserInfoList, idCapital, mGroupId);
        mActivityBinding.setAdapter(mAdapter);
    }
}