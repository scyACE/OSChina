package com.wxhl.core.widget.recycleview;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * 继承自SwipeRefreshLayout,
 * 从而实现滑动到底部时RecycleView上拉加载更多的功能.
 *
 */
public class RefreshLayoutForRecycleView extends SwipeRefreshLayout{

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;


    private RecyclerView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    /**
     * 是否完成分页加载
     */
    private boolean iscompleteLoading=false;
    // a
    private FooterAdapter adapter;
    /**
     * @param context
     */
    public RefreshLayoutForRecycleView(Context context) {
        this(context, null);
    }

    public RefreshLayoutForRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mListView = (RecyclerView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        // 滚动时到了最底部也可以加载更多
                        if (canLoad()) {
                            loadData();
                        }
                    }
                });
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        adapter = (FooterAdapter) mListView.getAdapter();
        if (adapter != null) {
            LinearLayoutManager manager = (LinearLayoutManager) mListView.getLayoutManager();
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            // TODO: 2015年11月4日,0004    -1
            int totalItemCount = manager.getItemCount();
            return lastVisibleItem == (totalItemCount-1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if(iscompleteLoading){   //完成加载，不需要再进行分页加载数据
            return;
        }
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onPageLoad();
        }
    }

    /**
     * 是否显示分页加载，true：显示，false：不显示
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            adapter.setFootVisible();
        } else {
            adapter.setFootGone();
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * 完成分页加载，没有更多数据了
     */
    public void completePageData() {
        iscompleteLoading=true;
        adapter.setNoMoreData();
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        //设置loading的颜色
//        setColorSchemeColors(getColorPrimary());
        mOnLoadListener = loadListener;
    }

//    public int getColorPrimary(){
//        return AttrUtil.getValueOfColorAttr(getContext(),
//                android.R.styleable.Theme, R.styleable.Theme_colorPrimary);
//    }

    /**
     * 加载更多的监听器,加载下一页
     *
     * @author mrsimple
     */
    public interface OnLoadListener {
        void onPageLoad();
    }
}