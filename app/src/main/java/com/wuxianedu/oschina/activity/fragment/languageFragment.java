package com.wuxianedu.oschina.activity.fragment;
//                .-~~~~~~~~~-._       _.-~~~~~~~~~-.
//            __.'              ~.   .~              `.__
//          .'//                  \./                  \\`.
//        .'//                     |                     \\`.
//      .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
//    .'//.-"                 `-.  |  .-'                 "-.\\`.
//  .'//______.============-..   \ | /   ..-============.______\\`.
//.'______________________________\|/______________________________`.

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.LanguageActivity;
import com.wuxianedu.oschina.activity.core.BaseFragment;
import com.wuxianedu.oschina.adapter.LanguageAdapter;
import com.wuxianedu.oschina.bean.Languange;
import com.wuxianedu.oschina.network.RequestAPI;
import com.wuxianedu.oschina.network.RequestClient;
import com.wuxianedu.oschina.network.RequestConfig;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.JSONParseUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;

import java.util.List;

/**
 * Created by shicunyu on 2016/11/28.
 */

public class languageFragment extends BaseFragment{

    private List<Languange> list;
    private OrderBroadcastReceiver receiver;
    private RecyclerView recycleView;

    @Override
    protected int getBodyView() {
        return R.layout.fragmet_language;
    }

    @Override
    protected void init(View root) {

        //排序方式
        receiver = new OrderBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.my.order");
        mContext.registerReceiver(receiver, intentFilter);


//
//        //下拉刷新
//        refreshLayout.setOnRefreshListener(this);
//
//        //上拉加载
//        refreshLayout.setOnLoadListener(this);
        //设置recycleview
        recycleView = (RecyclerView) root.findViewById(R.id.recycleview_id);

        boolean a = (Boolean) SharedPreferenceUtil.get(mContext, Constants.ORDER,true);
        //设置卡片方式
        if( a ){
            recycleView.setLayoutManager(new GridLayoutManager(mContext,2));
        }else{
            recycleView.setLayoutManager(new GridLayoutManager(mContext,1));
        }

        RequestConfig config = new RequestConfig();
        config.setTipInfoLayout(mTipInfoLayout);

        new RequestClient(getContext(), config){

            @Override
            protected void loadSuccess(String result) {
                list = JSONParseUtil.parseArray(result,Languange.class);
                LanguageAdapter adapter =  new LanguageAdapter(list);
                recycleView.setAdapter(adapter);
                adapter.setOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, LanguageActivity.class);
                        Languange languange = list.get((Integer) view.getTag());
                        intent.putExtra(Constants.LAN,languange);
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected void loadFail() {

            }
        }.get(RequestAPI.LANGUAGE);



    }


    /**
     * 改变排列方式
     */

    class OrderBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if((boolean)SharedPreferenceUtil.get(mContext,Constants.ORDER,true)){
                recycleView.setLayoutManager(new GridLayoutManager(mContext,2));
            }else{
                recycleView.setLayoutManager(new GridLayoutManager(mContext,1));
            }
        }
    }
}
