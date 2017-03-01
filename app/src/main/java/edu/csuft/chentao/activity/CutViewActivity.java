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
import edu.csuft.chentao.controller.presenter.ActivityCutViewPresenter;
import edu.csuft.chentao.databinding.ActivityCutViewBinding;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;

public class CutViewActivity extends BaseActivity {

    private ActivityCutViewBinding mActivityBinding;
    private ActivityCutViewPresenter mActivityPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_cut_view;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityCutViewBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        mActivityPresenter = new ActivityCutViewPresenter(mActivityBinding, getIntent().getStringExtra(Constant.EXTRA_CUT_VIEW));
        mActivityBinding.setVariable(BR.presenter, mActivityPresenter);

        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(Constant.IMAGE_TYPE);
        ((CutViewActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, Constant.IMAGE_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityPresenter.unregisterEventBus();
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
                ImageDetail imageDetail = new ImageDetail(Constant.IMAGE_ACTIVITY_CUT_VIEW_PRESENTER, buf);
                EventBus.getDefault().post(imageDetail);

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
