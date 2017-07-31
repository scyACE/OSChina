package com.wuxianedu.oschina.activity;

import android.os.Bundle;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.fragment.EventsFragment;
import com.wuxianedu.oschina.utils.Constants;


public class DynamicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTemplate = true;
        setContentView(0);

        int id = getIntent().getIntExtra(Constants.ID,0);
        String name = getIntent().getStringExtra(Constants.NAME);

        setTitleName(name);

        switchFragmentContent(EventsFragment.newInstance(id));
    }

}
