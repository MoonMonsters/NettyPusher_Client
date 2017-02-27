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

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityEditorInfoPresenter;
import edu.csuft.chentao.databinding.ActivityEditorInfoBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

public class EditorInfoActivity extends BaseActivity {

    private ActivityEditorInfoBinding mActivityBinding = null;
    private ActivityEditorInfoPresenter mPresenter;

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

        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
        UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
        //设置属性
        mActivityBinding.setUserHead(userHead);
        mActivityBinding.setUserInfo(userInfo);

        mPresenter =
                new ActivityEditorInfoPresenter(mActivityBinding);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        mPresenter.unregisterEventBus();
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

                /*
                把图片数据发送到Presenter
                 */
                ImageDetail imageDetail = new ImageDetail(Constant.IMAGE_ACTIVITY_EDITOR_INFO_PRESENTER, buf);
                EventBus.getDefault().post(imageDetail);

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}