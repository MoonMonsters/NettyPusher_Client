package edu.csuft.chentao.ui.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import edu.csuft.chentao.R;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.Hint;
import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.ui.activity.HintActivity;
import edu.csuft.chentao.ui.activity.MessageActivity;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

/**
 * Created by Chalmers on 2017-05-09 11:47.
 * email:qxinhai@yeah.net
 */

public class MessageNotification {

    private Context mContext;

    public MessageNotification(Context context) {
        this.mContext = context;

        LoggerUtil.logger("TAG", "MessageNotification-->Context");
    }

    public void show(Object object) {

        String title = null;
        String content = null;
        String ticker = "新消息";
        Class c = null;
        int groupId = -1;

        if (object instanceof Message) {
            Message msg = (Message) object;
            groupId = msg.getGroupid();
            Groups groups = GroupsDaoUtil.getGroups(groupId);
            title = groups.getGroupname();
            if (msg.getType() == Constant.TYPE_MSG_IMAGE) { //如果发送都是图片
                content = "[图片]";
            } else if (msg.getType() == Constant.TYPE_MSG_TEXT) {   //如果发送的是文字
                content = msg.getMessage();
            }
            c = MessageActivity.class;
        } else if (object instanceof Hint) {
            Hint hint = (Hint) object;
            title = "新通知";
            content = hint.getDescription();
            c = HintActivity.class;
        }

        LoggerUtil.logger("TAG", "MessageNotification-->show");
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setContentText(content)
                .setTicker(ticker)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        int requestCode = 3;
        Intent intent = new Intent(mContext, c);
        intent.putExtra(Constant.EXTRA_GROUP_ID, groupId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(requestCode, builder.getNotification());
    }
}
