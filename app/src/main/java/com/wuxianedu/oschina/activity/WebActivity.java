package com.wuxianedu.oschina.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.bean.Content;
import com.wuxianedu.oschina.network.RequestClient;
import com.wuxianedu.oschina.network.RequestConfig;
import com.wuxianedu.oschina.utils.Constants;
import com.wxhl.core.utils.JSONParseUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebActivity extends BaseActivity {

    private List<String> list = new ArrayList<>();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTemplate = true;
        setContentView(R.layout.activity_web);

        init();

    }

    private void init() {


        setTitle(getIntent().getStringExtra(Constants.NAME));
        setSubTitleName("README.md");

        String url = getIntent().getStringExtra(Constants.URL);
        webView = (WebView) findViewById(R.id.web_id);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        final RequestConfig config = new RequestConfig();
        config.setTipInfoLayout(mTipInfoLayout);
        new RequestClient(this, config) {

            @Override
            protected void loadSuccess(String result) {
                Content content = JSONParseUtil.parseObject(result, Content.class);
                webView.loadDataWithBaseURL(null, content.getContent(), "text/html", "utf-8", null);
                HandlerHtml(content.getContent());
            }

            @Override
            protected void loadFail() {

            }
        }.get(url);



    }


    // 获取src路径的正则
    private static final String IMGSRC_REG = "http(s)?:\"?(.*?)(\"|>|)+.(jpg|jpeg|gif|png|bmp)";

    private void HandlerHtml(String str) {


        //构建匹配规则1
        Pattern p_img = Pattern.compile( //获取url   Pattern.CASE_INSENSITIVE 不区分大小写[^>]*>
                "(<img[^>]+src=\")(\\S+)\"", Pattern.CASE_INSENSITIVE);
        //构建匹配规则2
        Pattern srcImage = Pattern.compile( //获取src后对应的图片地址
                IMGSRC_REG, Pattern.CASE_INSENSITIVE);

        Matcher matcher = p_img.matcher(str);

        while (matcher.find()) {
            String string = matcher.group();
            Matcher srcMatcher = srcImage.matcher(string);
            srcMatcher.find();
            try{
                String imageUrl = srcMatcher.group();
                list.add(imageUrl);
            }catch (Exception e){

            }
        }


        //添加点击事件
        str = str.replaceAll("(<img[^>]+src=\")(\\S+)\"", "$1$2\" width='100%' onClick=\"javascript:jsListener.onImageClick('$2')\"");

        //替换超链接
        str = str.replaceAll("href", "");

        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        webView.addJavascriptInterface(new MyJs(), "jsListener");

    }

    class MyJs {

        @JavascriptInterface
        public void onImageClick(String imgeUrl) {
//            Toast.makeText(WebActivity.this, list.indexOf(imgeUrl) + "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WebActivity.this,PictureActivity.class);
            intent.putStringArrayListExtra(Constants.PICTURE, (ArrayList<String>) list);
            intent.putExtra(Constants.POSITION,list.indexOf(imgeUrl));
            startActivity(intent);
        }
    }
}
