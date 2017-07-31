package com.wuxianedu.oschina.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.wuxianedu.oschina.activity.core.MyApplication;

/**
 * 字体工具类 将指定文字转化为图标
 */
public class TypefaceUtils {

    private static Typeface typeface;

    public static Typeface getTypeface() {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(MyApplication.getInstance().getAssets(), "icons.ttf");
        }
        return typeface;
    }

    public static void setIconText(TextView view, String text) {
        view.setText(text);
        view.setTypeface(TypefaceUtils.getTypeface());
    }

}
