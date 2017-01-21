package edu.csuft.chentao.controller.presenter;

import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;

/**
 * 菜单栏的Presenter,负责加入群，搜索群和创建群操作
 */
public class ItemGroupOperationPresenter {

    public void onClickToAddGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToAddGroup", Toast.LENGTH_SHORT).show();
    }

    public void onClickToSearchGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToSearchGroup", Toast.LENGTH_SHORT).show();
    }

    public void onClickToCreateGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToCreateGroup", Toast.LENGTH_SHORT).show();
    }
}