package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentMinePresenter;
import edu.csuft.chentao.dao.UserHeadDao;
import edu.csuft.chentao.dao.UserInfoDao;
import edu.csuft.chentao.databinding.FragmentMineBinding;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

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

        UserInfo userInfo = DaoSessionUtil.getUserInfoDao()
                .queryBuilder().where(UserInfoDao.Properties.Userid.eq(SharedPrefUserInfoUtil.getUserId()))
                .build().list().get(0);

        UserHead userHead = DaoSessionUtil.getUserHeadDao()
                .queryBuilder().where(UserHeadDao.Properties.Userid.eq(SharedPrefUserInfoUtil.getUserId()))
                .build().list().get(0);
        mFragmentBinding.setUserInfo(userInfo);
        mFragmentBinding.setUserHead(userHead);
    }
}