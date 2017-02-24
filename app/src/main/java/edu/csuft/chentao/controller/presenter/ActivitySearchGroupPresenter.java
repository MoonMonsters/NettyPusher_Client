package edu.csuft.chentao.controller.presenter;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.adapter.SearchGroupContentAdapter;
import edu.csuft.chentao.databinding.ActivitySearchGroupBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.SendMessageUtil;

/**
 * Created by Chalmers on 2017-02-04 15:28.
 * email:qxinhai@yeah.net
 */

public class ActivitySearchGroupPresenter {

    private ActivitySearchGroupBinding mActivityBinding = null;
    private List<GroupInfoResp> mGroupInfoList;
    private SearchGroupContentAdapter mAdapter;
    private ProgressDialog mDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HANDLER_SEARCH_GROUP_SIZE_0) {
                ReturnInfoResp resp = (ReturnInfoResp) msg.obj;
                Toast.makeText(mActivityBinding.getRoot().getContext(), resp.getDescription(), Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        }
    };

    public ActivitySearchGroupPresenter(ActivitySearchGroupBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
//        ArrayAdapter spinnerAdapter = new ArrayAdapter(mActivityBinding.getRoot().getContext(),
//                android.R.layout.simple_spinner_dropdown_item, mActivityBinding.getRoot().getContext().getResources().getTextArray(R.array.group_tag_search_group));
        EventBus.getDefault().post(new HandlerMessage(mHandler, "SearchGroupActivity"));
        mGroupInfoList = new ArrayList<>();
        mAdapter = new SearchGroupContentAdapter(mActivityBinding.getRoot().getContext(), mGroupInfoList);
//        mActivityBinding.setVariable(BR.spinnerAdapter, spinnerAdapter);
        mActivityBinding.setVariable(BR.gridViewAdapter, mAdapter);
        mActivityBinding.setVariable(BR.type, 0);
    }

    /**
     * Spinner的选项选中事件
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mActivityBinding.setVariable(BR.type, position);
    }

    /**
     * 点击搜索
     */
    public void onClickToSearchGroup() {

        mDialog = ProgressDialog.show(mActivityBinding.getRoot().getContext(),
                null, "正在搜索中...", false, true);

        mGroupInfoList.clear();
        GetInfoReq req = new GetInfoReq();
        int position = mActivityBinding.acspinnerSearchGroupType.getSelectedItemPosition();
        if (position == 0) {
            int groupId = Integer.valueOf(mActivityBinding.acetSearchGroupInputNumber.getText()
                    .toString());
            req.setType(Constant.TYPE_GET_INFO_SEARCH_GROUP_ID);
            req.setArg1(groupId);
        } else if (position == 1) {
            String content = mActivityBinding.acetSearchGroupInputText.getText().toString();
            req.setType(Constant.TYPE_GET_INFO_SEARCH_GROUP_NAME);
            req.setObj(content);
        } else if (position == 2) {
            String tag = mActivityBinding.acetSearchGroupInputTag.getSelectedItem().toString();
            req.setType(Constant.TYPE_GET_INFO_SEARCH_GROUP_TAG);
            req.setObj(tag);
        }
        //发送搜索信息
        SendMessageUtil.sendMessage(req);
    }

    /**
     * 隐藏ProgressDialog
     */
    private void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGroupInfoRespData(EBToPreObject ebObj) {
        dismissProgressDialog();
        if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER)) {
            GroupInfoResp resp = (GroupInfoResp) ebObj.getObject();
            synchronized (this) {
                //如果已经存在，则返回，避免数据重复获得
                if (mGroupInfoList.contains(resp)) {
                    return;
                }
                mGroupInfoList.add(resp);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}