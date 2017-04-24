package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.view.View;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityGroupSettingBinding;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.ui.activity.AnnouncementActivity;
import edu.csuft.chentao.ui.activity.FileActivity;
import edu.csuft.chentao.ui.activity.GroupSettingActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.ui.view.CustomerAlertDialog;
import edu.csuft.chentao.ui.view.InvitePersonDialog;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

public class ActivityGroupSettingPresenter extends BasePresenter implements InvitePersonDialog.IDialogClickListener, CustomerAlertDialog.IAlertDialogClickListener {

    private ActivityGroupSettingBinding mActivityBinding;
    private int mGroupId;

    public ActivityGroupSettingPresenter(ActivityGroupSettingBinding activityBinding) {
        this.mActivityBinding = activityBinding;

        init();
    }

    @Override
    protected void initData() {
        this.mGroupId = ((GroupSettingActivity) mActivityBinding.getRoot().getContext()).getIntent()
                .getIntExtra(Constant.EXTRA_GROUP_ID, -1);

        mActivityBinding.setVariable(BR.title, OperationUtil.getString(mActivityBinding, R.string.string_group_operation));
    }

    /**
     * 公告系统
     */
    public void doClickToAnnouncement() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), AnnouncementActivity.class);
        //传递群id
        intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
        //传递用户身份信息
        intent.putExtra(Constant.EXTRA_USER_CAPITAL, ((GroupSettingActivity) mActivityBinding.getRoot().getContext()).getIntent().getIntExtra(Constant.EXTRA_USER_CAPITAL, -1));
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * 文件系统
     */
    public void doClickToFile() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), FileActivity.class);
        intent.putExtra(Constant.EXTRA_GROUP_ID, mGroupId);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * 邀请用户
     */
    public void doClickToInvite() {
        //邀请用户的弹出框，填入用户id
        InvitePersonDialog dialog = new InvitePersonDialog(mActivityBinding.getRoot().getContext(),
                this, Constant.TYPE_INVITE_TITLE_AND_HINT_USER);
        dialog.show();
    }

    /**
     * 退出群
     */
    public void doClickToExitGroup() {
        CustomerAlertDialog dialog = new CustomerAlertDialog(mActivityBinding.getRoot().getContext(),
                this,
                OperationUtil.getString(mActivityBinding, R.string.string_exit_group),
                OperationUtil.getString(mActivityBinding, R.string.string_exit_by_owner),
                OperationUtil.getString(mActivityBinding, R.string.string_exit),
                OperationUtil.getString(mActivityBinding, R.string.string_cancel)
        );
        dialog.show();
    }

    @Override
    public void initListener() {
        //返回按钮的点击事件
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GroupSettingActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    /**
     * 退出群的回调事件
     */
    @Override
    public void doClickAlertDialogToOk() {
        //退出
        GroupOperationReq req = new GroupOperationReq();
        req.setGroupid(mGroupId);
        req.setUserId1(SharedPrefUserInfoUtil.getUserId());
        req.setType(Constant.TYPE_GROUP_OPERATION_EXIT_BY_MYSELF);
        //发送退出群消息
        SendMessageUtil.sendMessage(req);

        //跳转到MainActivity界面去
        Intent intent = new Intent();
        intent.setClass(mActivityBinding.getRoot().getContext(), MainActivity.class);
        //清除MainActivity之上的Activity，将MainActivity提到最上面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    @Override
    public void onClickToInvitePerson(int id) {
        GroupOperationReq req = new GroupOperationReq();
        req.setGroupid(mGroupId);
        //需要请求的用户
        req.setUserId1(id);
        //邀请者
        req.setUserId2(SharedPrefUserInfoUtil.getUserId());
        //数据类型
        req.setType(Constant.TYPE_GROUP_OPERATION_ADD_BY_INVITE);
        SendMessageUtil.sendMessage(req);
    }
}
