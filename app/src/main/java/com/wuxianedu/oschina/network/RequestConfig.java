package com.wuxianedu.oschina.network;

import android.view.ViewGroup;

import com.wuxianedu.oschina.R;
import com.wxhl.core.widget.TipInfoLayout;

/**
 *
 *  网络请求参数配置
 *
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:24
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class RequestConfig {

    //是否隐藏mainBody显示加载进度, true隐藏,false不隐藏（只显示loading一块 类似对话框）
    private boolean isCover;

    //是否显示Loading (分页加载时  应不显示loading)
    private boolean isLoading;

    //提示信息
    private TipInfoLayout tipInfoLayout;

    //页面主体
    private ViewGroup mainBody ;

    //显示进度对话框  loading 已进化为 可以代替showProgressDialog
//    private boolean showProgressDialog;

    {
        this.isCover = true;
        this.isLoading = true;
//        this.showProgressDialog = false;
    }

    public boolean isCover() {
        return isCover;
    }

    public RequestConfig setIsCover(boolean isCover) {
        this.isCover = isCover;
        return this;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public RequestConfig setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
        return this;
    }

    public TipInfoLayout getTipInfoLayout() {
        return tipInfoLayout;
    }

    public RequestConfig setTipInfoLayout(TipInfoLayout tipInfoLayout) {
        this.tipInfoLayout = tipInfoLayout;
        mainBody = (ViewGroup) tipInfoLayout.findViewById(R.id.main_body_id);
        return this;
    }

    public ViewGroup getMainBody() {
        return mainBody ;
    }

//    public boolean isShowProgressDialog() {
//        return showProgressDialog;
//    }
//
//    public RequestConfig setShowProgressDialog(boolean showProgressDialog) {
//        this.showProgressDialog = showProgressDialog;
//        return this;
//    }

}
