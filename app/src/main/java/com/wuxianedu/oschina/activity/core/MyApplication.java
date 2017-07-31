package com.wuxianedu.oschina.activity.core;

import com.wuxianedu.oschina.bean.User;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.activity.CoreApplication;
import com.wxhl.core.network.RequestManager;
import com.wxhl.core.utils.FileLocalCache;
import com.wxhl.core.utils.constants.CoreConstants;

/**
 *
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/21  15:05
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class MyApplication extends CoreApplication {

    public static User user = null;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络请求框架
        RequestManager.getInstance().init(this);
        if(user == null){
           user = (User) FileLocalCache.getSerializableData(CoreConstants.CACHE_DIR_SD, Constants.USER_FILE);
        }
    }

}
