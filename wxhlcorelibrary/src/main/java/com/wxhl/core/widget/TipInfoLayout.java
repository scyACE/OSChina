package com.wxhl.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.wxhl.core.R;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:29
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class TipInfoLayout extends FrameLayout {
    private ProgressWheel mPbProgressBar;
    private ImageView mTvTipState;
    private TextView mTvTipMsg;

    /**
     * 加载对话框   默认不隐藏
     */
    private View loading;
    private Context context;

    public TipInfoLayout(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public TipInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    /**
     * 将所有的信息加到 FrameLayout里
     * @param context
     */
    private void initView(Context context) {
        //设置背景 颜色
        setBackgroundColor(context.getResources().getColor(R.color.core_back));

        //三个参数的  获取布局参数
        loading = LayoutInflater.from(context).inflate(R.layout.core_tip_info_layout,this,false);
        mPbProgressBar = (ProgressWheel) loading.findViewById(R.id.tv_tip_loading);
        mTvTipState = (ImageView) loading.findViewById(R.id.tv_tip_state);
        mTvTipMsg = (TextView) loading.findViewById(R.id.tv_tip_msg);


//        setLoading();
//        completeLoading();

        //先于body 加入FrameLayout
        //// TODO: 2016/11/21   减少一层嵌套
        FrameLayout.LayoutParams layoutParams = (LayoutParams) loading.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;

        super.addView(loading);

        this.loading.setVisibility(View.GONE);
    }

    /**
     * 重新addView 方法 将Body加入到最底层  loading在最上层
     * @param child
     * @param index
     * @param params
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        //////////////////////////////////////////////////////////////
        // TODO: 2016年6月12日 0012  第一次添加的是  R.id.base_id  其他再添加都添加都 R.id.base_id得下一层
        if(child.getId() != R.id.base_id){   //
            index = getChildCount()-1;
        }
        super.addView(child,index,params);

    }

    /*public void setOnClick(OnClickListener onClik) {
        this.setOnClickListener(onClik);
    }*/

    /**
     * 显示加载
     */
    public void setLoading() {
        this.loading.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.VISIBLE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_loading));
    }

    /**
     * 完成加载  不再有加载更多的监听
     */
    public void completeLoading(){
        this.setOnClickListener(null);
        this.loading.setVisibility(View.GONE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
    }

    /**
     * 没有网络的提醒
     */
    public void setNetworkError() {
        this.loading.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_network);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_network_error));
    }

    /**
     * 提示信息
     * @param message
     */
    public void setFailureInfo(String message) {
        this.loading.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_loaderror);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(message);
    }

    //////////////////////////////////////////////////////////////
    // TODO: 2016年6月8日 0008  加载失败 给重新加载按钮
    public void setLoadError(OnClickListener onClickListener) {
        this.setOnClickListener(onClickListener);
        this.loading.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_loaderror);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_error_try_again));

    }
    public void setEmptyData(String message){
        this.loading.setVisibility(View.VISIBLE);
        this.setFailureInfo(message);
    }

    public void setEmptyData() {
        this.loading.setVisibility(View.VISIBLE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.VISIBLE);
        this.mTvTipState.setImageResource(R.drawable.core_page_icon_empty);
        this.mTvTipMsg.setVisibility(View.VISIBLE);
        this.mTvTipMsg.setText(context.getString(R.string.tip_load_empty));
    }

    /**
     * 不显示任何提示
     */
    public void setCancelShow(){
        this.loading.setVisibility(View.GONE);
        this.mPbProgressBar.setVisibility(View.GONE);
        this.mTvTipState.setVisibility(View.GONE);
        this.mTvTipMsg.setVisibility(View.GONE);
    }

    /**
     * 关闭 加载 wheel
     */
//    public void setLoadingMiss(){
//        if( mPbProgressBar.getVisibility() != View.GONE ) {
//            this.mPbProgressBar.setVisibility(View.GONE );
//            this.mTvTipMsg.setVisibility(View.GONE);
//        }
//    }
}
