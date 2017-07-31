package com.wuxianedu.oschina.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.activity.core.MyApplication;
import com.wuxianedu.oschina.bean.User;
import com.wxhl.core.network.NetWorkUtils;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.utils.SharedPreferenceUtil;
import com.wxhl.core.utils.SnackbarUtils;

import org.w3c.dom.Text;

public class UserInforActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTemplate = true;
        setContentView(R.layout.activity_user_infor);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        setTitleName("个人中心");

        User user = MyApplication.user;

        ImageView head = (ImageView) findViewById(R.id.head_id);
        TextView name = (TextView) findViewById(R.id.name_id);
        TextView description = (TextView) findViewById(R.id.description_id);
        TextView email = (TextView) findViewById(R.id.email_id);
        TextView weibo = (TextView) findViewById(R.id.weibo_id);
        TextView blog = (TextView) findViewById(R.id.blog_id);
        TextView followers = (TextView) findViewById(R.id.followers_id);
        TextView following = (TextView) findViewById(R.id.following_id);
        TextView watched = (TextView) findViewById(R.id.watched_id);
        TextView stared = (TextView) findViewById(R.id.stared_id);
        final Button login_out = (Button) findViewById(R.id.login_out);

        ImageLoaderFactory.getImageLoader().loadImage(user.getNew_portrait(),head);
        name.setText(user.getName());
        //个人简介
        if(user.getBio() != null){
            description.setText("个人介绍: " + String.valueOf(user.getBio()));
        }else {
            description.setText("个人介绍: 暂无" );
        }
        //邮箱
        email.setText("邮箱: " + user.getEmail());
        //微博
        if(user.getWeibo()!= null){
            weibo.setText("微博: " + user.getWeibo()+"");
        }else {
            weibo.setText("微博: 暂无" );
        }
        //博客
        if(user.getBlog() != null){
            blog.setText("博客: " + user.getBlog()+"");
        }else {
            blog.setText("博客: 暂无" );
        }

        User.FollowBean followBean = user.getFollow();

        followers.setText("followers: " + followBean.getFollowers());
        following.setText("following: " + followBean.getFollowing());
        watched.setText("watched: " + followBean.getWatched());
        stared.setText("followers: " + followBean.getStarred());

        login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(NetWorkUtils.NETWORK){
                    startActivity(new Intent(UserInforActivity.this,LoginActivity.class));
                    MyApplication.user = null;
                    Intent intent = new Intent();
                    intent.setAction("com.scy.MyReceiver");
                    sendBroadcast(intent);
//                    Intent rIntent = new Intent();
//                    rIntent.setAction("com.scy.MyReceiver2");
//                    sendBroadcast(rIntent);

                    finish();
                }else{
                    SnackbarUtils.showMessage(SnackbarUtils.EStyle.ALERT,"请检查网络",mTipInfoLayout);
                }
            }
        });

    }
}
