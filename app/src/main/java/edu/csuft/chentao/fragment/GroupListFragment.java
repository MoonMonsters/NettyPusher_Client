package edu.csuft.chentao.fragment;


import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import edu.csuft.chentao.R;
import edu.csuft.chentao.adapter.GroupListAdapter;
import edu.csuft.chentao.base.BaseFragment;
import edu.csuft.chentao.dao.GroupsDao;
import edu.csuft.chentao.databinding.FragmentGroupListBinding;
import edu.csuft.chentao.pojo.bean.Groups;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.GreenDaoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends BaseFragment {

    private FragmentGroupListBinding mFragmentBinding = null;
    private List<Groups> mGroupsList = null;
    private GroupsDao mGroupsDao = null;
    private GroupListAdapter mAdapter = null;

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
        mGroupsDao = GreenDaoUtil.getInstance().getDaoSession().getGroupsDao();
        mGroupsList = mGroupsDao.queryBuilder().list();
        mAdapter = new GroupListAdapter(mGroupsList);

        mFragmentBinding.rvGroupListContent.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mFragmentBinding.rvGroupListContent.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mFragmentBinding.btnGroupListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Groups groups = new Groups();
                groups.setGroupid(10009);
                groups.setGroupname("Hello");
                try {
                    File file = new File(Constant.PATH, "splash.jpg");
                    FileInputStream fis = new FileInputStream(file);
                    int length = fis.available();
                    byte[] buf = new byte[length];
                    fis.read(buf, 0, length);
                    groups.setImage(buf);

                    mGroupsDao.insert(groups);
                    mGroupsList.add(groups);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
