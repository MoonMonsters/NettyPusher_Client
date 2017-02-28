package edu.csuft.chentao.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityRegisterPresenter;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;

/**
 * @author csuft.chentao
 *         注册界面
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding mActivityBinding = null;

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
        if (requestCode == Constant.IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);//显得到bitmap图片
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] buf = baos.toByteArray();

                /**
                 * 把图片数据发送Presenter
                 */
                ImageDetail imageDetail = new ImageDetail(Constant.IMAGE_ACTIVITY_REGISTER_PRESENTER, buf);
                EventBus.getDefault().post(imageDetail);

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
