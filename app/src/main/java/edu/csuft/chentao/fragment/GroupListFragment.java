package edu.csuft.chentao.fragment;


import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentGroupListPresenter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * @author cusft.chentao
 */
public class GroupListFragment extends BaseFragment {

    private FragmentGroupListBinding mFragmentBinding = null;

    private BroadcastReceiver mReceiver = null;

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
        FragmentGroupListPresenter presenter = new FragmentGroupListPresenter(mFragmentBinding);
        presenter.init();
        mFragmentBinding.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mReceiver = new FragmentGroupListPresenter.GroupListReceiver(this.getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_GROUPS);
        this.getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getActivity().unregisterReceiver(mReceiver);
    }
}