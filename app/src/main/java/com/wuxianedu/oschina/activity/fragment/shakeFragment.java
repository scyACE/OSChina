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
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.DetailActivity;
import com.wuxianedu.oschina.activity.core.BaseFragment;
import com.wuxianedu.oschina.bean.Featured;
import com.wuxianedu.oschina.network.RequestAPI;
import com.wuxianedu.oschina.network.RequestClient;
import com.wuxianedu.oschina.network.RequestConfig;
import com.wuxianedu.oschina.utils.Constants;
import com.wuxianedu.oschina.utils.ShakeManager;
import com.wuxianedu.oschina.utils.TypefaceUtils;
import com.wxhl.core.adapter.CusViewHolder;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.utils.JSONParseUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shicunyu on 2016/11/28.
 */

public class shakeFragment extends BaseFragment {
    private ShakeManager shakeManager;
    private RequestConfig config;
    private CardView cardView;
    private ImageView head;
    private TextView name;
    private TextView description;
    private TextView eye;
    private TextView star;
    private TextView watch;
    private TextView language;
    private LinearLayout loading;
    private SoundPool soundPool;
    private Map<Integer,Integer> soundMap;

    @Override
    protected int getBodyView() {
        return R.layout.fragmet_shake;
    }

    @Override
    protected void init(View root) {

        loading = (LinearLayout) root.findViewById(R.id.loading_id);
        cardView = (CardView) root.findViewById(R.id.cardview_id);
        head = (ImageView) root.findViewById(R.id.head_id);
        name = (TextView) root.findViewById(R.id.name_id);
        description = (TextView) root.findViewById(R.id.description_id);
        eye = (TextView) root.findViewById(R.id.see_id);
        star = (TextView) root.findViewById(R.id.star_id);
        watch = (TextView) root.findViewById(R.id.watch_id);
        language = (TextView) root.findViewById(R.id.language_id);




        final ImageView up_img = (ImageView) root.findViewById(R.id.shake_up_img);
        final ImageView down_img = (ImageView) root.findViewById(R.id.shake_down_img);
        config = new RequestConfig();
        config.setIsCover(false);
        config.setIsLoading(false);
        config.setTipInfoLayout(mTipInfoLayout);


        shakeManager = new ShakeManager(getContext());

        shakeManager.start();
        //音频
        play();

        shakeManager.setShakeListener(new ShakeManager.ShakeListener() {
            @Override
            public void onShake() {
                TranslateAnimation animation1 = new TranslateAnimation(0,0,0,-80);
                animation1.setDuration(600);
                animation1.setRepeatCount(1);
                animation1.setRepeatMode(Animation.REVERSE);
                up_img.startAnimation(animation1);
                TranslateAnimation animation2 = new TranslateAnimation(0,0,0,100);
                animation2.setDuration(600);
                animation2.setRepeatCount(1);
                animation2.setRepeatMode(Animation.REVERSE);
                down_img.startAnimation(animation2);

                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        shakeManager.stop();
                        cardView.setVisibility(View.GONE);
                        //播放音乐
                        soundPool.play(soundMap.get(1),0.3f,0.3f,1,0,1);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        shakeManager.start();
                        loading.setVisibility(View.VISIBLE);
                        request();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }

    private void request(){

        new RequestClient(mContext,config){

            @Override
            protected void loadSuccess(String result) {

                soundPool.play(soundMap.get(0),0.3f,0.3f,1,0,1);
                try {
                    result = new String(result.getBytes("ISO8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                loading.setVisibility(View.GONE);

                final Featured featured = JSONParseUtil.parseObject(result,Featured.class);
                cardView.setVisibility(View.VISIBLE);
                ImageLoaderFactory.getImageLoader().loadImage(featured.getOwner().getNew_portrait(),head);
                name.setText(featured.getOwner().getName() + "/" + featured.getName());
                if(description != null){
                    description.setText(featured.getDescription());
                }
                TypefaceUtils.setIconText(eye, "\uf06e " + featured.getForks_count());
                TypefaceUtils.setIconText(star, "\uf005 " + featured.getStars_count());
                TypefaceUtils.setIconText(watch, "\uf126 " + featured.getWatches_count());
                if(featured.getLanguage() != null){
                    if(!featured.getLanguage().isEmpty()){
                        TypefaceUtils.setIconText(language, "\uf02b " + featured.getLanguage());
                    }
                }

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra(Constants.FEATURED_,featured);
                        startActivity(intent);
                    }
                });
            }

            @Override
            protected void loadFail() {

            }
        }.get(RequestAPI.SHAKE);
    }


    /**
     * 播放音频
     */
    private void play(){
        //第一个参数 播放数量  第二个参数  用系统声音
        soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM,5);
        soundMap = new HashMap<>();
        int firstId = 0;
        int secondId = 0;
        try {
            firstId = soundPool.load(mContext.getAssets().openFd("sound/shake_match.mp3"),1);
            secondId = soundPool.load(mContext.getAssets().openFd("sound/shake_sound_male.mp3"),1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        soundMap.put(0,firstId);
        soundMap.put(1,secondId);

    }

    //切换fragment是回调的方法
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            shakeManager.stop();
        }else {
            shakeManager.start();
        }
    }

    /**
     *  锁屏时取消监听
     */
    @Override
    public void onPause() {
        super.onPause();
        shakeManager.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isHidden()){
            shakeManager.start();
        }
    }
}
