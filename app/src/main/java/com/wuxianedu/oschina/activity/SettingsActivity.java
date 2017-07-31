package com.wuxianedu.oschina.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.fragment.SettingFragment;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.FileUtil;
import com.wxhl.core.utils.SharedPreferenceUtil;

public class SettingsActivity extends BaseActivity{

    private Switch sw;
    private CheckBox checkBox;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTemplate = true;
        /**
         * 第一次启动时
         */

        setContentView(0);

//        setTitleName("设置");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_body_id,new SettingFragment()).commit();



//        init();
        //设置是否为卡片式
//        boolean a = (Boolean)SharedPreferenceUtil.get(this, Constants.ORDER,true);
//        if( a ){
//            SharedPreferenceUtil.put(this,Constants.ORDER,true);
//        }else{
//            SharedPreferenceUtil.put(this,Constants.ORDER,false);
//            checkBox.setChecked(false);
//        }
    }

//    private void init(){
//        setTitleName("设置");
//
//        sw = (Switch) findViewById(R.id.switch_id);
//        checkBox = (CheckBox) findViewById(R.id.iscard_id);
//        LinearLayout clear = (LinearLayout) findViewById(R.id.clear_id);
//        TextView cache = (TextView) findViewById(R.id.cache_id);
//
//        // TODO: 2016/12/5 缓存文件目录
//        cache.setText(FileUtil.getCacheSize(FileUtil.getSDCacheDir(this)));
//
//
//        //开关监听器
//        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                if(b){
////                    SharedPreferenceUtil.put(SettingsActivity.this,Constants.SWITCH,b);
////                    Intent intent = new Intent("com.my.localtion");
////                    intent.putExtra(Constants.SWITCH,b);
////                    sendBroadcast(intent);
////                }else {
////                    SharedPreferenceUtil.put(SettingsActivity.this,Constants.SWITCH,b);
////                }
//            }
//        });
//
//        //单选框监听器
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    SharedPreferenceUtil.put(SettingsActivity.this,Constants.ORDER,b);
//                    Intent intent = new Intent("com.my.order");
//                    intent.putExtra(Constants.ORDER,b);
//                    sendBroadcast(intent);
//                }else {
//                    SharedPreferenceUtil.put(SettingsActivity.this,Constants.ORDER,b);
//                    Intent intent = new Intent("com.my.order");
//                    intent.putExtra(Constants.ORDER,b);
//                    sendBroadcast(intent);
//                }
//            }
//        });
//
//        clear.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder buidler = new AlertDialog.Builder(SettingsActivity.this);
//                buidler.setTitle("提示");
//                buidler.setMessage("是否清空缓存");
//                buidler.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                buidler.setNegativeButton("取消",null);
//                buidler.show();
//                return true;
//            }
//        });
//
//

//    }

}
