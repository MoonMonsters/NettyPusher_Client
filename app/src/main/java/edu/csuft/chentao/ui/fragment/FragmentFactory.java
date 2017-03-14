package edu.csuft.chentao.ui.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chalmers on 2016-12-21 21:41.
 * email:qxinhai@yeah.net
 */

public class FragmentFactory {

    public final static int CHATTING_LIST_FRAGMENT = 0;
    public final static int GROUP_LIST_FRAGMENT = 1;
    public final static int MINE_FRAGMENT = 2;

    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public static Fragment getInstance(int type) {
        Fragment fragment = fragmentMap.get(type);
        if (fragment == null) {
            switch (type) {
                case CHATTING_LIST_FRAGMENT:
                    fragment = new ChattingListFragment();
                    break;
                case GROUP_LIST_FRAGMENT:
                    fragment = new GroupListFragment();
                    break;
                case MINE_FRAGMENT:
                    fragment = new MineFragment();
                    break;
            }

            fragmentMap.put(type, fragment);
        }

        return fragment;
    }

}
