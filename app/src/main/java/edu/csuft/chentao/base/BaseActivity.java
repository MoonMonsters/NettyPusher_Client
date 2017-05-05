package edu.csuft.chentao.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.ui.activity.MainActivity;

/**
 * Created by Chalmers on 2016-12-16 13:44.
 * email:qxinhai@yeah.net
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ViewDataBinding mViewDataBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId());
        setActivityBinding(mViewDataBinding);
        initData();

        if (!(this instanceof MainActivity) && !(this instanceof ImageActivity)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
     * 得到ViewDataBinding
     *
     * @return ViewDataBinding对象
     */
    public ViewDataBinding getActivityBinding() {

        return mViewDataBinding;
    }

    /**
     * 初始化数据
     */
    public abstract void initData();
}
