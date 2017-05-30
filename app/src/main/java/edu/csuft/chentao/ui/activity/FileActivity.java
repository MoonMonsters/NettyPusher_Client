package edu.csuft.chentao.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.controller.presenter.ActivityFilePresenter;
import edu.csuft.chentao.databinding.ActivityFileBinding;
import edu.csuft.chentao.utils.Constant;

public class FileActivity extends BaseActivity {

    private ActivityFilePresenter mPresenter;
    private ActivityFileBinding mActivityBinding;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_file;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityFileBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        setSupportActionBar(mActivityBinding.includeToolbar.layoutToolbar);
        int groupId = getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);
        mPresenter = new ActivityFilePresenter(mActivityBinding, groupId);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //点击上传按钮
        if (item.getItemId() == R.id.action_file_upload) {
            mPresenter.selectFile();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mPresenter.setPathUri(uri);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
