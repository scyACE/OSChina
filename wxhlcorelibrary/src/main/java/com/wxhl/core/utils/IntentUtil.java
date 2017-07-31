package com.wxhl.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wxhl.core.R;
import com.wxhl.core.network.NetWorkUtils;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:27
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class IntentUtil {

	public static void startActivity(Context context,Class<?> cls){
		context.startActivity(new Intent(context, cls));
	}

	public static void startActivity(Context context,Intent intent){
		context.startActivity(intent);
	}
	
	public static void startActivity(Context context,Class<?> cls,Intent intent){
		context.startActivity(intent.setClass(context, cls));
	}
	
	public static void startActivityForResult(Activity activity,Intent intent,int requestCode){
		activity.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param cls
	 */
	public static void startActivityNetWork(Context context,Class<?> cls){
		if(!NetWorkUtils.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, cls);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param intent
	 */
	public static void startActivityNetWork(Context context,Intent intent){
		if(!NetWorkUtils.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, intent);
	}
	
	/**
	 * @Description: 跳转前判断网络
	 * @param context
	 * @param intent
	 */
	public static void startActivityNetWork(Context context,Class<?> cls,Intent intent){
		if(!NetWorkUtils.NETWORK){
			T.showLong(context, context.getString(R.string.not_network));
			return;
		}
		startActivity(context, cls, intent);
	}
	
	/**
	 * @Description: 跳转前判断网络,有返回值
	 * @param activity
	 * @param intent
	 * @param requestCode
	 */
	public static void startActivityForResultNetWork(Activity activity,Intent intent,int requestCode){
		if(!NetWorkUtils.NETWORK){
			T.showLong(activity, activity.getString(R.string.not_network));
			return;
		}
		startActivityForResult(activity, intent, requestCode);
	}
	
	/**
	 * @Description: 返回主页
	 * @param activity
	 * @param cls
	 */
	public static void goHome(Activity activity,Class<?> cls){
		Intent intent=new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(activity, cls, intent);
		activity.finish();
	}
	
}
