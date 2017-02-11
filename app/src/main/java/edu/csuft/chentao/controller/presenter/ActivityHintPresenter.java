package edu.csuft.chentao.controller.presenter;

import android.support.v7.widget.LinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.adapter.HintAdapter;
import edu.csuft.chentao.databinding.ActivityHintBinding;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.pojo.resp.GroupReminderResp;
import edu.csuft.chentao.utils.CopyUtil;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;

/**
 * Created by Chalmers on 2017-02-09 23:46.
 * email:qxinhai@yeah.net
 */

public class ActivityHintPresenter {

    private ActivityHintBinding mActivityBinding;

    private List<Hint> mHintList;
    private HintAdapter mAdapter;

    public ActivityHintPresenter(ActivityHintBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    private void init() {
        //注册
        EventBus.getDefault().register(this);
        if (mHintList == null) {
            mHintList = new ArrayList<>();
        }
        mHintList.addAll(HintDaoUtil.loadAll());

        mAdapter = new HintAdapter(mActivityBinding.getRoot().getContext(), mHintList);
        mActivityBinding.rvHintContent.setLayoutManager(new LinearLayoutManager(mActivityBinding.getRoot().getContext()));
        mActivityBinding.setVariable(BR.adapter, mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGroupReminderData(GroupReminderResp resp) {
        Hint hint = CopyUtil.saveHintFromGroupReminder(resp);
        if (mHintList == null) {
            mHintList = new ArrayList<>();
        }
        if (mHintList.contains(hint)) {
            return;
        }
        mHintList.add(hint);
        mAdapter.notifyDataSetChanged();
    }
}
