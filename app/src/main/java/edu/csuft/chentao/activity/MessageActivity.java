package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMessagePresenter;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.daoutil.GroupChattingItemDaoUtil;

/**
 * @author csuft.chentao
 *         聊天界面
 */
public class MessageActivity extends BaseActivity {

    private ActivityMessageBinding mActivityBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;
    //该聊天群的id
    private int groupId = -1;

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
        //注册EventBus
        EventBus.getDefault().register(this);

        //获得传递进来的群id
        groupId = this.getIntent().getIntExtra(Constant.EXTRA_GROUP_ID, -1);

        ActivityMessagePresenter presenter = new ActivityMessagePresenter(mActivityBinding);
        //初始化并且传递群id
        presenter.init(groupId);
        mActivityBinding.setPresenter(presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CHATTING_MESSAGE);
        filter.addAction(Constant.ACTION_GET_USERINFO);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销广播
        unregisterReceiver(mReceiver);
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
            //发送广播
            OperationUtil.sendBroadcastToUpdateGroupChattingItem(chattingItem);
        }
    }

    /**
     * 接收来自Presenter的Handler对象
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("MessageActivity")) {
            this.mHandler = handlerMessage.getHandler();
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
                通过Handler把image的byte数据发送到Presenter
                 */
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_MESSAGE_CHATTING_MESSAGE_IMAGE;
                msg.obj = buf;
                mHandler.sendMessage(msg);

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

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //广播类型
            String action = intent.getAction();
            LoggerUtil.logger(Constant.TAG, "MessageActivity接收到到广播->" + action);
            //获得传输的数据对象
            ChattingMessage chattingMessage = (ChattingMessage) intent.getSerializableExtra(Constant.EXTRA_CHATTING_MESSAGE);
            if (action.equals(Constant.ACTION_CHATTING_MESSAGE)) {
                //判断接收的数据群id是不是和当前的界面的群id一致
                if (chattingMessage.getGroupid() == groupId) {
                    //发送到Handler中去操作
                    android.os.Message msg = mHandler.obtainMessage();
                    msg.what = Constant.HANDLER_MESSAGE_CHATTING_MESSAGE;
                    msg.obj = chattingMessage;
                    mHandler.sendMessage(msg);
                }
            } else if (action.equals(Constant.ACTION_GET_USERINFO)) {
                LoggerUtil.logger(Constant.TAG, "接收到广播Action_Get_Userinfo" + Constant.ACTION_GET_USERINFO);
                //发送到Handler中去操作
                android.os.Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_PRESENTER_REFRESH;
                mHandler.sendMessage(msg);
            }
        }
    }
}