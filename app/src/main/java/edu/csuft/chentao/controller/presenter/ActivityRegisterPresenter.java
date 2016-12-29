package edu.csuft.chentao.controller.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.activity.MainActivity;
import edu.csuft.chentao.activity.RegisterActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityRegisterBinding;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.RegisterReq;
import edu.csuft.chentao.pojo.resp.RegisterResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.GreenDaoUtil;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2016-12-24 15:41.
 * email:qxinhai@yeah.net
 */

public class ActivityRegisterPresenter {

    final String IMAGE_TYPE = "image/*";
    public static final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的

    /**
     * DataBinding类型
     */
    private static ActivityRegisterBinding mActivityBinding = null;
    /**
     * RegisterReq数据对象
     */
    private static RegisterReq req = null;

    public ActivityRegisterPresenter(ActivityRegisterBinding activityBinding) {
        this.mActivityBinding = activityBinding;
    }

    public void onClickForRegister() {
        String username = mActivityBinding.etRegisterUsername.getText().toString();
        String password = mActivityBinding.etRegisterPassword.getText().toString();
        String password2 = mActivityBinding.etRegisterPassword2.getText().toString();
        String nickname = mActivityBinding.etRegisterNickname.getText().toString();
        String signature = mActivityBinding.etRegisterSignature.getText().toString();
        Drawable drawable = mActivityBinding.ivRegisterHead.getDrawable();
        if (drawable == null) { //如果没有设置类型，那么则使用默认的图片
            drawable = mActivityBinding.ivRegisterHead.getBackground();
        }
        byte[] buf = bitmapToBytes(drawableToBitmap(drawable));

        if (password.equals(password2) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(password2)) {
            req = new RegisterReq();
            req.setUsername(username);
            req.setPassword(password);
            req.setNickname(nickname);
            req.setHeadImage(buf);
            req.setSignature(signature);

            LoggerUtil.logger("USER", req.toString());
            SendMessageUtil.sendMessage(req);
        } else {
            Toast.makeText(MyApplication.getInstance(), "密码不匹配或者为空，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickForCancel() {
        Toast.makeText(MyApplication.getInstance(), "取消", Toast.LENGTH_SHORT).show();
    }

    public void onClickForSelectPicture() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        ((RegisterActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, IMAGE_CODE);
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

    /**
     * 注册广播
     */
    public static class RegisterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            LoggerUtil.logger(Constant.TAG, "RegisterReceiver->" + action);

            if (action.equals(Constant.ACTION_REGISTER)) {
                RegisterResp resp = (RegisterResp) intent.getSerializableExtra(Constant.EXTRA_REGISTERRESP);

                //弹出提示
                Toast.makeText(MyApplication.getInstance(), resp.getDescription(), Toast.LENGTH_SHORT).show();

                SharedPrefUserInfoUtil.setUserId(resp.getUserid());
                SharedPrefUserInfoUtil.setLoginType();

                //保存用户信息
                UserInfo userInfo = new UserInfo();
                userInfo.setUserid(resp.getUserid());
                userInfo.setSignature(req.getSignature());
                userInfo.setNickname(req.getNickname());
                GreenDaoUtil.getInstance().getDaoSession().getUserInfoDao()
                        .insert(userInfo);

                //保存用户头像
                UserHead userHead = new UserHead();
                userHead.setUserid(resp.getUserid());
                userHead.setImage(req.getHeadImage());
                GreenDaoUtil.getInstance().getDaoSession().getUserHeadDao()
                        .insert(userHead);

                //进入主界面
                mActivityBinding.getRoot().getContext().startActivity(new Intent((RegisterActivity) mActivityBinding.getRoot().getContext(),
                        MainActivity.class));
                //关闭注册界面
                ((RegisterActivity) mActivityBinding.getRoot().getContext())
                        .finish();
            }
        }
    }
}
