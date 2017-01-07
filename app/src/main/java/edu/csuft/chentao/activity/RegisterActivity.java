package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.utils.Constant;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mActivityBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;

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
        //在设置Presenter之前注册EventBus
        EventBus.getDefault().register(this);
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
        mReceiver = new RegisterReceiver();
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("RegisterActivity")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    /**
     * 注册广播
     */
    public class RegisterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Constant.ACTION_REGISTER)) {
                RegisterResp resp = (RegisterResp) intent.getSerializableExtra(Constant.EXTRA_REGISTERRESP);

                //弹出提示
                Toast.makeText(RegisterActivity.this, resp.getDescription(), Toast.LENGTH_SHORT).show();

                if (resp.getUserid() > 0) {
                    //发送消息
                    Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_REGISTER;
                    msg.obj = resp;
                    mHandler.sendMessage(msg);

                    //进入主界面
                    RegisterActivity.this.startActivity(new Intent((RegisterActivity) mActivityBinding.getRoot().getContext(),
                            MainActivity.class));
                    //关闭注册界面
                    RegisterActivity.this.finish();
                }

            }
        }
    }
}
