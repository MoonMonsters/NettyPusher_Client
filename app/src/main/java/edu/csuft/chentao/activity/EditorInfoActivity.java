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

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityEditorInfoPresenter;
import edu.csuft.chentao.databinding.ActivityEditorInfoBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

public class EditorInfoActivity extends BaseActivity {

    private ActivityEditorInfoBinding mActivityBinding = null;
    private Handler mHandler = null;
    private BroadcastReceiver mReceiver = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_editor_info;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityEditorInfoBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        //注册
        EventBus.getDefault().register(this);
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
        UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
        //设置属性
        mActivityBinding.setUserHead(userHead);
        mActivityBinding.setUserInfo(userInfo);

        ActivityEditorInfoPresenter presenter =
                new ActivityEditorInfoPresenter(mActivityBinding);
        mActivityBinding.setPresenter(presenter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage message) {
        if (message.getTag().equals("EditorInfoActivity")) {
            mHandler = message.getHandler();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new EditorInfoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_RETURN_MESSAGE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销
        unregisterReceiver(mReceiver);
        EventBus.getDefault().unregister(this);
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

                //发送数据到Presenter
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_RETURN_MESSAGE_IMAGE;
                msg.obj = buf;
                mHandler.sendMessage(msg);

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 广播
     */
    private class EditorInfoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LoggerUtil.logger(Constant.TAG, action);
            if (action.equals(Constant.ACTION_RETURN_MESSAGE)) {
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_RETURN_MESSAGE;
                msg.obj = intent.getSerializableExtra(Constant.EXTRA_RETURN_MESSAGE);
                mHandler.sendMessage(msg);
            }
        }
    }
}