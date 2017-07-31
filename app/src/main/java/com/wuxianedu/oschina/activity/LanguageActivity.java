package com.wuxianedu.oschina.activity;

import android.os.Bundle;

import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.fragment.findSubFragment;
import com.wuxianedu.oschina.bean.Languange;
import com.wuxianedu.oschina.utils.Constants;

public class LanguageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(0);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        Languange language = (Languange) getIntent().getSerializableExtra(Constants.LAN);
        setTitleName(language.getName());
        switchFragmentContent(new findSubFragment().newInstance(Constants.LANGUANGE,language.getId()));
    }
}
