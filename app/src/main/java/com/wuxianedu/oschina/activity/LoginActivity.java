package com.wuxianedu.oschina.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.core.MyApplication;
import com.wuxianedu.oschina.bean.User;
import com.wuxianedu.oschina.network.RequestAPI;
import com.wuxianedu.oschina.network.RequestClient;
import com.wuxianedu.oschina.network.RequestConfig;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.network.NetWorkUtils;
import com.wxhl.core.utils.FileLocalCache;
import com.wxhl.core.utils.JSONParseUtil;
import com.wxhl.core.utils.KeyBoardUtil;
import com.wxhl.core.utils.L;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.utils.SnackbarUtils;
import com.wxhl.core.utils.T;
import com.wxhl.core.utils.TextUtil;
import com.wxhl.core.utils.constants.CoreConstants;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText account_et;
    private EditText password_et;
    private Button login_btn;
    private CheckBox auto_login;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsTemplate = true;
        setContentView(R.layout.activity_login);
        init();


    }

    /**
     * 初始化
     */
    private void init(){

        RequestConfig config = new RequestConfig();
        config.setTipInfoLayout(mTipInfoLayout);
        //设置标题
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //获取组件
        account_et = (EditText) findViewById(R.id.account_layout_id).findViewById(R.id.account_et);
        password_et = (EditText) findViewById(R.id.password_layout_id).findViewById(R.id.password_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        auto_login = (CheckBox) findViewById(R.id.auto_login_checkbox);
        account_et.addTextChangedListener(new MyTextWatch(Constants.ACCOUNT_TYPE));
        password_et.addTextChangedListener(new MyTextWatch(Constants.PASSWORD_TYPE));

        if(SharedPreferenceUtil.contains(this,"email")){
            account_et.setText((String) SharedPreferenceUtil.get(this,"email",""));
            password_et.setText((String) SharedPreferenceUtil.get(this,"password",""));
        }

        login_btn.setOnClickListener(this);
    }

    /**
     * 登录
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                login();
            break;
        }
    }


    /**
     * 登录时的判断
     */
    private void login(){

        KeyBoardUtil.HideKeyboard(mTipInfoLayout);
        RequestConfig config = new RequestConfig();
        config.setIsCover(false);
        config.setIsLoading(false);
        config.setTipInfoLayout(mTipInfoLayout);
        Map<String,String> param = new HashMap<>();
        param.put("email",account_et.getText().toString());
        param.put("password",password_et.getText().toString());
        L.e(param.toString());
        new RequestClient(this,config){

            @Override
            protected void loadSuccess(String result) {
                User user = JSONParseUtil.parseObject(result, User.class);
                if(auto_login.isChecked()){
                    SharedPreferenceUtil.put(LoginActivity.this,"email",account_et.getText().toString());
                    SharedPreferenceUtil.put(LoginActivity.this,"password",password_et.getText().toString());
                    FileLocalCache.setSerializableData(CoreConstants.CACHE_DIR_SD,user,Constants.USER_FILE);
                }else{
                    FileLocalCache.clearCache1();
                    SharedPreferenceUtil.remove(LoginActivity.this,"email");
                    SharedPreferenceUtil.remove(LoginActivity.this,"password");
                }
                SnackbarUtils.showMessageDefault("正在登陆, 请稍后...",mTipInfoLayout);
                MyApplication.user = user;

                Intent rIntent = new Intent();
                rIntent.setAction("com.scy.MyReceiver");
                sendBroadcast(rIntent);

                Intent intent =  getIntent();
                if(intent.getBooleanExtra(Constants.IS_FROM_ME,false)){
                    setResult(101);
                    finish();
                }else {
                    intent.setClass(LoginActivity.this,UserInforActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            protected void loadFail() {
                if(NetWorkUtils.NETWORK){
                    SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT,"账号或密码错误",mTipInfoLayout);
                }else{
                    SnackbarUtils.showMessageDefault("请检查网络",mTipInfoLayout);
                }

            }
        }.post(RequestAPI.LOGIN,param);
    }




    /**
     * 当输入框有东西时  可登陆
     */
    class MyTextWatch implements TextWatcher{

        private int type;
        public MyTextWatch(int type){
            this.type = type;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(type == Constants.ACCOUNT_TYPE){
                if(TextUtils.isEmpty(account_et.getText())){
                    login_btn.setEnabled(false);
                }else{
                    flag-=1;
                }
            }
            if(type == Constants.PASSWORD_TYPE){
                if(TextUtils.isEmpty(password_et.getText())){
                    login_btn.setEnabled(false);
                }else{
                    flag-=1;
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(type == Constants.ACCOUNT_TYPE){
                if(TextUtils.isEmpty(account_et.getText())){
                    login_btn.setEnabled(false);
                }else{
                    flag+=1;
                }
            }
            if(type == Constants.PASSWORD_TYPE){
                if(TextUtils.isEmpty(password_et.getText())){
                    login_btn.setEnabled(false);
                }else{
                    flag+=1;
                }
            }
            if(flag == 2){
                login_btn.setEnabled(true);
            }
        }
    }
}
