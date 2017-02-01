package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityCreateGroupPresenter;
import edu.csuft.chentao.databinding.ActivityCreateGroupBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;

public class CreateGroupActivity extends BaseActivity {

    private ActivityCreateGroupBinding mActivityBinding;
    private BroadcastReceiver mReceiver = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_create_group;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        mActivityBinding = (ActivityCreateGroupBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        mActivityBinding.setPresenter(new ActivityCreateGroupPresenter(mActivityBinding));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == Constant.IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);//显得到bitmap图片

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] buf = baos.toByteArray();

                EventBus.getDefault().post(new ImageDetail(buf));

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new CreateGroupReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CREATE_GROUP);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    /**
     * 广播接收器
     */
    private class CreateGroupReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_CREATE_GROUP)) {
                ReturnInfoResp resp = (ReturnInfoResp) intent.getSerializableExtra(Constant.EXTRA_RETURN_INFO);
                //弹出提示框
                Toast.makeText(CreateGroupActivity.this, resp.getDescription(), Toast.LENGTH_SHORT).show();
                //创建成功，关闭当前界面
                if (resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS) {
                    CreateGroupActivity.this.finish();
                }
            }
        }
    }
}
