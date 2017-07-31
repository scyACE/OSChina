package com.wuxianedu.oschina.activity.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.MainActivity;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.CoreUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.widget.TipInfoLayout;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 * Create on 2016/11/21  9:42
 * Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 是否使用BaseActivity的模板，如果不适用模板 在setContentView（）之前设置为false
     */
    protected boolean mIsTemplate = true;

    /**
     * 封装了loading的布局  所有需要显示loading的布局必须 有此布局
     */
    protected TipInfoLayout mTipInfoLayout;

    /**
     * 记录上一个Fragment
     */
    protected Fragment mFragmentContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if((Integer) SharedPreferenceUtil.get(BaseActivity.this, Constants.THEME,0) != 0){
            setTheme((Integer) SharedPreferenceUtil.get(BaseActivity.this,Constants.THEME,0));
        }
        super.onCreate(savedInstanceState);
        //将所有开启的activity 加入到集合中
        CoreUtil.addAppActivity(this);

    }

    /**
     * 重写setContentView方法 如果子类使用模板  将子类的布局加入模板中。
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        if(mIsTemplate){
            super.setContentView(R.layout.base_activity);
            init(layoutResID);
        }else{
            super.setContentView(layoutResID);
        }
    }

    /**
     * 初始化
     */
    private void init(int layoutResID){
        //初始化
        mTipInfoLayout = findView(R.id.tip_layout_id);
        LinearLayout body = findView(R.id.main_body_id);
        Toolbar toolbar = findView(R.id.toolbar_id);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        //设置标题栏
        setSupportActionBar(toolbar);
        if(!(this instanceof MainActivity)){   //首页不需要返回键
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //返回键
        }

        //将子Activity视图加入到 body中
        if(layoutResID == 0){
            return;
        }
        View content = getLayoutInflater().inflate(layoutResID,null);
        body.removeAllViews();
        body.addView(content,new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
    }

    /**
     * 返回箭头和菜单的监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置标题
     */
    protected void setTitleName(CharSequence title){
        getSupportActionBar().setTitle(title);
    }
    protected void setTitleName(int resId){
        getSupportActionBar().setTitle(resId);
    }
    /**
     * 设置子标题
     * @param subtitleName
     */
    public void setSubTitleName(String subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }
    public void setSubTitleName(int subtitleName){
        getSupportActionBar().setSubtitle(subtitleName);
    }

    /**
     * 隐藏返回建
     */
    protected void hideBack(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 查找view
     *  
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    /**
     * 切换Fragment
     * @param to
     */
    protected void switchFragmentContent(Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mFragmentContent!=null){
            if (mFragmentContent != to) {
                if (!to.isAdded()) { // 先判断是否被add过
                    transaction.hide(mFragmentContent).add(R.id.tip_layout_id, to); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mFragmentContent).show(to); // 隐藏当前的fragment，显示下一个
                }
//				transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//	            transaction.replace(R.id.container, to); // 替换Fragment，实现切换
//	            transaction.commit();
            }
        }else{
            transaction.add(R.id.tip_layout_id, to);
        }
        transaction.commitAllowingStateLoss();  //推荐使用此方法，更安全，更方便
        /**
         * Can not perform this action after onSaveInstanceState
         * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
         * 再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行了，其效果是一样的。
         */
//        transaction.commit(); //不推荐，点击发现-->我的-->登录--点击左侧菜单，进入个人中心-->注销，报错，使用commitAllowingStateLoss没有问题
        mFragmentContent = to;
    }
}

