package edu.csuft.chentao.controller.presenter;

import android.widget.Toast;

import edu.csuft.chentao.base.MyApplication;

/**
 * Created by Chalmers on 2017-02-11 17:57.
 * email:qxinhai@yeah.net
 */

public class ItemGroupDetailPopupPresenter {

    public void onClickToInvite() {
        Toast.makeText(MyApplication.getInstance(), "邀请", Toast.LENGTH_SHORT).show();
    }

    public void onClickToExit() {
        Toast.makeText(MyApplication.getInstance(), "退出", Toast.LENGTH_SHORT).show();
    }
}