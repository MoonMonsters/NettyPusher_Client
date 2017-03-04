package edu.csuft.chentao.controller.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.CutViewActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityCutViewBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.CutListener;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.view.CutView;

/**
 * Created by Chalmers on 2017-02-28 20:44.
 * email:qxinhai@yeah.net
 */

public class ActivityCutViewPresenter extends BasePresenter implements CutListener {

    private ActivityCutViewBinding mActivityBinding;
    private String mTag;
    private static final int REQUEST_CODE = 0;

    private ImageDetail mImageDetail;

    /**
     * 图片裁剪类型
     */
    private int mType = -1;

    /**
     * 裁剪圆形头像
     */
    private final int TYPE_SHAPE_OVAL = 2;
    /**
     * 不处理
     */
    private final int TYPE_SHAPE_NULL = 1;
    /**
     * 裁剪正方形图形
     */
    private final int TYPE_SHAPE_RECT = 0;

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
        pickPicture();
    }

    /**
     * 打开选择图片界面
     */
    private void pickPicture() {
        //配置
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
                .backResId(R.drawable.ic_back)
                //标题
                .title("Images")
                //标题颜色
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                //标题显示
                .allImagesText("All Images")
                //不需要裁剪
                .needCrop(false)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        //启动选择界面
        ImgSelActivity.startActivity((CutViewActivity) mActivityBinding.getRoot().getContext(), config, REQUEST_CODE);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.IMAGE_ACTIVITY_CUT_VIEW_PRESENTER)) {

            this.mImageDetail = imageDetail;
            if (mTag.equals(Constant.CUT_VIEW_REGISTER_ACTIVITY)
                    || mTag.equals(Constant.CUT_VIEW_CREATE_GROUP_ACTIVITY)
                    || mTag.equals(Constant.CUT_VIEW_EDITOR_INFO_PRESENTER)) {

                mType = TYPE_SHAPE_OVAL;
                //设置默认选中第一个RadioButton
                mActivityBinding.setVariable(BR.type, 1);

                //如果是这些类型，那么RadioGroup则不可点击
                setRadioButtonNotClickable(mActivityBinding.rgCutViewChoose);
                cutImageByType(TYPE_SHAPE_OVAL);
            } else {
                //设置默认选中第二个
                mActivityBinding.setVariable(BR.type, 2);
                mType = TYPE_SHAPE_RECT;

                cutImageByType(TYPE_SHAPE_RECT);
            }
        }
    }

    /**
     * 点击确认剪切
     */
    public void onClickToCutImage() {

        //如果不需要裁剪图片，则直接返回该图片数据
        if (mType == TYPE_SHAPE_NULL) {
            OperationUtil.sendImageDetail(mTag, mImageDetail.getImage());

            //关闭界面
            finishActivity();
            //否则，调用裁剪方法进行裁剪
        } else {
            mActivityBinding.cvCutView.clipImage();
        }
    }

    /**
     * 点击出现选择图片形状的RadioGroup
     */
    public void onClickToChooseShape() {

        //如果是VISIBLE状态，那么就GONE
        mActivityBinding.rgCutViewChoose.setVisibility(
                mActivityBinding.rgCutViewChoose.getVisibility() == View.VISIBLE
                        ? View.GONE
                        : View.VISIBLE);
    }

    /**
     * 根据传入的类型的值，来裁剪不同类型的形状
     *
     * @param type 裁剪图片的值
     */
    private void cutImageByType(int type) {

        mActivityBinding.cvCutView.setImageBitmap(null);

        Bitmap bitmap = BitmapFactory.decodeByteArray(mImageDetail.getImage(), 0, mImageDetail.getImage().length);
        mActivityBinding.cvCutView.setImageBitmap(bitmap);
        mActivityBinding.cvCutView.setCutListener(this);//设置剪裁监听，用于剪裁完成后获取圆形头像

        if (type != TYPE_SHAPE_NULL) {
            //自定义CutView的一些属性
            mActivityBinding.cvCutView.setShadeColor(0X6674d6b5);//色值要使用argb，带透明度的
            mActivityBinding.cvCutView.setPathColor(0Xfff9771e);//argb
            mActivityBinding.cvCutView.setCutRadius(150);//这里的单位是px
            mActivityBinding.cvCutView.setPathEffect(new CornerPathEffect(3.0f));//将默认的圆形虚线线条改为实线
            mActivityBinding.cvCutView.setPathWidth(2);//设置线条宽度，默认是2，单位是px
            mActivityBinding.cvCutView.setCutFillColor(0xffffffff);//图片剪切空白部分颜色设置为白色，argb
            //只有当type的值是2的时候，才是裁剪图形图片
            mActivityBinding.cvCutView.setPathType(type == 2 ? CutView.PathType.OVAL : CutView.PathType.RECT);
            mActivityBinding.cvCutView.setRoundRectRadius(10.0f);//如果剪切类型为圆角矩形，需要设置圆角矩形的圆角半径，可不设置默认是3.0f
        } else {
            mActivityBinding.cvCutView.setPathColor(Color.TRANSPARENT);//argb
            mActivityBinding.cvCutView.setShadeColor(Color.TRANSPARENT);//色值要使用argb，带透明度的
            mActivityBinding.cvCutView.setCutRadius(0);//这里的单位是px
            //自定义CutView的一些属性
            mActivityBinding.cvCutView.setPathWidth(0);//设置线条宽度，默认是2，单位是px
        }
    }

    @Override
    public void cutResultWithBitmap(Bitmap bitmap) {
        LoggerUtil.logger(Constant.TAG, "cutResultWithBitmap-----" + mTag);

        byte[] buf = OperationUtil.bitmapToBytes(bitmap);
        OperationUtil.sendImageDetail(mTag, buf);

        finishActivity();
    }

    /**
     * 关闭当前界面
     */
    private void finishActivity() {
        //关闭界面
        ((CutViewActivity) mActivityBinding.getRoot().getContext()).finish();
    }

    /**
     * 设置RadioGroup不可点击
     *
     * @param group RadioGroup对象
     */
    private void setRadioButtonNotClickable(RadioGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setClickable(false);
            group.getChildAt(i).setEnabled(false);
        }
    }

    /**
     * 选中圆形
     */
    public void onClickToChooseOVAL() {
        //保存当前图片类型
        mType = TYPE_SHAPE_OVAL;
        //裁剪图片
        cutImageByType(mType);

        hideRadioGroup();
    }

    /**
     * 选中矩形
     */
    public void onClickToChooseRECT() {
        //保存当前图片类型
        mType = TYPE_SHAPE_RECT;
        //裁剪图片
        cutImageByType(mType);

        hideRadioGroup();
    }

    /**
     * 不处理
     */
    public void onClickToChooseNULL() {
        //保存当前图片类型
        mType = TYPE_SHAPE_NULL;
        //裁剪图片
        cutImageByType(mType);

        hideRadioGroup();
    }

    /**
     * 隐藏RadioGroup
     */
    private void hideRadioGroup() {
        //设置成不可见状态
        mActivityBinding.rgCutViewChoose.setVisibility(View.GONE);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void getEBToObjectPresenter(EBToPreObject ebObj) {

    }
}
