package edu.csuft.chentao.base;

import android.databinding.ViewDataBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;

/**
 * Created by Chalmers on 2017-02-26 11:50.
 * email:qxinhai@yeah.net
 */

/**
 * Presenter类的基类
 */
public abstract class BasePresenter {

    protected BasePresenter() {
    }

    protected BasePresenter(ViewDataBinding viewDataBinding) {
    }

    protected BasePresenter(ViewDataBinding viewDataBinding, Object object1) {
    }

    protected BasePresenter(ViewDataBinding viewDataBinding, Object object1, Object object2) {
    }

    /**
     * 初始化必须执行的方法
     */
    protected void init() {
        registerEventBus();
        initData();
        initListener();
    }

    /**
     * 初始化操作
     */
    protected abstract void initData();

    /**
     * 每个类都需要实现该方法，用来接收EventBus传递过来的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {

    }

    /**
     * 获得从Activity发送过来的图片数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail imageDetail) {

    }

    /**
     * 可能需要用上的设置监听器方法
     */
    public void initListener() {

    }

    /**
     * 注册EventBus
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 注销EventBus
     */
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
