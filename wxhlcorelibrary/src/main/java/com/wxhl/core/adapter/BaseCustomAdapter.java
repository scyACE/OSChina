package com.wxhl.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wxhl.core.utils.CollectionUtil;

import java.util.List;
/**
 *
 * Created by qiao yuandong.
 * 2015年10月26日,0026.
 * 所有BaseAdapter的直接父类
 * @param <E>
 */
public abstract class BaseCustomAdapter<E> extends BaseAdapter {

    protected LayoutInflater layoutInflater;

    protected List<E> data;

    protected int layoutResId; // item id

    public BaseCustomAdapter(Context context, List<E> data, int layoutResId) {
        this.layoutResId = layoutResId;
        this.data=data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<E> data){
        if(!CollectionUtil.listIsNull(this.data)){
            this.data.clear();
            this.data=null;
        }
        this.data=data;
        notifyDataSetChanged();
    }

    public void addAll(List<E> data){
        this.data.addAll(data);
        notifyDataSetChanged();

    }

    public void addAll(int location,List<E> data){
        this.data.addAll(location, data);
        notifyDataSetChanged();
    }

    public void add(E e){
        this.data.add(e);
        notifyDataSetChanged();
    }

    public void add(int location,E e){
        this.data.add(location, e);
        notifyDataSetChanged();
    }

    public void remove(int location){
        this.data.remove(location);
        notifyDataSetChanged();
    }

    public void remove(E e){
        this.data.remove(e);
        notifyDataSetChanged();
    }

    public void removeAll(List<E> data){
        this.data.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public E getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(layoutResId,null);
            getListener(convertView);
        }
        getCustomView(position, convertView);
        return convertView;
    }

    public abstract View getCustomView(int position, View convertView);

    /**
     * 如果需要点击事件  重写此方法
     */
    protected void getListener(View convertView){}
}