package edu.csuft.chentao.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SendMessageUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;
import edu.csuft.chentao.utils.daoutil.ChattingMessageDaoUtil;
import edu.csuft.chentao.utils.daoutil.GroupsDaoUtil;

/**
 * 启动服务，在此服务中，对群中的聊天数据进行同步操作
 */
public class SyncMessageService extends IntentService {

    public SyncMessageService(String name) {
        super(name);
    }

    public SyncMessageService() {
        this("SyncMessageService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        List<Groups> groupsList = GroupsDaoUtil.loadAll();
        String startTime = SharedPrefUserInfoUtil.getStartActiveTime();
        String endTime = SharedPrefUserInfoUtil.getEndInactiveTime();

        LoggerUtil.logger(SyncMessageService.class, "startTime = " + startTime);
        LoggerUtil.logger(SyncMessageService.class, "endTime = " + endTime);

        for (Groups g : groupsList) {
            String gTime = ChattingMessageDaoUtil.getMaxTimeByGroupId(g.getGroupid());
            String maxTime = getMaxTime(startTime, endTime, gTime);

            LoggerUtil.logger(SyncMessageService.class, "gTime = " + gTime);
            LoggerUtil.logger(SyncMessageService.class, "maxTime = " + maxTime);

            GetInfoReq req = new GetInfoReq();
            req.setType(Constant.TYPE_GET_INFO_LOAD_MESSAGE);
            req.setArg1(g.getGroupid());
            req.setObj(maxTime);

            SendMessageUtil.sendMessage(req);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerUtil.logger("CHENTAO22", "SyncMessageService.create");
    }

    /**
     * 得到三者中的最大者
     */
    private String getMaxTime(String... times) {
        List<String> timeList = new ArrayList<>();
        for (String t : times) {
            if (!TextUtils.isEmpty(t)) {
                timeList.add(t);
            }
        }

        if (timeList.size() != 0) {
            LoggerUtil.logger(SyncMessageService.class, timeList.toString());
            Collections.sort(timeList, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    return rhs.compareTo(lhs);
                }
            });
            return timeList.get(0);
        }

        return null;
    }
}
