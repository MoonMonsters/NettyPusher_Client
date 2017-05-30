package edu.csuft.chentao.controller.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityGroupSettingBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.ui.activity.AnnouncementActivity;
import edu.csuft.chentao.ui.activity.FileActivity;
import edu.csuft.chentao.ui.activity.GroupSettingActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.ui.view.CustomerAlertDialog;
import edu.csuft.chentao.ui.view.InvitePersonDialog;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
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

    private ProgressDialog mSyncMessageDialog;

    /**
     * 同步所有聊天数据
     */
    public void doClickToSyncGroupMessage() {
        //如果是已经开始同步消息，但隐藏了对话框，则点击后重新显示对话框
        if (mSyncMessageDialog != null && !mSyncMessageDialog.isShowing()) {
            mSyncMessageDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivityBinding.getRoot().getContext());
            builder.setTitle("提示")
                    .setMessage("是否需要同步所有聊天信息")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendStartSyncMessageRequest();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
    }

    /**
     * 开始同步后，显示对话框
     */
    private void showSyncMessageDialog() {
        mSyncMessageDialog = new ProgressDialog(mActivityBinding.getRoot().getContext());
        mSyncMessageDialog.setMessage("正在同步中...");
        mSyncMessageDialog.setCancelable(false);
        mSyncMessageDialog.setCanceledOnTouchOutside(false);
        mSyncMessageDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "隐藏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissSyncMessageDialog();
            }
        });
        mSyncMessageDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "停止同步", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendStopSyncMessageRequest();
            }
        });
        mSyncMessageDialog.show();
    }

    /**
     * 隐藏对话框
     */
    private void dismissSyncMessageDialog() {
        if (mSyncMessageDialog != null && mSyncMessageDialog.isShowing()) {
            mSyncMessageDialog.dismiss();
        }
    }

    /**
     * 发送消息，开始同步
     */
    private void sendStartSyncMessageRequest() {
        /*
         发送同步数据消息
         */
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_START_SYNC_GROUP_MESSAGE);
        req.setArg1(mGroupId);
        SendMessageUtil.sendMessage(req);

        //需要到主线程中打开对话框
        ((GroupSettingActivity) mActivityBinding.getRoot().getContext())
                .runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showSyncMessageDialog();
                    }
                });
    }

    /**
     * 发送消息，停止同步
     */
    private void sendStopSyncMessageRequest() {
        /*
         发送同步数据消息
         */
        GetInfoReq req = new GetInfoReq();
        req.setType(Constant.TYPE_GET_INFO_STOP_SYNC_GROUP_MESSAGE);
        req.setArg1(mGroupId);
        SendMessageUtil.sendMessage(req);

        dismissSyncMessageDialog();
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

    @Override
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_GROUP_SETTING_PRESENTER_SYNC_COMPLETE)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            int groupId = resp.getArg1();
            //如果聊天记录同步完成群是当前打开的群，则关闭界面，并且提示
            if (groupId == mGroupId) {
                //关闭对话框
                dismissSyncMessageDialog();
                LoggerUtil.showToast(mActivityBinding.getRoot().getContext(), "聊天记录同步完成");
            }
        }
    }
}