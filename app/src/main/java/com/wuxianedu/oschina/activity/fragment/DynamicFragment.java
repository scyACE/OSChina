package com.wuxianedu.oschina.activity.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseFragment;
import com.wuxianedu.oschina.adapter.DynamicAdapter;
import com.wuxianedu.oschina.bean.EventVo;
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

public class DynamicFragment extends BaseFragment implements RefreshLayout.OnLoadListener,
        SwipeRefreshLayout.OnRefreshListener {

    private RefreshLayout refreshLayout;
    private RequestConfig config;
    private ListView listView;
    protected List<EventVo> list;
    private int page = 1;
    private DynamicAdapter adapter;
    private int id;

    public static DynamicFragment newInstance(int id) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getBodyView() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void init(View root) {

        id = getArguments().getInt(Constants.ID, 0);
        refreshLayout = (RefreshLayout) root.findViewById(R.id.refresh_id);

        config = new RequestConfig();
        config.setTipInfoLayout(mTipInfoLayout);
        listView = (ListView) root.findViewById(R.id.list_id);

        requestMessage(findSubFragment.LoadType.FIRST);
        //下拉刷新
        refreshLayout.setOnRefreshListener(this);

        //上拉加载
        refreshLayout.setOnLoadListener(this);
    }
    /*
     * 网络加载
     *
     * @param type
     */

    private void requestMessage(final findSubFragment.LoadType type) {

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
        new RequestClient(getContext(), config) {

            @Override
            protected void loadSuccess(String result) {

                list = JSONParseUtil.parseArray(result, EventVo.class);


                switch (type) {
                    case FIRST://第一次加载
                        if (list == null) {//list集合为空,加载失败,点击重新加载
                            mTipInfoLayout.setLoadError(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    requestMessage(findSubFragment.LoadType.FIRST);
                                }
                            });
                        }

                        if (list.size() == 0) {//list长度为0,暂无数据
                            mTipInfoLayout.setEmptyData();
                        }else {
                            page++;
                            adapter = new DynamicAdapter(getContext(), list, R.layout.item_dynamic);
                            listView.setAdapter(adapter);
                            if(list.size() > 0 && list.size() < 20){
                                refreshLayout.endPageData();
                            }
                        }

                        break;
                    case LOADMORE://加载更多
                        if (list == null) {//集合为空,加载失败
                            SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT, "加载失败", refreshLayout);
                        }
                        if (list.size() == 0) {//长度为0.加载到最后一条
                            refreshLayout.endPageData();
                        }
                        page++;
                        adapter.addAll(list);
                        refreshLayout.setLoading(false);
                        break;
                    case REFRESH://刷新
                        List<EventVo> listSub = new ArrayList<>();
                        if(list.size() != 0){
                            EventVo eventVo = adapter.getItem(0);

                            //伪增量更新
                            for (EventVo e : list) {
                                if (e.getId() != eventVo.getId()) {
                                    listSub.add(e);
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
                        }else{
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
        }.get("events/user/" + id, params);
    }

    @Override
    public void onRefresh() {
        requestMessage(findSubFragment.LoadType.REFRESH);
    }

    @Override
    public void onPageLoad() {
        requestMessage(findSubFragment.LoadType.LOADMORE);
    }
}
