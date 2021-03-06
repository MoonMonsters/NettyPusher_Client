package edu.csuft.chentao.controller.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityHintBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.ui.activity.HintActivity;
import edu.csuft.chentao.ui.adapter.HintAdapter;
import edu.csuft.chentao.ui.view.DividerItemDecoration;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;

/**
 * Created by Chalmers on 2017-02-09 23:46.
 * email:qxinhai@yeah.net
 */

public class ActivityHintPresenter extends BasePresenter {

    private ActivityHintBinding mActivityBinding;
    //数据集合
    private List<Hint> mHintList;
    //适配器
    private HintAdapter mAdapter;

    public ActivityHintPresenter(ActivityHintBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    @Override
    protected void initData() {

        if (mHintList == null) {
            mHintList = new ArrayList<>();
        }
        mHintList.addAll(HintDaoUtil.loadAll());

        mAdapter = new HintAdapter(mActivityBinding.getRoot().getContext(), mHintList);
        mActivityBinding.rvHintContent.setLayoutManager(new LinearLayoutManager(mActivityBinding.getRoot().getContext()));
        mActivityBinding.rvHintContent.addItemDecoration(new DividerItemDecoration(mActivityBinding.getRoot().getContext(),
                DividerItemDecoration.HORIZONTAL));
        mActivityBinding.setVariable(BR.adapter, mAdapter);
        mActivityBinding.setVariable(BR.title, "提示信息");
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_HINT_PRESENTER)) {
            Hint hint = (Hint) ebObj.getObject();
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

    @Override
    public void initListener() {
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HintActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }
}