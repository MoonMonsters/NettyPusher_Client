package edu.csuft.chentao.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Chalmers on 2016-12-16 13:44.
 * email:qxinhai@yeah.net
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityBinding(DataBindingUtil.setContentView(this, getLayoutResourceId()));
        initData();
        initListener();
    }

    /**
     * 返回布局id
     */
    public abstract int getLayoutResourceId();

    /**
     * 设置ViewDataBinding
     */
    public abstract void setActivityBinding(ViewDataBinding viewDataBinding);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 添加监听器
     */
    public abstract void initListener();
}
