package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivityEditorInfoBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.ChangePasswordReq;
import edu.csuft.chentao.pojo.req.UpdateUserInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.ui.activity.CutViewActivity;
import edu.csuft.chentao.ui.activity.EditorInfoActivity;
import edu.csuft.chentao.ui.activity.ImageActivity;
import edu.csuft.chentao.ui.view.ChangePasswordDialog;
import edu.csuft.chentao.ui.view.UpdateInfoDialog;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * 编辑个人信息的Presenter
 * Created by Chalmers on 2017-01-06 18:00.
 * email:qxinhai@yeah.net
 */
public class ActivityEditorInfoPresenter extends BasePresenter implements UpdateInfoDialog.IDialogClickListener, ChangePasswordDialog.IOnClickToChangePassword {

    private ActivityEditorInfoBinding mActivityBinding = null;
    /**
     * 更新签名
     */
    private final int UPDATE_SIGNATURE = 0;
    /**
     * 更新昵称
     */
    private final int UPDATE_NICKNAME = 1;
    /**
     * 更新索引
     */
    private int UPDATE_INDEX = -1;

    /**
     * 更新的内容
     */
    private String mUpdateInfo = null;
    /**
     * 记录下来更新的图片
     */
    private byte[] mImage = null;

    public ActivityEditorInfoPresenter(ActivityEditorInfoBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        //如果是更新用户数据命令
        if (ebObj.getTag().equals(Constant.TAG_UPDATE_USER_INFO)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();

            LoggerUtil.logger("TAG", "ActivityEditorInfoPresenter...." + resp.toString());

            //更新成功
            switch (resp.getType()) {
                case Constant.TYPE_RETURN_INFO_UPDATE_SIGNATURE_SUCESS:  //更新签名
                    UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                    userInfo.setSignature(mUpdateInfo);
                    UserInfoDaoUtil.updateUserInfo(userInfo);
                    break;
                case Constant.TYPE_RETURN_INFO_UPDATE_NICKNAME_SUCCESS:   //更新昵称
                    UserInfo userInfo2 = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                    userInfo2.setNickname(mUpdateInfo);
                    UserInfoDaoUtil.updateUserInfo(userInfo2);
                    break;
                case Constant.TYPE_RETURN_INFO_UPDATE_HEAD_IMAGE_SUCCESS:  //更新头像
                    UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
                    userHead.setImage(mImage);
                    UserHeadDaoUtil.updateUserHead(userHead);
                    break;
                case Constant.TYPE_RETURN_INFO_CHANGE_PASSWORD_SUCCESS: //修改密码
                    String password = (String) resp.getObj();
                    SharedPrefUserInfoUtil.setUsernameAndPassword(SharedPrefUserInfoUtil.getUsername(), password);
                    LoggerUtil.showToast(mActivityBinding.getRoot().getContext(), "密码设置成功");
                    break;
            }
            LoggerUtil.showToast(mActivityBinding.getRoot().getContext(),
                    (String) resp.getObj());
        }
    }

    /**
     * 点击选择更新头像
     */
    public void onClickToUpdateHeadImage() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), CutViewActivity.class);
        intent.putExtra(Constant.EXTRA_CUT_VIEW, Constant.CUT_VIEW_EDITOR_INFO_PRESENTER);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    /**
     * 接收从Activity传递过来的Image数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.CUT_VIEW_EDITOR_INFO_PRESENTER)) {
            byte[] buf = imageDetail.getImage();
            mImage = buf;
            UpdateUserInfoReq req = new UpdateUserInfoReq();
            req.setContent(null);
            req.setUserid(SharedPrefUserInfoUtil.getUserId());
            req.setHeadImage(buf);
            req.setType(Constant.TYPE_UPDATE_HEADIMAGE);
            SendMessageUtil.sendMessage(req);
        }
    }

    /**
     * 放大头像
     */
    public void onClickToBigImage() {
        ImageDetail detail = new ImageDetail(null, UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId()).getImage());
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(),
                ImageActivity.class);
        intent.putExtra(Constant.EXTRA_IMAGE_DETAIL, detail);
        mActivityBinding.getRoot().getContext()
                .startActivity(intent);
    }

    /**
     * 更新昵称
     */
    public void onClickToUpdateNickname() {
        UPDATE_INDEX = UPDATE_NICKNAME;
        UpdateInfoDialog dialog = new UpdateInfoDialog(mActivityBinding.getRoot().getContext(),
                this, mActivityBinding.tvEditorNickname.getText().toString());
        dialog.show();
    }

    /**
     * 更新签名
     */
    public void onClickToUpdateSignature() {
        UPDATE_INDEX = UPDATE_SIGNATURE;
        //显示对话框
        UpdateInfoDialog dialog = new UpdateInfoDialog(mActivityBinding.getRoot().getContext(),
                this, mActivityBinding.tvEditorSignature.getText().toString());
        dialog.show();
    }

    /**
     * 回调方法
     */
    @Override
    public void onClickToUpdateInfo(String updateInfo) {
        //保存更新的信息
        mUpdateInfo = updateInfo;
        UpdateUserInfoReq req = new UpdateUserInfoReq();
        switch (UPDATE_INDEX) {
            case UPDATE_NICKNAME:   //更新用户昵称
                req.setType(Constant.TYPE_UPDATE_NICKNAME);
                break;
            case UPDATE_SIGNATURE:  //更新签名
                req.setType(Constant.TYPE_UPDATE_SIGNATURE);
                break;
        }
        req.setUserid(SharedPrefUserInfoUtil.getUserId());
        req.setContent(updateInfo);
        req.setHeadImage(null);
        //发送更新信息
        SendMessageUtil.sendMessage(req);
    }

    /**
     * 修改密码
     */
    public void doClickToChangePassword() {
        ChangePasswordDialog dialog = new ChangePasswordDialog(mActivityBinding.getRoot().getContext(), this);
        dialog.show();
    }

    @Override
    protected void initData() {
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
        UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
        //设置属性
        mActivityBinding.setVariable(BR.userHead, userHead);
        mActivityBinding.setVariable(BR.userInfo, userInfo);
        mActivityBinding.setVariable(BR.title, "用户信息");
    }

    @Override
    public void initListener() {
        super.initListener();
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditorInfoActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }

    /**
     * 修改密码
     */
    @Override
    public void doClickToChangePassword(String oldPassword, String newPassword, String newPassword2) {
        LoggerUtil.logger("TAG", "ActivityEditorInfoPresenter-->更改密码---->" + oldPassword);

        ChangePasswordReq req = new ChangePasswordReq();
        req.setUserId(SharedPrefUserInfoUtil.getUserId());
        req.setOldPassword(oldPassword);
        req.setNewPassword(newPassword);
        req.setNewPassword2(newPassword2);

        SendMessageUtil.sendMessage(req);
    }
}