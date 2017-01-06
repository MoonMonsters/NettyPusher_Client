package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.widget.Toast;

import edu.csuft.chentao.activity.LoginActivity;
import edu.csuft.chentao.activity.MainActivity;
import edu.csuft.chentao.databinding.FragmentMineBinding;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2016-12-29 19:05.
 * email:qxinhai@yeah.net
 */

public class FragmentMinePresenter {

    private FragmentMineBinding mFragmentBinding = null;

    public FragmentMinePresenter(FragmentMineBinding fragmentBinding) {
        this.mFragmentBinding = fragmentBinding;
    }

    public void onClickToExitLogining() {
        SharedPrefUserInfoUtil.clearUserInfo();
        DaoSessionUtil.getChattingMessageDao().deleteAll();
        DaoSessionUtil.getGroupChattingItemDao().deleteAll();
        DaoSessionUtil.getGroupsDao().deleteAll();
        (mFragmentBinding.getRoot().getContext()).startActivity(new Intent(
                (mFragmentBinding.getRoot().getContext()),
                LoginActivity.class));
        ((MainActivity) mFragmentBinding.getRoot().getContext()).finish();
    }

    /**
     * 编辑用户信息
     */
    public void onClickToEditorInfo() {
        Toast.makeText(mFragmentBinding.getRoot().getContext(), "修改资料", Toast.LENGTH_SHORT).show();
    }
}
