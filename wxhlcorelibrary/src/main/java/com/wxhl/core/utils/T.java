package com.wxhl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.wxhl.core.activity.CoreApplication;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:28
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class T {
	public static boolean isShow = true;


	public static void show(Object message) {
		if (isShow)
			Toast.makeText(CoreApplication.getInstance(), message.toString(), Toast.LENGTH_SHORT).show();
	}
	/**
	 * 短时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void show(Context context,Object message) {
		if (isShow)
			Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
	}
	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration) {
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration) {
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * @Description: 显示Toast消息,当在非UI线程中需要显示消息时调用此方法
	 * @param activity
	 * @param message
	 */
	public static void showToastMsgOnUiThread(final Activity activity,final String message){
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
