package com.wxhl.core.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:26
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class AttrUtil {

    /**TODO: 2016/11/25  获取获取当前主题的颜色
     * color = AttrUtil.getValueOfColorAttr(getActivity(),R.attr.colorPrimary);
     * 获取主题的颜色
     * @param context
     * @param attrs
     * @return
     */
    public static int getValueOfColorAttr(Context context,int attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attrs});
		int color=a.getColor(0, 0);
        a.recycle();
        return color;
    }

}


