package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.widget.Toast;

import edu.csuft.chentao.ui.activity.CreateGroupActivity;
import edu.csuft.chentao.ui.activity.SearchGroupActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityMainBinding;
import edu.csuft.chentao.pojo.req.GroupOperationReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.ui.view.InvitePersonDialog;

/**
 * 菜单栏的Presenter,负责加入群，搜索群和创建群操作
 */
public class ItemGroupOperationPresenter implements InvitePersonDialog.IDialogClickListener {

    private ActivityMainBinding mActivityBinding;

    public ItemGroupOperationPresenter(ActivityMainBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    /**
     * 加入群
     */
    public void onClickToAddGroup() {
        InvitePersonDialog dialog = new InvitePersonDialog(mActivityBinding.getRoot().getContext(), this, Constant.TYPE_INVITE_TITLE_AND_HINT_GROUP);
        dialog.show();
    }

    /**
     * 搜索群
     */
    public void onClickToSearchGroup() {
        Intent intent = new Intent(MyApplication.getInstance(), SearchGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    /**
     * 创建群
     */
    public void onClickToCreateGroup() {
        Intent intent = new Intent(MyApplication.getInstance(), CreateGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    @Override
    public void onClickToInvitePerson(int groupId) {
        //主动加入群
        GroupOperationReq req = new GroupOperationReq();
        req.setGroupid(groupId);
        req.setUserId1(SharedPrefUserInfoUtil.getUserId());
        req.setType(Constant.TYPE_GROUP_OPERATION_ADD_BY_MYSELF);
        SendMessageUtil.sendMessage(req);
    }
}