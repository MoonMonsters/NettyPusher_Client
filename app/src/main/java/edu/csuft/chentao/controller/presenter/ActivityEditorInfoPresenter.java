package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import edu.csuft.chentao.activity.EditorInfoActivity;
import edu.csuft.chentao.activity.ImageActivity;
import edu.csuft.chentao.databinding.ActivityEditorInfoBinding;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.bean.UserHead;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.pojo.req.UpdateUserInfoReq;
import edu.csuft.chentao.pojo.resp.ReturnMessageResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.UserHeadDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;
import edu.csuft.chentao.view.UpdateInfoDialog;

/**
 * Created by Chalmers on 2017-01-06 18:00.
 * email:qxinhai@yeah.net
 */

public class ActivityEditorInfoPresenter implements UpdateInfoDialog.IDialogClickListener {

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

    private String mUpdateInfo = null;
    private byte[] mImage = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_RETURN_MESSAGE) {
                //注销
                EventBus.getDefault().unregister(ActivityEditorInfoPresenter.this);
                ReturnMessageResp resp = (ReturnMessageResp) msg.obj;
                    //更新成功
                    switch (resp.getType()) {
                        case Constant.TYPE_RETURN_MESSAGE_UPDATE_SIGNATURE_SUCESS:  //更新签名
                            UserInfo userInfo = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                            userInfo.setSignature(mUpdateInfo);
                            UserInfoDaoUtil.updateUserInfo(userInfo);
                            break;
                        case Constant.TYPE_RETURN_MESSAGE_UPDATE_NICKNAME_SUCCESS:   //更新昵称
                            UserInfo userInfo2 = UserInfoDaoUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                            userInfo2.setNickname(mUpdateInfo);
                            UserInfoDaoUtil.updateUserInfo(userInfo2);
                            break;
                        case Constant.TYPE_RETURN_MESSAGE_UPDATE_HEAD_IMAGE_SUCCESS:  //更新头像
                            UserHead userHead = UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
                            userHead.setImage(mImage);
                            UserHeadDaoUtil.updateUserHead(userHead);
                            break;
                    }
                Toast.makeText(mActivityBinding.getRoot().getContext(),
                        resp.getDescription(), Toast.LENGTH_SHORT).show();
            } else if (msg.what == Constant.HANDLER_RETURN_MESSAGE_IMAGE) {
                byte[] buf = (byte[]) msg.obj;
                mImage = buf;
                UpdateUserInfoReq req = new UpdateUserInfoReq();
                req.setContent(null);
                req.setUserid(SharedPrefUserInfoUtil.getUserId());
                req.setHeadImage(buf);
                req.setType(Constant.TYPE_UPDATE_HEADIMAGE);
                SendMessageUtil.sendMessage(req);
            }
        }
    };

    public ActivityEditorInfoPresenter(ActivityEditorInfoBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        EventBus.getDefault().post(new HandlerMessage(mHandler, "EditorInfoActivity"));
    }

    /**
     * 更新头像
     */
    public void onClickToUpdateHeadImage() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(Constant.IMAGE_TYPE);
        ((EditorInfoActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, Constant.IMAGE_CODE);
    }

    /**
     * 放大头像
     */
    public void onClickToBigImage() {
        ImageDetail detail = new ImageDetail(UserHeadDaoUtil.getUserHead(SharedPrefUserInfoUtil.getUserId()).getImage());
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
}