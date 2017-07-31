package com.wxhl.core.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * 重写getParams() 提供post请求的参数
 *
 * Stay  hungry , Stay  foolish
 * .
 * Create by YuanDong Qiao
 * Create on 2016/11/21  10:04
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */

public class CustomStringRequest extends StringRequest {

    private Map<String,String> map;

    public CustomStringRequest(int method, String url
            , Response.Listener<String> listener
            , Response.ErrorListener errorListener
            , Map<String,String> map) {
        super(method, url, listener, errorListener);
        this.map = map;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
