package com.wuxianedu.oschina.network;

import android.content.Context;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wuxianedu.oschina.R;
import com.wxhl.core.network.NetWorkUtils;
import com.wxhl.core.network.RequestManager;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.SnackbarUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**网络请求
 *
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/22  20:24
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public abstract class RequestClient {

    private Context context;

    private RequestConfig config;

    private boolean isPost;
    private  String url;
    private  Map<String,String> params;
    public RequestClient(Context context, RequestConfig config){
        this.context = context;
        this.config = config;
    }

    /**
     * get请求
     * @param url
     */
    public void get(String url){
        request(false, url, null);
    }

    /**
     * get请求
     * @param url
     */
    public void get(String url, Map<String,String> params){
        request(false, url, params);
    }

    /**
     * post请求
     * @param url
     * @param params
     */
    public void post(String url, Map<String,String> params){
        request(true, url, params);
    }

    /**
     * 网络请求
     * @param isPost 是否post请求
     * @param url 请求url
     * @param params 请求参数
     */
    private void request(boolean isPost, String url, Map<String,String> params){

        this.isPost = isPost;
        this.url = url;
        this.params = params;

        //首先判断。如果不用显示body 直接隐藏
        if(config.isCover() && config.getMainBody()!=null){
            config.getMainBody().setVisibility(View.VISIBLE);
        }

        //判断是否有网络   如果没有网络且不覆盖 mainBody 使用SnackbarUtils 提示
        if(!NetWorkUtils.NETWORK ){
            if(config.isCover()){
                config.getTipInfoLayout().setNetworkError();
            }else {
                SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT, R.string.not_network,config.getTipInfoLayout());
            }
            return;
        }

        //// TODO: 2016/11/26  拼接url
        url = RequestAPI.getAbsoluteUrl(url);


        if(config.isCover() || config.isLoading()){ //如果隐藏body 肯定要loading
            //显示模拟进度对话框
            config.getTipInfoLayout().setLoading();
        }

        if(isPost){
            L.e("请求地址:url===="+url);
            RequestManager.getInstance().post(url,responseListener,errorListener,params);
        }else{
            // StringBuilder是用来组拼请求地址和参数
            if(params!=null) {
                StringBuilder sb = new StringBuilder();
                sb.append(url).append("?");
                if (params.size() != 0) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        // 如果请求参数中有中文，需要进行URLEncoder编码,gbk/utf8
                        try {
                            sb.append(entry.getKey()).append("=").append(URLEncoder.
                                encode(entry.getValue(), "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                url = sb.toString();
            }
            L.e("请求地址:url===="+url);
            RequestManager.getInstance().get(url, responseListener,errorListener);
        }

    }

    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String result) {
            //判断返回数据  如果为空 依然调用失败方法
            if(result != null){
                config.getMainBody().setVisibility(View.VISIBLE);
                config.getTipInfoLayout().completeLoading();
                loadSuccess(result);
            }else{
                loadError();
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
//            config.getMainBody().setVisibility(View.VISIBLE);
            loadError();
        }
    };

    /**
     * 失败处理
     */
    private void loadError(){
        config.getTipInfoLayout().completeLoading();
        if(config.isCover()){  //如果隐藏body 肯定要loading
            //加载失败  点击尝试 重新加载
            config.getTipInfoLayout().setLoadError(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request(isPost,url,params);
                }
            });
        }else{
            SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT, R.string.foot_state_error,config.getTipInfoLayout());
        }
        loadFail();
    }

    /**
     * 加载成功
     * @param result 返回数据
     */
    protected abstract void loadSuccess(String result);

    /**
     * 加载失败
     */
    protected abstract void loadFail();

}
