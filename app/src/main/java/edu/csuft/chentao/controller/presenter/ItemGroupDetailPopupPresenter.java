package edu.csuft.chentao.controller.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.PopupWindow;
import android.widget.Toast;

import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.ui.view.InvitePersonDialog;

/**
 * Created by Chalmers on 2017-02-11 17:57.
 * email:qxinhai@yeah.net
 */

public class ItemGroupDetailPopupPresenter implements InvitePersonDialog.IDialogClickListener {

    private ActivityGroupDetailBinding mActivityBinding;
    private PopupWindow mPopupWindow;
    /**
     * 当前界面的群id
     */
    private int mGroupId;

    public ItemGroupDetailPopupPresenter(ActivityGroupDetailBinding activityBinding, PopupWindow popupWindow) {
        this.mActivityBinding = activityBinding;
        this.mPopupWindow = popupWindow;
    }

    /**
     * 设置操作的群id
     */
    public void setGroupId(int groupId) {
        this.mGroupId = groupId;
    }

    /**
     * 邀请其他用户参加
     */
    public void onClickToInvite() {
        Toast.makeText(MyApplication.getInstance(), "邀请2222222", Toast.LENGTH_SHORT).show();
        mPopupWindow.dismiss();

        InvitePersonDialog dialog = new InvitePersonDialog(mActivityBinding.getRoot().getContext(), this, Constant.TYPE_INVITE_TITLE_AND_HINT_USER);
        dialog.show();
    }

    /**
     * 自己退出群
     */
    public void onClickToExit() {
        Toast.makeText(MyApplication.getInstance(), "退出", Toast.LENGTH_SHORT).show();
        mPopupWindow.dismiss();

        /*
        弹出对话框提示
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivityBinding.getRoot().getContext());
        builder.setTitle("退出聊天群")
                .setMessage("是否退出该群？如果你是群主，将会解散该群！")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onClickToInvitePerson(int userId) {
        GroupOperationReq req = new GroupOperationReq();
        req.setGroupid(mGroupId);
        //需要请求的用户
        req.setUserId1(userId);
        //邀请者
        req.setUserId2(SharedPrefUserInfoUtil.getUserId());
        //数据类型
        req.setType(Constant.TYPE_GROUP_OPERATION_ADD_BY_INVITE);
        SendMessageUtil.sendMessage(req);
    }
}