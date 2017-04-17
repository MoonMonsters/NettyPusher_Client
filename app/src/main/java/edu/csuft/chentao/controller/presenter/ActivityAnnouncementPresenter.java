package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.view.View;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityAnnouncementBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.ui.activity.AnnouncementActivity;
import edu.csuft.chentao.ui.activity.PublishAnnouncementActivity;
import edu.csuft.chentao.ui.adapter.AnnouncementAdapter;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.LocalAnnouncementDaoUtil;

/**
 * AnnouncementActivity的协助处理类
 */
public class ActivityAnnouncementPresenter extends BasePresenter {

    private ActivityAnnouncementBinding mActivityBinding;
    private int mGroupId;
    private List<LocalAnnouncement> mAnnouncementList;
    private AnnouncementAdapter mAdapter;

    public ActivityAnnouncementPresenter(ActivityAnnouncementBinding activityBinding) {
        this.mActivityBinding = activityBinding;

        init();
    }

    @Override
    protected void initData() {
        mGroupId = ((AnnouncementActivity) mActivityBinding.getRoot().getContext()).getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        //得到所有的群公告数据
        mAnnouncementList = LocalAnnouncementDaoUtil.getAllLocalAnnouncements(mGroupId);
        LoggerUtil.logger("TAg", "ActivityAnnouncementPresenter-->initData.list.size=" + mAnnouncementList.size());
        //构造适配器
        mAdapter = new AnnouncementAdapter(mActivityBinding.getRoot().getContext(), mAnnouncementList);
        //设置Activity的标题
        mActivityBinding.setVariable(BR.title, "群公告");
        //传递数据，设置ListView的适配器
        mActivityBinding.setVariable(BR.adapter, mAdapter);
    }

    @Override
    public void initListener() {
        //设置返回按键的点击事件
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnnouncementActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    @Override
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_ANNOUNCEMENT_PRESENTER)) {
            LocalAnnouncement la = (LocalAnnouncement) ebObj.getObject();
            //添加到第一个位置
            mAnnouncementList.add(0, la);
            //刷新界面
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 点击菜单栏，发布新公告
     */
    public void doMenuToPublishNewAnnouncement() {
        //携带groupId数据，跳转到发布公告界面去
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), PublishAnnouncementActivity.class);
        intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }
}
