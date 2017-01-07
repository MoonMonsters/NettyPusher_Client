package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
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
     * 更新头像
     */
    private final int UPDATE_HEADIMAGE = 2;
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
                if (resp.getType() == Constant.TYPE_RETURN_MESSAGE_SUCCESS) {
                    switch (UPDATE_INDEX) {
                        case UPDATE_SIGNATURE:  //更新签名
                            UserInfo userInfo = DaoSessionUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                            userInfo.setSignature(mUpdateInfo);
                            DaoSessionUtil.getUserInfoDao().update(userInfo);
                            break;
                        case UPDATE_NICKNAME:   //更新昵称
                            UserInfo userInfo2 = DaoSessionUtil.getUserInfo(SharedPrefUserInfoUtil.getUserId());
                            userInfo2.setNickname(mUpdateInfo);
                            DaoSessionUtil.getUserInfoDao().update(userInfo2);
                            break;
                        case UPDATE_HEADIMAGE:  //更新头像
                            UserHead userHead = DaoSessionUtil.getUserHead(SharedPrefUserInfoUtil.getUserId());
                            userHead.setImage(mImage);
                            DaoSessionUtil.getUserHeadDao().update(userHead);
                            break;
                    }
                }
                Toast.makeText(mActivityBinding.getRoot().getContext(),
                        resp.getDescription(), Toast.LENGTH_SHORT).show();
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
        UPDATE_INDEX = UPDATE_HEADIMAGE;
        EventBus.getDefault().register(this);
//        Toast.makeText(mActivityBinding.getRoot().getContext(), "onClickToUpdateHeadImage", Toast.LENGTH_SHORT).show();
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        ((EditorInfoActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, 0);
    }

    /**
     * 放大头像
     */
    public void onClickToBigImage() {
        ImageDetail detail = new ImageDetail(DaoSessionUtil.getUserHead(SharedPrefUserInfoUtil.getUserId()).getImage());
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

        Toast.makeText(mActivityBinding.getRoot().getContext(), updateInfo, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageBytes(byte[] buf) {
        mImage = buf;
        UpdateUserInfoReq req = new UpdateUserInfoReq();
        req.setContent(null);
        req.setUserid(SharedPrefUserInfoUtil.getUserId());
        req.setHeadImage(buf);
        req.setType(Constant.TYPE_UPDATE_HEADIMAGE);
        SendMessageUtil.sendMessage(req);
    }
}
