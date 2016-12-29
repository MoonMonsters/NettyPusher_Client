package edu.csuft.chentao.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.utils.Constant;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mActivityBinding = null;

    private ActivityRegisterPresenter.RegisterReceiver mReceiver = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityRegisterBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        this.mActivityBinding.setPresenter(new ActivityRegisterPresenter(mActivityBinding));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == ActivityRegisterPresenter.IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);//显得到bitmap图片
                mActivityBinding.ivRegisterHead.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new ActivityRegisterPresenter.RegisterReceiver();
        IntentFilter filter = new IntentFilter();
        //添加广播
        filter.addAction(Constant.ACTION_REGISTER);
        //注册广播
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销广播
        unregisterReceiver(mReceiver);
    }
}
