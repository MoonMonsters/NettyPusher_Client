package edu.csuft.chentao.controller.presenter;

import android.content.Intent;

import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.EditorInfoActivity;
import edu.csuft.chentao.ui.activity.LoginActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.FragmentMineBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.ui.view.CustomerAlertDialog;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 19:05.
 * email:qxinhai@yeah.net
 */

public class FragmentMinePresenter extends BasePresenter implements CustomerAlertDialog.IAlertDialogClickListener {

    private FragmentMineBinding mFragmentBinding = null;

    public FragmentMinePresenter(FragmentMineBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
        init();
    }

    /**
     * 退出登录，注销
     */
    public void onClickToExitLogining() {
        CustomerAlertDialog dialog = new CustomerAlertDialog(mFragmentBinding.getRoot().getContext(),
                this,
                OperationUtil.getString(mFragmentBinding, R.string.string_exit_login),
                OperationUtil.getString(mFragmentBinding, R.string.string_exit_login_detail_message),
                OperationUtil.getString(mFragmentBinding, R.string.string_exit),
                OperationUtil.getString(mFragmentBinding, R.string.string_cancel));

        dialog.show();
    }

    /**
     * 编辑用户信息
     */
    public void onClickToEditorInfo() {
        mFragmentBinding.getRoot().getContext()
                .startActivity(new Intent(mFragmentBinding.getRoot().getContext(), EditorInfoActivity.class));
    }

    @Override
    protected void initData() {
        //绑定数据
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
        UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());

        mFragmentBinding.setUserInfo(userInfo);
        mFragmentBinding.setUserHead(userHead);
    }

    @Override
    public void doClickAlertDialogToOk() {
        //清空保存的用户信息
        SharedPrefUserInfoUtil.clearUserInfo();
        //删除所有的ChattingMessage数据
        ChattingMessageDaoUtil.deleteAll();
        //删除所有的GroupChattingItem数据
        GroupChattingItemDaoUtil.deleteAll();
        //删除所有的Groups数据
        GroupsDaoUtil.deleteAll();
        //删除所有消息数据
        HintDaoUtil.deleteAll();
        //退出登录
        SendMessageUtil.sendUnLoginReq();

        (mFragmentBinding.getRoot().getContext()).startActivity(new Intent(
                (mFragmentBinding.getRoot().getContext()),
                LoginActivity.class));
        //关闭界面
        ((MainActivity) mFragmentBinding.getRoot().getContext()).finish();
    }
}