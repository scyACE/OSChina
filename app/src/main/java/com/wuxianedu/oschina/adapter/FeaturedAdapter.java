package com.wuxianedu.oschina.adapter;
//                .-~~~~~~~~~-._       _.-~~~~~~~~~-.
//            __.'              ~.   .~              `.__
//          .'//                  \./                  \\`.
//        .'//                     |                     \\`.
//      .'// .-~"""""""~~~~-._     |     _,-~~~~"""""""~-. \\`.
//    .'//.-"                 `-.  |  .-'                 "-.\\`.
//  .'//______.============-..   \ | /   ..-============.______\\`.
//.'______________________________\|/______________________________`.

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.activity.DynamicActivity;
import com.wuxianedu.oschina.bean.Featured;
import com.wuxianedu.oschina.utils.Constants;
import com.wuxianedu.oschina.utils.TypefaceUtils;
import com.wxhl.core.adapter.BaseCustomAdapter;
import com.wxhl.core.adapter.CusViewHolder;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.network.image.PicassoImageLoader;
import com.wxhl.core.utils.T;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by shicunyu on 2016/11/28.
 */

public class FeaturedAdapter extends BaseCustomAdapter<Featured> {
    private Context context;
    private List<Featured> list;
    private int ids ;
    public FeaturedAdapter(Context context, List<Featured> data, int layoutResId,int id) {
        super(context, data, layoutResId);
        this.context = context;
        this.list = data;
        this.ids = id;
    }

    @Override
    protected void getListener(View convertView) {

        convertView.findViewById(R.id.head_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Featured featured = list.get(position);
                int id = featured.getOwner().getId();
                String name = featured.getName();

                if(ids != id){

                    Intent intent = new Intent(context, DynamicActivity.class);
                    intent.putExtra(Constants.ID,id);
                    intent.putExtra(Constants. NAME,name);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public View getCustomView(int position, View convertView) {


        Featured featured = list.get(position);

        ImageView head = CusViewHolder.get(convertView, R.id.head_id);//头像

        head.setTag(position);

        TextView name = CusViewHolder.get(convertView, R.id.name_id);//名字
        TextView description = CusViewHolder.get(convertView, R.id.description_id);//描述
        TextView see = CusViewHolder.get(convertView, R.id.see_id);//眼睛
        TypefaceUtils.setIconText(see, "\uf06e " + featured.getForks_count());
        TextView star = CusViewHolder.get(convertView, R.id.star_id);//星
        TypefaceUtils.setIconText(star, "\uf005 " + featured.getStars_count());
        TextView watch = CusViewHolder.get(convertView, R.id.watch_id);//看
        TypefaceUtils.setIconText(watch, "\uf126 " + featured.getWatches_count());

        TextView language = CusViewHolder.get(convertView, R.id.language_id);//语言
        if(featured.getLanguage() != null){
            if(!featured.getLanguage().isEmpty()){
                TypefaceUtils.setIconText(language, "\uf02b " + featured.getLanguage());
            }
        }



        //设置
        ImageLoaderFactory.getImageLoader().loadImage(featured.getOwner().getNew_portrait(), head,R.mipmap.mini_avatar,R.mipmap.mini_avatar);
        name.setText(featured.getOwner().getName() + ": " + featured.getName());
        if(featured.getDescription()!= null){
            if(!featured.getDescription().isEmpty()){
                description.setText(featured.getDescription());
            }
        }

        return convertView;
    }

}
