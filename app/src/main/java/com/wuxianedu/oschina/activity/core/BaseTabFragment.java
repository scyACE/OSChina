package com.wuxianedu.oschina.activity.core;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wuxianedu.oschina.R;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseTabFragment extends BaseFragment {

    List<Fragment> list;
    List<String> listTitle;
    @Override
    protected int getBodyView() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init(View root) {
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab_id);
        ViewPager pagerId = (ViewPager) root.findViewById(R.id.pager_id);

        list = new ArrayList<>();
        listTitle = new ArrayList<>();
        //初始化数据
        initList(list,listTitle);

        pagerId.setAdapter(new PagerAdapter());
        tabLayout.setupWithViewPager(pagerId);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter() {
            // TODO: 2016/11/28   有问题
//            super(getActivity().getSupportFragmentManager());
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
    }

    protected abstract void initList(List<Fragment> listFragment, List<String> listTitle);
}