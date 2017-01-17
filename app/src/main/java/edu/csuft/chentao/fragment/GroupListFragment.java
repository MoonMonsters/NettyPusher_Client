package edu.csuft.chentao.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.csuft.chentao.R;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.controller.presenter.FragmentGroupListPresenter;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.databinding.ItemGroupOperationBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.pojo.bean.HandlerMessage;
import edu.csuft.chentao.utils.Constant;

/**
 * @author cusft.chentao
 */
public class GroupListFragment extends BaseFragment {

    private FragmentGroupListBinding mFragmentBinding = null;
    private BroadcastReceiver mReceiver = null;
    private Handler mHandler = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_group_list;
    }

    @Override
    public void setDataBinding(ViewDataBinding viewDataBinding) {
        this.mFragmentBinding = (FragmentGroupListBinding) viewDataBinding;
    }

    @Override
    public void initData() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(mFragmentBinding.tlFragmentBar);

        EventBus.getDefault().register(this);
        FragmentGroupListPresenter presenter = new FragmentGroupListPresenter(mFragmentBinding);
        presenter.init();
        mFragmentBinding.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mReceiver = new GroupListReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_GROUPS);
        this.getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPresenterHandler(HandlerMessage handlerMessage) {
        if (handlerMessage.getTag().equals("GroupListFragment")) {
            this.mHandler = handlerMessage.getHandler();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_group_operation, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //菜单
        if (item.getItemId() == R.id.action_group) {

            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_group_operation, null);
            //弹出菜单
            PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(mFragmentBinding.tlFragmentBar);

            //绑定ItemPresenter
            ItemGroupOperationBinding binding =
                    DataBindingUtil.bind(view);
            binding.setItemPresenter(new FragmentGroupListPresenter.ItemGroupOperationPresenter());

        }

        return true;
    }

    public class GroupListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_GROUPS)) {
                Groups groups = (Groups) intent.getSerializableExtra(Constant.EXTRA_GROUPS);

                Message msg = mHandler.obtainMessage();
                msg.what = Constant.HANDLER_GROUPLIST;
                msg.obj = groups;
                mHandler.sendMessage(msg);
            }
        }
    }
}