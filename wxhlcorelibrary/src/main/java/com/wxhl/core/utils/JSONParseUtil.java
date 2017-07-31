package com.wxhl.core.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:27
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class JSONParseUtil {

    /**
     * 数组解析器
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        List<T> list = new ArrayList<T>(
                JSONArray.parseArray(json, clazz));
        return list;
    }


    /**
     * 对象解析器
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        return JSONObject.parseObject(json, clazz);
    }
}
