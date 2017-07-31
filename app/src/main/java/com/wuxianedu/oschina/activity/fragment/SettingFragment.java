package com.wuxianedu.oschina.activity.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.jenzz.materialpreference.CheckBoxPreference;
import com.jenzz.materialpreference.SwitchPreference;
import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.utils.AppDataManager;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.utils.T;

import java.util.List;

/**
 * Created by Administrator on 2016/12/6.
 */

public class SettingFragment extends PreferenceFragment{

    private List<ResolveInfo> list;
    private Preference email;
    private Preference clear;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);

        email = findPreference("email");
        clear = findPreference("clear");


        clear.setSummary(AppDataManager.getCacheSize(getActivity()));
        feedBackInit();

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        
        switch (preference.getKey()){
            case "right":
                SwitchPreference sw = (SwitchPreference) preference;
                if(sw.isChecked()){
                    Intent intent = new Intent("com.scy.MyReceiver");
                    SharedPreferenceUtil.put(getActivity(), Constants.SWITCH,true);
                    getActivity().sendBroadcast(intent);
                }else{
                    Intent intent = new Intent("com.scy.MyReceiver");
                    SharedPreferenceUtil.put(getActivity(), Constants.SWITCH,false);
                    getActivity().sendBroadcast(intent);
                }
            break;
            case "iscard":
                CheckBoxPreference checkbox = (CheckBoxPreference) preference;
                if(checkbox.isChecked()){
                    Intent intent = new Intent("com.my.order");
                    SharedPreferenceUtil.put(getActivity(), Constants.ORDER,true);
                    getActivity().sendBroadcast(intent);
                }else {
                    Intent intent = new Intent("com.my.order");
                    SharedPreferenceUtil.put(getActivity(), Constants.ORDER,false);
                    getActivity().sendBroadcast(intent);
                }
            break;
            case "clear":
                clear();
            break;
            case "email":
                feedBack();
            break;
            case "goodjob":
                try{
                    Uri uri = Uri.parse("market://details?id="+getActivity().getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }catch (Exception e){
                    T.show("请安装应用商店");
                }
            break;
        }
        
        
        
        
        
        
        
        
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


    /**
     * 发送邮件初始化,判断是否有邮箱
     */
    private void feedBackInit(){
        Uri uri = Uri.parse("mailto:237104789@qq.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        list = getActivity().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(list == null || list.size() == 0){
            email.setSummary("您暂无邮箱");
        }
    }

    /**
     * 发送邮件
     */
    private void feedBack(){
        Uri uri = Uri.parse("mailto:237104789@qq.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        if(list == null || list.size() == 0){

        }else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 清理缓存
     */
    private void clear(){

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage("正在清理");

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                AppDataManager.cleanCache(getActivity());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                dialog.dismiss();
                clear.setSummary("0KB");
                T.show("清理完成");
            }
        }.execute();
    }
}
