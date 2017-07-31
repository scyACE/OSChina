package com.wuxianedu.oschina.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.core.MyApplication;
import com.wuxianedu.oschina.activity.fragment.EventsFragment;
import com.wuxianedu.oschina.activity.fragment.findFragment;
import com.wuxianedu.oschina.activity.fragment.languageFragment;
import com.wuxianedu.oschina.activity.fragment.shakeFragment;
import com.wuxianedu.oschina.bean.User;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.utils.CoreUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.utils.T;

import java.lang.reflect.Method;

/**
 * Stay  hungry , Stay  foolish
 *
 * @author YuanDong Qiao
 *         Create on 2016/11/22  20:24
 *         Copyright(c) 2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ImageView head;//头像
    private TextView name; //名字
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Fragment[] fragments = new Fragment[4]; //四个fragment
    private MenuItem item;
    private boolean isClickItem; //是否点击条目
    private boolean isClickHead; //是否点击头像
    private MyReceiver myReceiver;
    private FloatingActionButton red_btn; //点击设置红色主题
    private FloatingActionButton brown_btn;//点击设置棕色主题
    private FloatingActionButton blue_btn;//点击设置蓝色主题
    private FloatingActionButton blue_grey_btn;//点击设置蓝灰色主题
    private FloatingActionButton yellow_btn;//点击设置黄色主题
    private FloatingActionButton deep_purple_btn;//点击设置深紫色主题
    private FloatingActionButton pink_btn;//点击设置粉色主题
    private FloatingActionButton green_btn;//点击设置绿色主题
    private AlertDialog dialog;
    private Toolbar toolbar;
//    private ChangeLocal changeLocal;
//    private MyReceiver2 myReceiver2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //不使用模板
        mIsTemplate = false;
        setContentView(R.layout.activity_main);


        //更改头像广播,更改drawer方向
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter("com.scy.MyReceiver");
        registerReceiver(myReceiver, intentFilter);

//        //更改drawer选中广播
//        myReceiver2 = new MyReceiver2();
//        IntentFilter intentFilter2 = new IntentFilter("com.scy.MyReceiver2");
//        registerReceiver(myReceiver2, intentFilter2);

        fragments[0] = new findFragment();
        fragments[2] = new languageFragment();
        fragments[3] = new shakeFragment();


        init();

        //改变抽屉方向
        if((boolean)SharedPreferenceUtil.get(MainActivity.this,Constants.SWITCH,false)){
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
            layoutParams.gravity = Gravity.END;
            navigationView.setLayoutParams(layoutParams);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                        drawerLayout.closeDrawer(GravityCompat.END);
                    }else{
                        drawerLayout.openDrawer(GravityCompat.END);
                    }
                }
            });

        }else{
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
            layoutParams.gravity = Gravity.START;
            navigationView.setLayoutParams(layoutParams);

        }

//        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
//        params.gravity = Gravity.RIGHT;



    }


    //更改drawer中子条目
//    class MyReceiver2 extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            drawerLayout.closeDrawers();
//            navigationView.setCheckedItem(R.id.menu_explore);
//            switchFragmentContent(mFragmentContent);
//            setTitleName("发现");
//        }
//    }

    //设置头像,设置抽屉方向
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //设置头像
            head.setImageResource(R.mipmap.mini_avatar);
            name.setText("点击登录");
            if (MyApplication.user != null) {

                User user = MyApplication.user;
                ImageLoaderFactory.getImageLoader().loadImage(user.getNew_portrait(), head);
                name.setText(user.getName());
            }


            //改变抽屉方向
            if((boolean)SharedPreferenceUtil.get(MainActivity.this,Constants.SWITCH,false)){
                DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
                layoutParams.gravity = Gravity.END;
                navigationView.setLayoutParams(layoutParams);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                            drawerLayout.closeDrawer(GravityCompat.END);
                        }else{
                            drawerLayout.openDrawer(GravityCompat.END);
                        }
                    }
                });

            }else{
                DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
                layoutParams.gravity = Gravity.START;
                navigationView.setLayoutParams(layoutParams);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }else{
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                });
            }

        }
    }



    /**
     * 改变drawer方向
     */
