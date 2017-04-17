package edu.csuft.chentao.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.ItemAnnouncementBinding;
import edu.csuft.chentao.pojo.bean.LocalAnnouncement;
import edu.csuft.chentao.pojo.bean.UserInfo;
import edu.csuft.chentao.utils.daoutil.LocalAnnouncementDaoUtil;
import edu.csuft.chentao.utils.daoutil.UserInfoDaoUtil;

/**
 * 群公告列表的适配器
 */
public class AnnouncementAdapter extends BaseAdapter {

    private List<LocalAnnouncement> mAnnouncementList = null;
    private Context mContext;

    public AnnouncementAdapter(Context context, List<LocalAnnouncement> announcementList) {
        this.mAnnouncementList = announcementList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mAnnouncementList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnnouncementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemAnnouncementBinding itemBinding;

        if (convertView == null) {
            itemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_announcement, parent, false);
        } else {
            itemBinding = DataBindingUtil.getBinding(convertView);
        }

        //得到当前的LocalAnnouncement对象
        LocalAnnouncement la = mAnnouncementList.get(position);
        //根据用户id得到用户信息
        UserInfo userInfo = UserInfoDaoUtil.getUserInfo(la.getUserid());
        //判断用户的昵称是否和公告中的昵称一致，如果不一致，则更新公告中的昵称
        if (!userInfo.getNickname().equals(la.getUsername())) {
            la.setUsername(userInfo.getNickname());
            LocalAnnouncementDaoUtil.update(la);
        }

        itemBinding.setVariable(BR.announcement, la);
        itemBinding.setVariable(BR.itemPresenter, new ItemAnnouncementPresenter(itemBinding));

        return itemBinding.getRoot();
    }

    public class ItemAnnouncementPresenter {

        private ItemAnnouncementBinding mItemBinding;

        ItemAnnouncementPresenter(ItemAnnouncementBinding itemBinding) {
            this.mItemBinding = itemBinding;
        }

        /**
         * 点击item，显示完整的内容
         */
        public void doClickToShowContent() {
            mItemBinding.tvAnnouncementContent.setVisibility(mItemBinding.tvAnnouncementContent.getVisibility()
                    == View.GONE ? View.VISIBLE : View.GONE);
        }
    }
}
