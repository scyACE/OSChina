package com.wxhl.core.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wxhl.core.utils.TextUtil;

import java.util.Map;
/**
 * 网络请求管理类
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/21  9:44
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class RequestManager {

    private static RequestManager requestManager = null;

    private static RequestQueue requestQueue = null;

    private RequestManager(){}

    /**
     * 初始化请求队列
     * @param context
     */
    public void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestManager getInstance(){
        if (requestManager == null) {
            synchronized (RequestManager.class) {
                if (requestManager == null) {
                    requestManager = new RequestManager();
                }
            }
        }
        return requestManager;
    }

    /**
     * 添加Request到队列
     * @param request
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request){
        requestQueue.add(request);
    }

    /**
     * 添加Request到队列【带标签】
     * @param request
     * @param tag
     * @param <T>
     */
    private <T> void addRequestQueue(Request<T> request,String tag){
        if(!TextUtil.stringIsNull(tag)){
            request.setTag(tag);
        }
        addRequestQueue(request);
    }

    /**
     * get请求
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public Request get(String url, Response.Listener<String> listener,
           Response.ErrorListener errorListener){
        StringRequest stringRequest = new StringRequest(url,listener,errorListener);
        addRequestQueue(stringRequest);
        return stringRequest;
    }

    /**
     * post请求
     * @param url
     * @param listener
     * @param errorListener
     * @return
     */
    public Request post(String url, Response.Listener<String> listener,
               Response.ErrorListener errorListener, Map<String, String> map){
        CustomStringRequest stringRequest = new CustomStringRequest(Request.Method.POST, url,
            listener, errorListener, map);
        addRequestQueue(stringRequest);
        return stringRequest;
    }

    /**
     * 根据标签取消请求
     * @param tag
     */
    public void cancelRequest(String tag){
        requestQueue.cancelAll(tag);
    }

}