//    class ChangeLocal extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
////            if (intent.getBooleanExtra(Constants.SWITCH, false)) {
////                DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) drawerLayout.getLayoutParams();
////                layoutParams.gravity = Gravity.END;
////                drawerLayout.setLayoutParams(layoutParams);
////            } else {
////                DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) drawerLayout.getLayoutParams();
////                layoutParams.gravity = Gravity.START;
////                drawerLayout.setLayoutParams(layoutParams);
////            }
////            recreate();
//        }
//    }

    /**
     * 初始化
     */
    private void init() {

        //设置Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        toolbar.setTitle("发现");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        drawerLayout = findView(R.id.drawer_id);

        navigationView = findView(R.id.navigation_id);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {//在drawer关闭之后再执行
                super.onDrawerClosed(drawerView);

                if (isClickItem) {
                    clickItem();
                    isClickItem = false;
                }
            }
        };

        drawerLayout.setDrawerListener(toggle);

        //同步
        toggle.syncState();

        //显示发现页面
        switchFragmentContent(new findFragment());

        //头视图点击事件
        View headView = navigationView.getHeaderView(0);


        head = (ImageView) headView.findViewById(R.id.drawer_head);
        name = (TextView) headView.findViewById(R.id.login);

        final Intent intent = new Intent();
        intent.setAction("com.scy.MyReceiver");
        sendBroadcast(intent);

        headView.findViewById(R.id.drawer_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isClickItem = true;
                isClickHead = true;
                drawerLayout.closeDrawers();

            }
        });

        //隐藏滚动条
        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }

        //侧滑栏选中事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                isClickItem = true;
                MainActivity.this.item = item;

                switch (item.getItemId()) {
                    case R.id.menu_explore://发现
                        setTitleName("发现");
                        break;
                    case R.id.menu_mine://我的
                        if (MyApplication.user != null) {
                            setTitleName("我的");
                        } else {
                            drawerLayout.closeDrawers();
                            return false;
                        }
                        break;
                    case R.id.menu_language://语言
                        setTitleName("语言");
                        break;
                    case R.id.menu_shake://摇一摇
                        setTitleName("摇一摇");
                        break;
                    case R.id.menu_change_theme://改变主题

                        break;
                    case R.id.menu_setting://设置

                        break;
                }

                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting_id:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 通过反射  显示菜单图标
     */

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    /**
     * 点击drawer中子条目触发
     */
    private void clickItem() {

        if (isClickHead) {
            clickHead();
            isClickHead = false;
            return;
        }


        switch (item.getItemId()) {
            case R.id.menu_explore://发现
                switchFragmentContent(fragments[0]);
                break;
            case R.id.menu_mine://我的
                if (MyApplication.user != null) {
                    if (fragments[1] == null) {
                        fragments[1] = EventsFragment.newInstance(MyApplication.user.getId());
                    }
                    switchFragmentContent(fragments[1]);
                } else {
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    intent1.putExtra(Constants.IS_FROM_ME, true);
                    startActivityForResult(intent1, 100);
                    drawerLayout.closeDrawers();
//                    return false;
                }
                break;
            case R.id.menu_language://语言
                switchFragmentContent(fragments[2]);
                break;
            case R.id.menu_shake://摇一摇
                switchFragmentContent(fragments[3]);
                break;
            case R.id.menu_change_theme://改变主题
                showDialog();
                switch ((Integer) SharedPreferenceUtil.get(MainActivity.this, Constants.THEME, 0)) {
                    case R.style.RedTheme:
                        red_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.BrownTheme:
                        brown_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.BlueTheme:
                        blue_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.BlueGreyTheme:
                        blue_grey_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.YellowTheme:
                        yellow_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.DeepPurpleTheme:
                        deep_purple_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.PinkTheme:
                        pink_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case R.style.GreenTheme:
                        green_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                    case 0:
                        blue_btn.setImageDrawable(getResources().getDrawable(R.mipmap.selected));
                        break;
                }
                break;
            case R.id.menu_setting://设置
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

    }

    /**
     * 点击头像时触发
     */
    private void clickHead() {
        if (MyApplication.user != null) {
            startActivity(new Intent(MainActivity.this, UserInforActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            switchFragmentContent(EventsFragment.newInstance(MyApplication.user.getId()));
            setTitleName("我的");
            navigationView.setCheckedItem(R.id.menu_mine);
        } else {
            if (mFragmentContent instanceof findFragment) {
                setTitleName("发现");
                navigationView.setCheckedItem(R.id.menu_explore);
            }
            if (mFragmentContent instanceof languageFragment) {
                setTitleName("语言");
                navigationView.setCheckedItem(R.id.menu_language);
            }
            if (mFragmentContent instanceof shakeFragment) {
                setTitleName("摇一摇");
                navigationView.setCheckedItem(R.id.menu_shake);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private long firstTime, secondTime;

    /**
     * 点击返回键的时候的判断
     */
    @Override
    public void onBackPressed() {
        //如果抽屉打开,就关闭抽屉
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (!(mFragmentContent instanceof findFragment)) {//如果不是发现页面,跳转到发现
            switchFragmentContent(fragments[0]);
            navigationView.setCheckedItem(R.id.menu_explore);
            setTitleName("发现");
            return;
        } else {//退出程序
            secondTime = System.currentTimeMillis();
            if (secondTime - firstTime < 2000) {
                finish();
                CoreUtil.exitApp(this);
            } else {
                firstTime = System.currentTimeMillis();
                T.show("再按一次退出");
            }
        }
    }

    /**
     * 显示颜色对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("改变主题");
        TableLayout tableLayout = (TableLayout) LayoutInflater.from(this).inflate(R.layout.dialog_change_color, null);
        builder.setView(tableLayout);
        dialog = builder.show();

        red_btn = (FloatingActionButton) tableLayout.findViewById(R.id.red_btn);
        brown_btn = (FloatingActionButton) tableLayout.findViewById(R.id.brown_btn);
        blue_btn = (FloatingActionButton) tableLayout.findViewById(R.id.blue_btn);
        blue_grey_btn = (FloatingActionButton) tableLayout.findViewById(R.id.blue_grey_btn);
        yellow_btn = (FloatingActionButton) tableLayout.findViewById(R.id.yellow_btn);
        deep_purple_btn = (FloatingActionButton) tableLayout.findViewById(R.id.deep_purple_btn);
        pink_btn = (FloatingActionButton) tableLayout.findViewById(R.id.pink_btn);
        green_btn = (FloatingActionButton) tableLayout.findViewById(R.id.green_btn);

        red_btn.setOnClickListener(this);
        brown_btn.setOnClickListener(this);
        blue_btn.setOnClickListener(this);
        blue_grey_btn.setOnClickListener(this);
        yellow_btn.setOnClickListener(this);
        deep_purple_btn.setOnClickListener(this);
        pink_btn.setOnClickListener(this);
        green_btn.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.RedTheme);
                recreate();
                break;
            case R.id.brown_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.BrownTheme);
                recreate();
                break;
            case R.id.blue_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.BlueTheme);
                recreate();
                break;
            case R.id.blue_grey_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.BlueGreyTheme);
                recreate();
                break;
            case R.id.yellow_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.YellowTheme);
                recreate();
                break;
            case R.id.deep_purple_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.DeepPurpleTheme);
                recreate();
                break;
            case R.id.pink_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.PinkTheme);
                recreate();
                break;
            case R.id.green_btn:
                SharedPreferenceUtil.put(MainActivity.this, Constants.THEME, R.style.GreenTheme);
                recreate();
                break;
        }
        dialog.dismiss();

    }

}
