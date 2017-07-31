package com.wxhl.core.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.wxhl.core.R;

/**
 * Snackbar工具类
 */
public class SnackbarUtils {

    public enum  EStyle {
        ALERT,    //警告
        WARNING,  //警告
        CONFIRM,  //确认
        INFO,    //信息
        DEFAULT
    }
    private static final int red = 0xfff44336;
    private static final int green = 0xff4caf50;
    private static final int blue = 0xff2195f3;
    private static final int orange = 0xffffc107;

    public static void  showMessage(EStyle style,int resId,View v){
        showMessage(style,v.getContext().getString(resId),v);
    }
    /**
     * 用Snackbar 显示提示信息  可提供 resId参数的
     */
    public static void  showMessage(EStyle style,String message,View v){
        int colorResId;
        switch (style){
            case ALERT:
                colorResId=red;
                break;
            case WARNING:
                colorResId=orange;
                break;
            case CONFIRM:
                colorResId=green;
                break;
            case INFO:
                colorResId=blue;
                break;
            default://获取主题的颜色
                colorResId = AttrUtil.getValueOfColorAttr(v.getContext(),R.attr.colorPrimary);
                break;
        }
        final Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        //设置显示文字的颜色
//        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(colorResId);
//        view.setBackgroundColor(AttrUtil.getValueOfColorAttr(this,R.styleable.Theme,R.styleable.Theme_colorPrimary));
        //设置Snackbar的背景颜色
        view.setBackgroundColor(colorResId);

        //设置按钮文字与事件
        snackbar.setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        //设置按钮文字的颜色
        snackbar.setActionTextColor(v.getContext().getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    /**
     * 显示默认颜色
     * @param message
     * @param v
     */
    public static void  showMessageDefault(String message,View v){
        showMessage(EStyle.DEFAULT,message,v);
    }

}
