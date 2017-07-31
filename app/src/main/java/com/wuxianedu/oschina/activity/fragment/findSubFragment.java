package com.wuxianedu.oschina.activity.fragment;
//                .-~~~~~~~~~-._       _.-~~~~~~~~~-.
//            __.'              ~.   .~              `.__
//          .'//                  \./                  \\`.
//        .'//                     |                     \\`.
//      .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
//    .'//.-"                 `-.  |  .-'                 "-.\\`.
//  .'//______.============-..   \ | /   ..-============.______\\`.
//.'______________________________\|/______________________________`.

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.DetailActivity;
import com.wuxianedu.oschina.activity.DynamicActivity;
import com.wuxianedu.oschina.activity.core.BaseFragment;
import com.wuxianedu.oschina.activity.core.MyApplication;
import com.wuxianedu.oschina.adapter.FeaturedAdapter;
import com.wuxianedu.oschina.bean.Featured;
import com.wuxianedu.oschina.network.RequestAPI;
import com.wuxianedu.oschina.network.RequestClient;
import com.wuxianedu.oschina.network.RequestConfig;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.CollectionUtil;
import com.wxhl.core.utils.JSONParseUtil;
import com.wxhl.core.utils.SnackbarUtils;
import com.wxhl.core.utils.T;
import com.wxhl.core.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shicunyu on 2016/11/28.
 */

public class findSubFragment extends BaseFragment implements RefreshLayout.OnLoadListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RequestConfig config;
    private List<Featured> list;
    private ListView listView;
    private int position;
    private FeaturedAdapter adapter;
    private int page = 1;
    private RefreshLayout refreshLayout;
    private int id;
    List<Featured> lastList = new ArrayList<Featured>();

    public static findSubFragment newInstance(int position) {
        findSubFragment fragment = new findSubFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public static findSubFragment newInstance(int position, int id) {
        findSubFragment fragment = new findSubFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt(Constants.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 在oncreateview之前调用,取消预加载
     *
     * @param isVisibleToUser 可见视图时为true,不可见时为false
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //1.是否可见  2. 布局文件  3.是否第一次加载
        if (getUserVisibleHint() && mTipInfoLayout != null && adapter == null) {
            requestMessage(LoadType.FIRST);
        }
    }

    /**
     * 加载第一个视图时回调
     */
    @Override
    public void onStart() {
        super.onStart();
        setUserVisibleHint(getUserVisibleHint());
    }

    @Override
    protected int getBodyView() {
        return R.layout.fragment_find_sub;
    }

    @Override
    protected void init(View root) {

        config = new RequestConfig();
        config.setTipInfoLayout(mTipInfoLayout);

        refreshLayout = (RefreshLayout) root.findViewById(R.id.refresh_id);

        listView = (ListView) root.findViewById(R.id.list_id);


        position = getArguments().getInt("position");
        id = getArguments().getInt(Constants.ID);

        //下拉刷新
        refreshLayout.setOnRefreshListener(this);

        //上拉加载
        refreshLayout.setOnLoadListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Constants.FEATURED_, list.get(i));
                startActivity(intent);
            }
        });

    }


    /**
     * 网络加载
     *
     * @param type
     */
    private void requestMessage(final LoadType type) {

        Map<String, String> params = new HashMap<>();

        params.put("page", String.valueOf(page));
        switch (type) {
            case FIRST://第一次加载

                break;
            case LOADMORE://上拉加载
                config.setIsCover(false);
                config.setIsLoading(false);
                break;
            case REFRESH://下拉刷新
                config.setIsCover(false);
                config.setIsLoading(false);
                params.put("page", String.valueOf(1));

                break;
        }


        //获取数据
        new RequestClient(mContext, config) {

            @Override
            protected void loadSuccess(String result) {


//                list = JSONParseUtil.parseArray(result, Featured.class);
                lastList = JSONParseUtil.parseArray(result, Featured.class);


                switch (type) {
                    case FIRST://第一次加载
                        list = lastList;
                        Log.e("--main--", list.size() + "");
                        if (list == null) {//list集合为空,加载失败,点击重新加载
                            mTipInfoLayout.setLoadError(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    requestMessage(LoadType.FIRST);
                                }
                            });
                        }

                        if (list.size() == 0) {//list长度为0,暂无数据
                            mTipInfoLayout.setEmptyData();
                            refreshLayout.setOnLoadListener(null);
                        } else {
                            page++;
                            adapter = new FeaturedAdapter(mContext, list, R.layout.item_find, id);
                            listView.setAdapter(adapter);
                            if (list.size() < 20 && list.size() > 0) {
                                refreshLayout.endPageData();
                            }
                        }


                        break;
                    case LOADMORE://加载更多
                        if (lastList == null) {//集合为空,加载失败
                            SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT, "加载失败", refreshLayout);
                            return;
                        }
                        if (lastList.size() == 0) {//长度为0.加载到最后一条
                            refreshLayout.endPageData();
                            return;
                        }
                        page++;
                        adapter.addAll(lastList);
                        refreshLayout.setLoading(false);
                        break;
                    case REFRESH://刷新

                        List<Featured> listSub = new ArrayList<>();

                        if (list == null) {
                            refreshLayout.setRefreshing(false);
                            return;
                        }
                        if (list.size() == 0) {
                            mTipInfoLayout.setEmptyData();
                            refreshLayout.setRefreshing(false);
                            return;
                        }
                        if (list.size() != 0) {
                            Featured fFirst = adapter.getItem(0);

                            //伪增量更新
                            for (Featured f : list) {
                                if (f.getId() != fFirst.getId()) {
                                    listSub.add(f);
                                } else {
                                    break;
                                }
                            }
                            //有没有数据更新
                            if (CollectionUtil.listIsNull(listSub)) {
                                SnackbarUtils.showMessageDefault("暂无更新", mTipInfoLayout);
                            } else {
                                adapter.addAll(0, listSub);
                                SnackbarUtils.showMessageDefault("更新了" + listSub.size() + "条数据", mTipInfoLayout);
                            }
                            refreshLayout.setRefreshing(false);
                        } else {
                            //有没有数据更新
                            if (CollectionUtil.listIsNull(listSub)) {
                                SnackbarUtils.showMessageDefault("暂无更新", mTipInfoLayout);
                            } else {
                                adapter.addAll(0, listSub);
                                SnackbarUtils.showMessageDefault("更新了" + listSub.size() + "条数据", mTipInfoLayout);
                            }
                            refreshLayout.setRefreshing(false);
                        }


                        break;
                }

            }

            @Override
            protected void loadFail() {
                T.show("失败");
            }
        }.get(getUrl(), params);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

        requestMessage(LoadType.REFRESH);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onPageLoad() {
        requestMessage(LoadType.LOADMORE);
    }

    enum LoadType {
        FIRST,//第一次加载
        LOADMORE,//加载更多
        REFRESH  // 刷新
    }


    private String getUrl() {
        switch (position) { //根据类型 获取不同的url
            case Constants.FEATURED:  //推荐
                return RequestAPI.FEATURED;
            case Constants.POPULAR:  //热门
                return RequestAPI.POPULAR;
            case Constants.LATEST:  //最近更新
                return RequestAPI.LATEST;
            case Constants.PROJECT:  //项目
                return "user/" + id + "/projects";
            case Constants.STAR:  //star
                return "user/" + id + "/stared_projects";
            case Constants.WATCH:  //watch
                return "user/" + id + "/watched_projects";
            case Constants.LANGUANGE:  //Languange
                return RequestAPI.LANGUAGE + "/" + id;
            default:
                return RequestAPI.FEATURED;
        }
    }
}
