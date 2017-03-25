package edu.csuft.chentao.controller.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.PopupWindow;
import android.widget.Toast;

import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityGroupDetailBinding;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.ui.view.CustomerAlertDialog;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.ui.view.InvitePersonDialog;

/**
 * Created by Chalmers on 2017-02-11 17:57.
 * email:qxinhai@yeah.net
 */

public class ItemGroupDetailPopupPresenter implements InvitePersonDialog.IDialogClickListener, CustomerAlertDialog.IAlertDialogClickListener {

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
        mPopupWindow.dismiss();

        InvitePersonDialog dialog = new InvitePersonDialog(mActivityBinding.getRoot().getContext(), this, Constant.TYPE_INVITE_TITLE_AND_HINT_USER);
        dialog.show();
    }

    /**
     * 自己退出群
     */
    public void onClickToExit() {
        mPopupWindow.dismiss();

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
}