package com.wuxianedu.oschina.activity.fragment;
//                .-~~~~~~~~~-._       _.-~~~~~~~~~-.
//            __.'              ~.   .~              `.__
//          .'//                  \./                  \\`.
//        .'//                     |                     \\`.
//      .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
//    .'//.-"                 `-.  |  .-'                 "-.\\`.
//  .'//______.============-..   \ | /   ..-============.______\\`.
//.'______________________________\|/______________________________`.

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseFragment;
import com.wuxianedu.oschina.activity.core.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shicunyu on 2016/11/28.
 */

public class findFragment extends BaseFragment {
    @Override
    protected int getBodyView() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init(View root) {

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tab_id);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.pager_id);

        viewPager.setAdapter(new PagerAdapter());

        tabLayout.setupWithViewPager(viewPager);


    }


    class PagerAdapter extends FragmentPagerAdapter{

        List<Fragment> list = new ArrayList<>();
        String[] titles = new String[]{"推荐项目","热门项目","最近更新"};

        public PagerAdapter() {
            super(getChildFragmentManager());
            list.add(findSubFragment.newInstance(0));
            list.add(findSubFragment.newInstance(1));
            list.add(findSubFragment.newInstance(2));

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
            return titles[position];
        }
    }
}
