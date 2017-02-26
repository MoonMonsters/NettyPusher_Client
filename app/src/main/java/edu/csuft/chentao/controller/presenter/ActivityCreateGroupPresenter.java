package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import edu.csuft.chentao.R;
import edu.csuft.chentao.activity.CreateGroupActivity;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityCreateGroupBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.CreateGroupReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2017-01-21 17:56.
 * email:qxinhai@yeah.net
 */

public class ActivityCreateGroupPresenter {

    private ActivityCreateGroupBinding mActivityBinding;
    private ImageDetail mImageDetail;

    public ActivityCreateGroupPresenter(ActivityCreateGroupBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    private void init() {
        initData();
    }

    private void initData() {
        EventBus.getDefault().register(this);
        ArrayAdapter adapter = new ArrayAdapter(mActivityBinding.getRoot().getContext(),
                android.R.layout.simple_list_item_1, mActivityBinding.getRoot().getContext().getResources().getTextArray(R.array.group_tags));
        mActivityBinding.acsCreateGroupTag.setAdapter(adapter);
    }

    /**
     * 提交
     */
    public void onClickToSubmit() {
        String groupName = mActivityBinding.etCreateGroupGroupName.getText().toString();
        if (TextUtils.isEmpty(groupName)) {
            Toast.makeText(MyApplication.getInstance(), "群名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateGroupReq req = new CreateGroupReq();
        req.setCreatorId(SharedPrefUserInfoUtil.getUserId());
        req.setHeadImage(mImageDetail.getImage());
        req.setGroupname(groupName);
        req.setTag(mActivityBinding.acsCreateGroupTag.getSelectedItem().toString());

        SendMessageUtil.sendMessage(req);
    }

    /**
     * 选择头像
     */
    public void onClickToSelectImage() {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(Constant.IMAGE_TYPE);
        ((CreateGroupActivity) (mActivityBinding.getRoot().getContext())).startActivityForResult(getAlbum, Constant.IMAGE_CODE);
    }

    @Subscribe
    public void getEBToPresenterObject(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_CREATE_GROUP_PRESENTER)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            //弹出提示框
            Toast.makeText(mActivityBinding.getRoot().getContext(), resp.getDescription(), Toast.LENGTH_SHORT).show();
            //创建成功，关闭当前界面
            if (resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS) {
                ((CreateGroupActivity) (mActivityBinding.getRoot().getContext())).finish();
            }
        }
    }

    @Subscribe
    public void getGroupImageBytes(ImageDetail imageDetail) {
        this.mImageDetail = imageDetail;
        mActivityBinding.setDetail(imageDetail);
    }
}