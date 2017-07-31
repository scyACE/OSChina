package com.wxhl.core.widget.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wxhl.core.R;


/**
 * Created by qiao yuandong.
 * 2015年11月4日,0004.
 */
//用的时候要继承此抽象类，然后实现里面的几个抽象方法即可。
public abstract class FooterAdapter extends BaseAnimAdapter{

    private static final int TYPE_FOOTER = Integer.MIN_VALUE;
    private static final int TYPE_ADAPTEE_OFFSET = 2;
    private View footer;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateContentItemViewHolder(parent, viewType - TYPE_ADAPTEE_OFFSET);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == getContentItemCount() && holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        } else {
            onBindContentItemView(holder, position);
        }
    }

    private class ViewHolderFooter extends RecyclerView.ViewHolder {
        public ProgressBar pro;
        public TextView title;

        public ViewHolderFooter(View v) {
            super(v);
            pro = (ProgressBar) v.findViewById(R.id.pro);
            title = (TextView) v.findViewById(R.id.footerTitle);
        }
    }

    /**
     * 完成加载
     */
    public void setFootGone(){
        if(footer != null) {
            footer.setVisibility(View.GONE);
        }
    }

    /**
     * 加载
     */
    public void setFootVisible(){
        if(footer != null){
            footer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 没有更多
     */
    public void setNoMoreData(){
        if(footer != null){
            ProgressBar pro = (ProgressBar) footer.findViewById(R.id.pro);
            TextView title = (TextView) footer.findViewById(R.id.footerTitle);
            pro.setVisibility(View.GONE);
            title.setText("没有更多数据了");
            footer.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public int getItemCount() {
        int itemCount = getContentItemCount();
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getContentItemCount() && useFooter()) {
            return TYPE_FOOTER;
        }
        return getContentItemType(position) + TYPE_ADAPTEE_OFFSET;
    }
    public abstract boolean useFooter();

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType){
        footer = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.footerview, parent, false);
        ViewHolderFooter vh = new ViewHolderFooter(footer);
        return vh;
    }

    public void onBindFooterView(RecyclerView.ViewHolder holder, int position){
        ViewHolderFooter footerHolder = (ViewHolderFooter) holder;
    }

    public abstract RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int viewType);//创建你要的普通item

    public abstract void onBindContentItemView(RecyclerView.ViewHolder holder, int position);//绑定数据

    public abstract int getContentItemCount();

    public abstract int getContentItemType(int position);//没用到，返回0即可，为了扩展用的


}