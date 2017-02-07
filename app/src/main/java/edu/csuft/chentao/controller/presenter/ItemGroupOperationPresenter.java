package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.widget.Toast;

import edu.csuft.chentao.activity.CreateGroupActivity;
import edu.csuft.chentao.activity.SearchGroupActivity;
import edu.csuft.chentao.base.MyApplication;

/**
 * 菜单栏的Presenter,负责加入群，搜索群和创建群操作
 */
public class ItemGroupOperationPresenter {

    /**
     * 加入群
     */
    public void onClickToAddGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToAddGroup", Toast.LENGTH_SHORT).show();
    }

    /**
     * 搜索群
     */
    public void onClickToSearchGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToSearchGroup", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyApplication.getInstance(), SearchGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }

    /**
     * 创建群
     */
    public void onClickToCreateGroup() {
        Toast.makeText(MyApplication.getInstance(), "onClickToCreateGroup", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyApplication.getInstance(), CreateGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getInstance().startActivity(intent);
    }
}