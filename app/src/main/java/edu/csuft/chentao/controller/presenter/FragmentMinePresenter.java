package edu.csuft.chentao.controller.presenter;

import android.content.Intent;

import edu.csuft.chentao.activity.EditorInfoActivity;
import edu.csuft.chentao.activity.LoginActivity;
import edu.csuft.chentao.activity.MainActivity;
import edu.csuft.chentao.databinding.FragmentMineBinding;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;
import edu.csuft.chentao.utils.daoutil.HintDaoUtil;

/**
 * Created by Chalmers on 2016-12-29 19:05.
 * email:qxinhai@yeah.net
 */

public class FragmentMinePresenter {

    private FragmentMineBinding mFragmentBinding = null;

    public FragmentMinePresenter(FragmentMineBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
    }

    /**
     * 退出登录，注销
     */
    public void onClickToExitLogining() {
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

    /**
     * 编辑用户信息
     */
    public void onClickToEditorInfo() {
        mFragmentBinding.getRoot().getContext()
                .startActivity(new Intent(mFragmentBinding.getRoot().getContext(), EditorInfoActivity.class));
    }
}