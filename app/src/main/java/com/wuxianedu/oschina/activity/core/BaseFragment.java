package com.wuxianedu.oschina.activity.core;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.wuxianedu.oschina.R;
import com.wxhl.core.widget.TipInfoLayout;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:24
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public abstract class BaseFragment extends Fragment {


    //root根视图
    private View root;
    protected TipInfoLayout mTipInfoLayout;
    protected Context mContext;
    private final String ISHIDDEN = "isHidden";


    //视图销毁时,保存状态
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ISHIDDEN,isHidden());
        super.onSaveInstanceState(outState);
    }

    /**
     * 解决切换fragment重影
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){//从恢复中来
            if(savedInstanceState.getBoolean(ISHIDDEN)){
                getFragmentManager().beginTransaction().hide(this);
            }else {
                getFragmentManager().beginTransaction().show(this);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if(root == null){
            root = inflater.inflate(R.layout.base_fragment,container,false);
            mTipInfoLayout = (TipInfoLayout) root.findViewById(R.id.tip_layout_id);
            LinearLayout body = (LinearLayout) root.findViewById(R.id.main_body_id);
            //将子Fragment视图加入到 body中
            View content = inflater.inflate(getBodyView(),null);
            body.removeAllViews();
            body.addView(content,new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));
            init(content);
        }
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * 获取子fragment绑定的视图
     * @return
     */
    protected abstract int getBodyView();

    /**
     * 初始化
     */
    protected abstract void init(View root);
}
