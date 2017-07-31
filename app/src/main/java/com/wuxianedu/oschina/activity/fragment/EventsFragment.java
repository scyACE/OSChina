package com.wuxianedu.oschina.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wuxianedu.oschina.activity.core.BaseTabFragment;
import com.wuxianedu.oschina.utils.Constants;

import java.util.List;

/**
 * Stay  hungry , Stay  foolish
 * .
 * Create by YuanDong Qiao
 * Create on 2016/11/30  11:04
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */

public class EventsFragment extends BaseTabFragment {


    public static EventsFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(Constants.ID,id);
        EventsFragment fragment = new EventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initList(List<Fragment> listFragment, List<String> listTitle) {
        int id =  getArguments().getInt(Constants.ID);

        listFragment.add(DynamicFragment.newInstance(id));
        listFragment.add(findSubFragment.newInstance(Constants.PROJECT,id));
        listFragment.add(findSubFragment.newInstance(Constants.STAR,id));
        listFragment.add(findSubFragment.newInstance(Constants.WATCH,id));

        listTitle.add("动态");
        listTitle.add("项目");
        listTitle.add("Star");
        listTitle.add("Watch");
    }
}
