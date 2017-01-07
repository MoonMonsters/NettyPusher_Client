package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentMinePresenter;
import edu.csuft.chentao.databinding.FragmentMineBinding;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

public class MineFragment extends BaseFragment {

    private FragmentMineBinding mFragmentBinding = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentMineBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        FragmentMinePresenter presenter = new FragmentMinePresenter(mFragmentBinding);
        mFragmentBinding.setPresenter(presenter);

        //绑定数据
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
        UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());

        mFragmentBinding.setUserInfo(userInfo);
        mFragmentBinding.setUserHead(userHead);
    }
}