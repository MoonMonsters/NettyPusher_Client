package edu.csuft.chentao.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMessagePresenter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.ImageDetail;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;

/**
 * @author csuft.chentao
 *         聊天界面
 */
public class MessageActivity extends BaseActivity {

    private ActivityMessageBinding mActivityBinding = null;
    //该聊天群的id
    private int groupId = -1;

    private ActivityMessagePresenter mPresenter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_message;
    }

    @Override
    public void setActivityBinding(ViewDataBinding viewDataBinding) {
        this.mActivityBinding = (ActivityMessageBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        //获得传递进来的群id
        groupId = this.getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);

        mPresenter = new ActivityMessagePresenter(mActivityBinding, groupId);
        mActivityBinding.setVariable(BR.presenter, mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unregisterEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*
        从该界面退出后，需要把聊天栏的该项的数量置为0，表示该项已经被阅读完成
         */
        //根据群id得到GroupChattingItem对象
        GroupChattingItem chattingItem = GroupChattingItemDaoUtil.getGroupChattingItem(groupId);
        if (chattingItem != null) {
            //将数据清0
            chattingItem.setNumber(0);
            //更新
            GroupChattingItemDaoUtil.updateGroupChattingItem(chattingItem);
//            EBToPreObject ebObj = new EBToPreObject(Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_UPDATE_ITEM, chattingItem);
//            EventBus.getDefault().post(ebObj);

            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FRAGMENT_CHATTING_LIST_PRESENTER_UPDATE_ITEM, chattingItem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == Constant.IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);//显得到bitmap图片

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] buf = baos.toByteArray();

                /*
                发送到Presenter中去处理
                 */
//                ImageDetail detail = new ImageDetail(Constant.IMAGE_ACTIVITY_MESSAGE_PRESENTER, buf);
//                EventBus.getDefault().post(detail);

                OperationUtil.sendImageDetail(Constant.IMAGE_ACTIVITY_MESSAGE_PRESENTER, buf);

            } catch (Exception e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_enter_group_detail) {
            Intent intent = new Intent(this, GroupDetailActivity.class);
            intent.putExtra(Constant.EXTRA_GROUP_ID, groupId);
            this.startActivity(intent);
        }
        return true;
    }
}