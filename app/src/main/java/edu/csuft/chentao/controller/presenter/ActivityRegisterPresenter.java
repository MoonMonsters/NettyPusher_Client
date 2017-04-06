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

import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.CutViewActivity;
import edu.csuft.chentao.ui.activity.LoginActivity;
import edu.csuft.chentao.ui.activity.MainActivity;
import edu.csuft.chentao.ui.activity.RegisterActivity;
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
import edu.csuft.chentao.utils.OperationUtil;
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

    @Override
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
            SharedPrefUserInfoUtil.setUsernameAndPassword(mActivityBinding.etRegisterUsername.getEditText().getText().toString(),
                    mActivityBinding.etRegisterPassword.getEditText().getText().toString());

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

            mActivityBinding.getRoot().getContext()
                    .startActivity(new Intent(mActivityBinding.getRoot().getContext(), MainActivity.class));
            ((RegisterActivity) (mActivityBinding.getRoot().getContext()))
                    .finish();
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.CUT_VIEW_REGISTER_ACTIVITY)) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageDetail.getImage(), 0, imageDetail.getImage().length);
            mActivityBinding.ivRegisterHead.setImageBitmap(bitmap);
        }
    }

    /**
     * Username输入框的输入监听事件
     */
    public void onTextChangedForUsername(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) { //用户名不能为空
            notNoneForUsername();
        } else if (s.length() < 6) {    //用户名不能低于6位
            mActivityBinding.etRegisterUsername.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_length_less6_username));
            mActivityBinding.etRegisterUsername.setErrorEnabled(true);
        } else if (TextUtils.isDigitsOnly(s)) { //用户名不能为纯数字
            mActivityBinding.etRegisterUsername.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_only_digit_username));
            mActivityBinding.etRegisterUsername.setErrorEnabled(true);
        } else {
            mActivityBinding.etRegisterUsername.setError(null);
            mActivityBinding.etRegisterUsername.setErrorEnabled(false);
        }
    }

    /**
     * Username输入框的输入监听事件
     */
    public void onTextChangedForPassword(CharSequence s, int start, int before, int count) {
        //得到密码
        String password = mActivityBinding.etRegisterPassword.getEditText().getText().toString();
        //得到重复密码
        String password2 = mActivityBinding.etRegisterPassword2.getEditText().getText().toString();

        if (TextUtils.isEmpty(s)) { //用户名不能为空
            notNoneForPassword();
        } else if (s.length() < 6) {    //用户名不能低于6位
            mActivityBinding.etRegisterPassword.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_length_less6_password));
            mActivityBinding.etRegisterPassword.setErrorEnabled(true);
        } else if (!TextUtils.isEmpty(password2)
                && !password.equals(password2)) {
            mActivityBinding.etRegisterPassword.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_equal_password));
            mActivityBinding.etRegisterPassword.setErrorEnabled(true);
        } else if (password.equals(password2)) {    //密码匹配了，两个输入框的错误提示信息都隐藏
            hideTipsForPassword();
            hideTipsForPassword2();
        } else {
            hideTipsForPassword();
        }
    }

    /**
     * Username输入框的输入监听事件
     */
    public void onTextChangedForPassword2(CharSequence s, int start, int before, int count) {
        //得到密码
        String password = mActivityBinding.etRegisterPassword.getEditText().getText().toString();
        //得到重复密码
        String password2 = mActivityBinding.etRegisterPassword2.getEditText().getText().toString();

        if (TextUtils.isEmpty(s)) { //密码不能为空
            notNoneForPassword2();
        } else if (s.length() < 6) {    //密码不能低于6位
            mActivityBinding.etRegisterPassword2.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_length_less6_password));
            mActivityBinding.etRegisterPassword2.setErrorEnabled(true);
        } else if (!TextUtils.isEmpty(password)
                && !password.equals(password2)) {   //密码不匹配
            mActivityBinding.etRegisterPassword2.setError(
                    mActivityBinding.getRoot().getContext().getString(R.string.string_not_equal_password));
            mActivityBinding.etRegisterPassword2.setErrorEnabled(true);
        } else if (password.equals(password2)) {    //密码匹配了，两个输入框的错误提示信息都隐藏
            hideTipsForPassword();
            hideTipsForPassword2();
        } else {
            hideTipsForPassword2();
        }
    }

    /**
     * 用户名不能为空
     */
    private void notNoneForUsername() {
        mActivityBinding.etRegisterUsername.setError(
                mActivityBinding.getRoot().getContext().getString(R.string.string_not_none_username));
        mActivityBinding.etRegisterUsername.setErrorEnabled(true);
    }

    /**
     * 密码不能为空
     */
    private void notNoneForPassword() {
        mActivityBinding.etRegisterPassword.setError(
                mActivityBinding.getRoot().getContext().getString(R.string.string_not_none_password));
        mActivityBinding.etRegisterPassword.setErrorEnabled(true);
    }

    /**
     * 重复密码不能为空
     */
    private void notNoneForPassword2() {
        mActivityBinding.etRegisterPassword2.setError(
                mActivityBinding.getRoot().getContext().getString(R.string.string_not_none_password));
        mActivityBinding.etRegisterPassword2.setErrorEnabled(true);
    }

    /**
     * 隐藏password的错误提示框
     */
    private void hideTipsForPassword() {
        mActivityBinding.etRegisterPassword.setError(null);
        mActivityBinding.etRegisterPassword.setErrorEnabled(false);
    }

    /**
     * 隐藏password2的隐藏提示框
     */
    private void hideTipsForPassword2() {
        mActivityBinding.etRegisterPassword2.setError(null);
        mActivityBinding.etRegisterPassword2.setErrorEnabled(false);
    }

    /**
     * 点击注册
     */
    public void onClickForRegister() {
        //用户名
        String username = mActivityBinding.etRegisterUsername.getEditText().getText().toString();
        //密码
        String password = mActivityBinding.etRegisterPassword.getEditText().getText().toString();
        String password2 = mActivityBinding.etRegisterPassword2.getEditText().getText().toString();

        /**
         * 加入下面的判断，是为了避免用户不输入任何数据直接进行注册操作
         */
        //用户名不能为空
        if (TextUtils.isEmpty(username)) {
            notNoneForUsername();

            return;
        }

        //密码不能为空
        if (TextUtils.isEmpty(password)) {
            notNoneForPassword();

            return;
        }

        //重复密码不能为空
        if (TextUtils.isEmpty(password2)) {
            notNoneForPassword2();
            return;
        }

        //密码和用户名均不能低于6位
        if (username.length() < 6
                || TextUtils.isDigitsOnly(username)
                || password.length() < 6
                || password2.length() < 6) {
            return;
        }

        //昵称
        String nickname = mActivityBinding.etRegisterNickname.getText().toString();
        //签名
        String signature = mActivityBinding.etRegisterSignature.getText().toString();
        //头像
        Drawable drawable = mActivityBinding.ivRegisterHead.getDrawable();
        //如果没有设置类型，那么则使用默认的图片
        if (drawable == null) {
//            drawable = mActivityBinding.ivRegisterHead.getBackground();
            drawable = mActivityBinding.getRoot().getContext()
                    .getResources().getDrawable(R.mipmap.ic_launcher);
        }
        //转成byte[]类型
        byte[] buf = OperationUtil.bitmapToBytes(drawableToBitmap(drawable));

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
        mActivityBinding.getRoot().getContext()
                .startActivity(new Intent(mActivityBinding.getRoot().getContext(), LoginActivity.class));

        ((RegisterActivity) (mActivityBinding.getRoot().getContext()))
                .finish();
    }

    /**
     * 点击选择图片
     */
    public void onClickForSelectPicture() {
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

    @Override
    protected void initData() {

    }
}
