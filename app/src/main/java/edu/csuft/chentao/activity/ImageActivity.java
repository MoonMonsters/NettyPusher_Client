package edu.csuft.chentao.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.databinding.ActivityImageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.utils.Constant;

public class ImageActivity extends BaseActivity {

    private ActivityImageBinding mActivityBinding = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_image;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityImageBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        ChattingMessage msg = (ChattingMessage) intent.getSerializableExtra(Constant.EXTRA_CHATTING_MESSAGE);
        mActivityBinding.setMessage(msg);
    }
}
