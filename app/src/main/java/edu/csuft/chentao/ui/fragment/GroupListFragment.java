package edu.csuft.chentao.ui.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentGroupListPresenter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;

/**
 * @author cusft.chentao
 */
public class GroupListFragment extends BaseFragment {

    private FragmentGroupListBinding mFragmentBinding = null;
    private FragmentGroupListPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_group_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentGroupListBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new FragmentGroupListPresenter(mFragmentBinding);
        mFragmentBinding.setPresenter(mPresenter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterEventBus();
    }
}