package edu.csuft.chentao.base;

import android.databinding.ViewDataBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;

/**
 * Created by Chalmers on 2017-02-26 11:50.
 * email:qxinhai@yeah.net
 */

/**
 * Presenter类的基类
 */
public abstract class BasePresenter {

    /**
     * View的DataBinding对象
     */
    private ViewDataBinding mViewDataBinding;
    /**
     * 传递的参数1
     */
    private Object mObject1;
    /**
     * 传递参数2
     */
    private Object mObject2;


    public BasePresenter(ViewDataBinding viewDataBinding) {
        this(viewDataBinding, null);
        LoggerUtil.logger(Constant.TAG, "BasePresenter1");
    }

    public BasePresenter(ViewDataBinding viewDataBinding, Object object1) {
        this(viewDataBinding, object1, null);
        LoggerUtil.logger(Constant.TAG, "BasePresenter2");
    }

    public BasePresenter(ViewDataBinding viewDataBinding, Object object1, Object object2) {
        LoggerUtil.logger(Constant.TAG, "1-->"+viewDataBinding.toString());
        setViewDataBinding(viewDataBinding);
        LoggerUtil.logger(Constant.TAG, "setViewDataBinding");
        setObject1(object1);
        setObject2(object2);

        registerEventBus();
        init();
        LoggerUtil.logger(Constant.TAG, "init");
        setListener();
    }

    /**
     * 初始化操作
     */
    public abstract void init();

    /**
     * 每个类都需要实现该方法，用来接收EventBus传递过来的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void getEBToObjectPresenter(EBToPreObject ebObj);

    /**
     * 可能需要用上的设置监听器方法
     */
    private void setListener() {

    }

    /**
     * 注册EventBus
     */
    private void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 注销EventBus
     */
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置ViewDataBinding对象
     */
    public void setViewDataBinding(ViewDataBinding viewDataBinding) {
        this.mViewDataBinding = viewDataBinding;
    }

    /**
     * 设置参数1
     */
    private void setObject1(Object object1) {
        this.mObject1 = object1;
    }

    /**
     * 设置参数2
     */
    private void setObject2(Object object2) {
        this.mObject2 = object2;
    }

    /**
     * 获取ViewDataBinding对象
     */
    public ViewDataBinding getViewDataBinding() {
        LoggerUtil.logger(Constant.TAG, "getViewDataBinding");
        return this.mViewDataBinding;
    }

    /**
     * 获取参数1
     */
    public Object getObject1() {
        return this.mObject1;
    }

    /**
     * 获取参数2
     */
    public Object getObject2() {
        return this.mObject2;
    }
}
