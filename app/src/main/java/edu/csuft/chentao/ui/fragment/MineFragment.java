package edu.csuft.chentao.ui.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentMinePresenter;
import edu.csuft.chentao.databinding.FragmentMineBinding;

public class MineFragment extends BaseFragment {

    private FragmentMineBinding mFragmentBinding = null;
    private FragmentMinePresenter mPresenter;

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
        mPresenter = new FragmentMinePresenter(mFragmentBinding);
        mFragmentBinding.setPresenter(mPresenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterEventBus();
    }
}