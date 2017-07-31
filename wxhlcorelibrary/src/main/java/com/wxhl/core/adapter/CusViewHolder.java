package com.wxhl.core.adapter;

import android.util.SparseArray;
import android.view.View;

public class CusViewHolder {

	/**
	 * 直接传入 convertView及 子条目控件id   拿到子条目控件直接 设置内容即可  无需再考虑ViewHolder
	 * @param view convertView
	 * @param id  子条目 控件id
	 * @param <T>
	 * @return
	 */
	public static <T extends View> T get(View view, int id) {
		//android 提供的类似 map集合
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag(); 
		if (viewHolder == null) { 
			viewHolder = new SparseArray<View>(); 
			view.setTag(viewHolder); 
		} 
		View childView = viewHolder.get(id); 
		if (childView == null) { 
			childView = view.findViewById(id); 
			viewHolder.put(id, childView); 
		} 
		return (T) childView; 
	} 
	
}
