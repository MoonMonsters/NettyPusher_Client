package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.activity.CutViewActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * Created by Chalmers on 2016-12-24 15:41.
 * email:qxinhai@yeah.net
 */

public class ActivityRegisterPresenter extends BasePresenter {

    /**
     * DataBinding类型
     */
    private ActivityRegisterBinding mActivityBinding = null;
    /**
     * RegisterReq数据对象
     */
    private RegisterReq req = null;

    public ActivityRegisterPresenter(ActivityRegisterBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //如果是注册消息类型
        if (ebObj.getTag().equals(Constant.TAG_REGISTER_PRESENTER)) {
            RegisterResp resp = (RegisterResp) ebObj.getObject();

            //保存用户id
            SharedPrefUserInfoUtil.setUserId(resp.getUserid());
            //设置登录方式为自动登录
            SharedPrefUserInfoUtil.setLoginType();
            //保存用户名和密码
            SharedPrefUserInfoUtil.setUsernameAndPassword(mActivityBinding.etRegisterUsername.getText().toString(),
                    mActivityBinding.etRegisterPassword.getText().toString());

            //保存用户信息
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(resp.getUserid());
            userInfo.setSignature(req.getSignature());
            userInfo.setNickname(req.getNickname());
            UserInfoDaoUtil.saveUserInfo(userInfo);

            //保存用户头像
            UserHead userHead = new UserHead();
            userHead.setUserid(resp.getUserid());
            userHead.setImage(req.getHeadImage());
            UserHeadDaoUtil.saveUserHead(userHead);
        }
    }

    @Override
    @Subscribe
    protected void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.CUT_VIEW_REGISTER_ACTIVITY)) {

            LoggerUtil.logger(Constant.TAG,"getImageDetail----"+Constant.CUT_VIEW_REGISTER_ACTIVITY);

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageDetail.getImage(), 0, imageDetail.getImage().length);
            mActivityBinding.ivRegisterHead.setImageBitmap(bitmap);
        }
    }

    /**
     * 点击注册
     */
    public void onClickForRegister() {
        //用户名
        String username = mActivityBinding.etRegisterUsername.getText().toString();
        //密码
        String password = mActivityBinding.etRegisterPassword.getText().toString();
        String password2 = mActivityBinding.etRegisterPassword2.getText().toString();
        //昵称
        String nickname = mActivityBinding.etRegisterNickname.getText().toString();
        //签名
        String signature = mActivityBinding.etRegisterSignature.getText().toString();
        //头像
        Drawable drawable = mActivityBinding.ivRegisterHead.getDrawable();
        //如果没有设置类型，那么则使用默认的图片
        if (drawable == null) {
            drawable = mActivityBinding.ivRegisterHead.getBackground();
        }
        //转成byte[]类型
        byte[] buf = bitmapToBytes(drawableToBitmap(drawable));

        //判断密码是否一样或者不为空
        if (password.equals(password2) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(password2)) {
            req = new RegisterReq();
            req.setUsername(username);
            req.setPassword(password);
            req.setNickname(nickname);
            req.setHeadImage(buf);
            req.setSignature(signature);

            SendMessageUtil.sendMessage(req);
        } else {
            Toast.makeText(MyApplication.getInstance(), "密码不匹配或者为空，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击取消
     */
    public void onClickForCancel() {
        Toast.makeText(MyApplication.getInstance(), "取消", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击选择图片
     */
    public void onClickForSelectPicture() {
//        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//        getAlbum.setType(Constant.IMAGE_TYPE);
//        ((RegisterActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, Constant.IMAGE_CODE);
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(),
                CutViewActivity.class);
        intent.putExtra(Constant.EXTRA_CUT_VIEW, Constant.CUT_VIEW_REGISTER_ACTIVITY);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * Drawable转成Bitmap
     */
    private static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap转成byte[]
     */
    private byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    protected void initData() {

    }
}
