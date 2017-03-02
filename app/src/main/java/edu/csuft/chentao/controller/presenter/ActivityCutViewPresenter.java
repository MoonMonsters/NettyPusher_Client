package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.CutViewActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityCutViewBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CutListener;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.view.CutView;

/**
 * Created by Chalmers on 2017-02-28 20:44.
 * email:qxinhai@yeah.net
 */

public class ActivityCutViewPresenter extends BasePresenter implements CutListener {

    private ActivityCutViewBinding mActivityBinding;
    private String mTag;
    private static final int REQUEST_CODE = 0;

    public ActivityCutViewPresenter(ActivityCutViewBinding activityBinding, Object object) {
        this.mActivityBinding = activityBinding;
        this.mTag = (String) object;

        LoggerUtil.logger(Constant.TAG, mTag);

        init();
    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    @Override
    protected void initData() {
        ImgSelConfig config = new ImgSelConfig.Builder(mActivityBinding.getRoot().getContext(), loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.drawable.ic_add_head)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .needCrop(false)    //不需要裁剪
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();

        ImgSelActivity.startActivity((CutViewActivity) mActivityBinding.getRoot().getContext(), config, REQUEST_CODE);
//        mActivityBinding.cvCutView.setImageBitmap(BitmapFactory.decodeResource(mActivityBinding.getRoot().getContext().getResources(), R.drawable.testview));
//        mActivityBinding.cvCutView.setCutListener(this);//设置剪裁监听，用于剪裁完成后获取圆形头像
//
//        //自定义CutView的一些属性
//        mActivityBinding.cvCutView.setShadeColor(0X6674d6b5);//色值要使用argb，带透明度的
//        mActivityBinding.cvCutView.setPathColor(0Xfff9771e);//argb
//        mActivityBinding.cvCutView.setCutRadius(150);//这里的单位是px
//        mActivityBinding.cvCutView.setPathEffect(new CornerPathEffect(3.0f));//将默认的圆形虚线线条改为实线
//        mActivityBinding.cvCutView.setPathWidth(2);//设置线条宽度，默认是2，单位是px
//        mActivityBinding.cvCutView.setCutFillColor(0xffffffff);//图片剪切空白部分颜色设置为白色，argb
//
//        mActivityBinding.cvCutView.setPathType(CutView.PathType.OVAL);
//
//        mActivityBinding.cvCutView.setRoundRectRadius(10.0f);//如果剪切类型为圆角矩形，需要设置圆角矩形的圆角半径，可不设置默认是3.0f
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void getEBToObjectPresenter(EBToPreObject ebObj) {

    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.IMAGE_ACTIVITY_CUT_VIEW_PRESENTER)) {

            LoggerUtil.logger(Constant.TAG, "收到了图片..." + Constant.IMAGE_ACTIVITY_CUT_VIEW_PRESENTER);

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageDetail.getImage(), 0, imageDetail.getImage().length);
            mActivityBinding.cvCutView.setImageBitmap(bitmap);
            mActivityBinding.cvCutView.setCutListener(this);//设置剪裁监听，用于剪裁完成后获取圆形头像

            //自定义CutView的一些属性
            mActivityBinding.cvCutView.setShadeColor(0X6674d6b5);//色值要使用argb，带透明度的
            mActivityBinding.cvCutView.setPathColor(0Xfff9771e);//argb
            mActivityBinding.cvCutView.setCutRadius(150);//这里的单位是px
            mActivityBinding.cvCutView.setPathEffect(new CornerPathEffect(3.0f));//将默认的圆形虚线线条改为实线
            mActivityBinding.cvCutView.setPathWidth(2);//设置线条宽度，默认是2，单位是px
            mActivityBinding.cvCutView.setCutFillColor(0xffffffff);//图片剪切空白部分颜色设置为白色，argb

            mActivityBinding.cvCutView.setPathType(CutView.PathType.OVAL);

            mActivityBinding.cvCutView.setRoundRectRadius(10.0f);//如果剪切类型为圆角矩形，需要设置圆角矩形的圆角半径，可不设置默认是3.0f
        }
    }

    /**
     * 点击确认剪切
     */
    public void onClickToCutImage() {
        mActivityBinding.cvCutView.clipImage();
        LoggerUtil.logger(Constant.TAG, "裁剪图片");
    }

    /**
     * 点击取消
     */
    public void onClickToCancel() {
        ((CutViewActivity) mActivityBinding.getRoot().getContext()).finish();
    }

    @Override
    public void cutResultWithBitmap(Bitmap bitmap) {
        LoggerUtil.logger(Constant.TAG, "cutResultWithBitmap-----" + mTag);

        byte[] buf = bitmapToBytes(bitmap);
        ImageDetail imageDetail = new ImageDetail(mTag, buf);
        EventBus.getDefault().post(imageDetail);

        ((CutViewActivity) mActivityBinding.getRoot().getContext()).finish();
    }

    /**
     * Bitmap转成byte[]
     */
    private byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
