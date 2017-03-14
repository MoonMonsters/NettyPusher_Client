package edu.csuft.chentao.ui.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentChattingListPresenter;
import edu.csuft.chentao.databinding.FragmentChattingListBinding;

public class ChattingListFragment extends BaseFragment {

    private FragmentChattingListBinding mFragmentBinding = null;
    private FragmentChattingListPresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_chatting_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentChattingListBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mPresenter = new FragmentChattingListPresenter(mFragmentBinding);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unregisterEventBus();
    }

}