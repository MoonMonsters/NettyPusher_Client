package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.databinding.FragmentMineBinding;

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
        this.mFragmentBinding.ivMineShow.setImageResource(R.drawable.splash);
    }

    @Override
    public void initListener() {

    }
}
