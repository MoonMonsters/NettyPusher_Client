package edu.csuft.chentao.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chalmers on 2016-12-22 07:47.
 * email:qxinhai@yeah.net
 */

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);
        setDataBinding(viewDataBinding);
        setHasOptionsMenu(true);
        initData();

        return view;
    }

    public abstract int getLayoutResourceId();

    /**
     * 设置ViewDataBinding对象
     */
    public abstract void setDataBinding(ViewDataBinding viewDataBinding);

    /**
     * 初始化数据
     */
    public abstract void initData();
}
