package edu.csuft.chentao.controller.presenter;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.AdapterView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BasePresenter;
import edu.csuft.chentao.databinding.ActivitySearchGroupBinding;
import edu.csuft.chentao.pojo.bean.EBToPreObject;
import edu.csuft.chentao.pojo.req.GetInfoReq;
import edu.csuft.chentao.pojo.resp.GroupInfoResp;
import edu.csuft.chentao.pojo.resp.ReturnInfoResp;
import edu.csuft.chentao.ui.activity.SearchGroupActivity;
import edu.csuft.chentao.ui.adapter.SearchGroupContentAdapter;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.OperationUtil;
import edu.csuft.chentao.utils.SendMessageUtil;

/**
 * Created by Chalmers on 2017-02-04 15:28.
 * email:qxinhai@yeah.net
 */

public class ActivitySearchGroupPresenter extends BasePresenter {

    private ActivitySearchGroupBinding mActivityBinding = null;
    private List<GroupInfoResp> mGroupInfoList;
    private SearchGroupContentAdapter mAdapter;
    private ProgressDialog mDialog;

    public ActivitySearchGroupPresenter(ActivitySearchGroupBinding activityBinding) {
        this.mActivityBinding = activityBinding;
        init();
    }

    @Override
    protected void initData() {
        mGroupInfoList = new ArrayList<>();
        mAdapter = new SearchGroupContentAdapter(mActivityBinding.getRoot().getContext(), mGroupInfoList);
        mActivityBinding.setVariable(BR.gridViewAdapter, mAdapter);
        mActivityBinding.setVariable(BR.type, 0);
        mActivityBinding.setVariable(BR.title, "搜索群");
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
                null, OperationUtil.getString(mActivityBinding, R.string.string_searching), false, true);

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

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEBToObjectPresenter(EBToPreObject ebObj) {
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
            //隐藏对话框
            dismissProgressDialog();
            //搜索到的群数量为0
        } else if (ebObj.getTag().equals(Constant.TAG_ACTIVITY_SEARCH_GROUP_PRESENTER_SIZE_0)) {
            ReturnInfoResp resp = (ReturnInfoResp) ebObj.getObject();
            //弹出提示框
            LoggerUtil.showToast(mActivityBinding.getRoot().getContext(), (String) resp.getObj());
            //隐藏对话框
            dismissProgressDialog();
        }
    }

    @Override
    public void initListener() {
        mActivityBinding.includeToolbar.layoutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchGroupActivity) mActivityBinding.getRoot().getContext()).finish();
            }
        });
    }
}
