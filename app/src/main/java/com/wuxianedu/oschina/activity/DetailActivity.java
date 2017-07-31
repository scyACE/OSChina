package com.wuxianedu.oschina.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.core.BaseActivity;
import com.wuxianedu.oschina.bean.Featured;
import com.wuxianedu.oschina.network.RequestAPI;
import com.wuxianedu.oschina.utils.Constants;
import com.wuxianedu.oschina.utils.TimeFormatUtil;
import com.wuxianedu.oschina.utils.TypefaceUtils;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsTemplate = true;
        setContentView(R.layout.activity_detail);

        init();

    }
    /**
     * 初始化
     */
    private void init(){
        final Featured featured = (Featured) getIntent().getSerializableExtra(Constants.FEATURED_);
        setTitle(featured.getName());
        setSubTitleName(featured.getOwner().getUsername());


        final TextView name = (TextView) findViewById(R.id.name_id);
        TextView time = (TextView) findViewById(R.id.time_id);
        TextView description = (TextView) findViewById(R.id.description_id);
        TextView date = (TextView) findViewById(R.id.date_id);
        TextView tag = (TextView) findViewById(R.id.tag_id);
        TextView language = (TextView) findViewById(R.id.language_id);
        TextView author_name = (TextView) findViewById(R.id.author_name_id);
        TextView readMe = (TextView) findViewById(R.id.readme_id);
        Button star = (Button) findViewById(R.id.star_btn);
        Button watch = (Button) findViewById(R.id.watch_btn);

        name.setText(featured.getName());
        if(featured.getLast_push_at() != null){
            time.setText("更新时间:  " + TimeFormatUtil.friendlyFormat(this, featured.getLast_push_at()));
        }
        description.setText(featured.getDescription());
        TypefaceUtils.setIconText(star, "\uf005 "  + " star " + featured.getStars_count());
        TypefaceUtils.setIconText(watch, "\uf06e " + " watch " + featured.getWatches_count());
        TypefaceUtils.setIconText(date, "\uf017 " + TimeFormatUtil.friendlyFormat(this, featured.getCreated_at()));
        TypefaceUtils.setIconText(tag, "\uF126 "  +featured.getForks_count());
        if(featured.getLanguage() != null){
            if(!featured.getLanguage().isEmpty()){
                TypefaceUtils.setIconText(language, "\uf02b " + featured.getLanguage());
            }
        }

        TypefaceUtils.setIconText(author_name,"\uf007 " + featured.getName());


        readMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,WebActivity.class);
                intent.putExtra(Constants.URL,RequestAPI.getReadmeURL(featured.getId()));
                intent.putExtra(Constants.NAME,featured.getName());
                startActivity(intent);
            }
        });









    }
}
