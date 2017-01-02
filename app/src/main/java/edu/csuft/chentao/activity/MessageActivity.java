package edu.csuft.chentao.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseActivity;
import edu.csuft.chentao.controller.presenter.ActivityMessagePresenter;
import edu.csuft.chentao.dao.GroupChattingItemDao;
import edu.csuft.chentao.databinding.ActivityMessageBinding;
import edu.csuft.chentao.pojo.bean.ChattingMessage;
import edu.csuft.chentao.pojo.bean.GroupChattingItem;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.DaoSessionUtil;
import edu.csuft.chentao.utils.OperationUtil;

public class MessageActivity extends BaseActivity {

    private ActivityMessageBinding mActivityBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;
    //该聊天群的id
    private static int groupId = -1;

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
        groupId = this.getIntent().getIntExtra(Constant.EXTRA_GROUPID, -1);

        ActivityMessagePresenter presenter = new ActivityMessagePresenter(mActivityBinding);
        //初始化并且传递群id
        presenter.init(groupId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CHATTING_MESSAGE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);

        GroupChattingItem chattingItem = DaoSessionUtil.getGroupChattingItemDao().queryBuilder()
                .where(GroupChattingItemDao.Properties.Groupid.eq(groupId))
                .build().list().get(0);
        chattingItem.setNumber(0);
        DaoSessionUtil.getGroupChattingItemDao().update(chattingItem);

        OperationUtil.sendBroadcastToUpdateGroupChattingItem(chattingItem);
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

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //广播类型
            String action = intent.getAction();
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
            }
        }
    }
}