package edu.csuft.chentao.controller.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.ui.activity.CreateGroupActivity;
import edu.csuft.chentao.ui.activity.CutViewActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.databinding.ActivityCreateGroupBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.pojo.req.CreateGroupReq;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * Created by Chalmers on 2017-01-21 17:56.
 * email:qxinhai@yeah.net
 */

/**
 * CreateGroupActivity的处理类
 */
public class ActivityCreateGroupPresenter extends BasePresenter {

    private ActivityCreateGroupBinding mActivityBinding;

    public ActivityCreateGroupPresenter(ActivityCreateGroupBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    @Override
    protected void initData() {
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
            Toast.makeText(MyApplication.getInstance(), OperationUtil.getString(mActivityBinding,R.string.string_not_groupname_null), Toast.LENGTH_SHORT).show();
            return;
        }

        //头像
        Drawable drawable = mActivityBinding.ivCreateGroupHead.getDrawable();
        //如果没有设置类型，那么则使用默认的图片
        if (drawable == null) {
            drawable = mActivityBinding.getRoot().getContext()
                    .getResources().getDrawable(R.mipmap.ic_launcher);
        }
        //转成byte[]类型
        byte[] buf = OperationUtil.bitmapToBytes(drawableToBitmap(drawable));

        CreateGroupReq req = new CreateGroupReq();
        req.setCreatorId(SharedPrefUserInfoUtil.getUserId());
        req.setHeadImage(buf);
        req.setGroupname(groupName);
        req.setTag(mActivityBinding.acsCreateGroupTag.getSelectedItem().toString());

        SendMessageUtil.sendMessage(req);
    }

    /**
     * 选择头像
     */
    public void onClickToSelectImage() {
        Intent intent = new Intent(mActivityBinding.getRoot().getContext(), CutViewActivity.class);
        intent.putExtra(Constant.EXTRA_CUT_VIEW, Constant.CUT_VIEW_CREATE_GROUP_ACTIVITY);
        mActivityBinding.getRoot().getContext().startActivity(intent);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
        if (ebObj.getTag().equals(Constant.TAG_CREATE_GROUP_PRESENTER)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            //弹出提示框
            Toast.makeText(mActivityBinding.getRoot().getContext(), (String) resp.getObj(), Toast.LENGTH_SHORT).show();
            //创建成功，关闭当前界面
            if (resp.getType() == Constant.TYPE_RETURN_INFO_CREATE_GROUP_SUCCESS) {
                ((CreateGroupActivity) (mActivityBinding.getRoot().getContext())).finish();
            }
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageDetail(ImageDetail imageDetail) {
        if (imageDetail.getTag().equals(Constant.CUT_VIEW_CREATE_GROUP_ACTIVITY)) {
            mActivityBinding.setVariable(BR.detail, imageDetail);
        }
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
}