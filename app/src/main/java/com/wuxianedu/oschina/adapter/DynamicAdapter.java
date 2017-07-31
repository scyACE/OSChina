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
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.bean.EventVo;
import com.wuxianedu.oschina.utils.EventUtils;
import com.wuxianedu.oschina.utils.TimeFormatUtil;
import com.wxhl.core.adapter.BaseCustomAdapter;
import com.wxhl.core.adapter.CusViewHolder;
import com.wxhl.core.network.image.ImageLoaderFactory;
import com.wxhl.core.utils.DateUtils;

import java.util.List;

/**
 * Created by scy on 2016/11/30.
 */

public class DynamicAdapter extends BaseCustomAdapter<EventVo> {

    private Context context;
    public DynamicAdapter(Context context, List<EventVo> data, int layoutResId) {
        super(context, data, layoutResId);
        this.context = context;
    }

    @Override
    public View getCustomView(int position, View convertView) {

        EventVo eventVo = data.get(position);

        ImageView head = CusViewHolder.get(convertView, R.id.head_id);//头像
        TextView action = CusViewHolder.get(convertView,R.id.action_id);//评论
        LinearLayout infor = CusViewHolder.get(convertView,R.id.infor_ll);//
        TextView time = CusViewHolder.get(convertView,R.id.time_id);//时间

        ImageLoaderFactory.getImageLoader().loadImage(eventVo.getProject().getOwner().getNew_portrait(), head,R.mipmap.mini_avatar,R.mipmap.mini_avatar);

        action.setText(EventUtils.parseEventTitle(context,eventVo.getProject().getOwner().getUsername(),eventVo.getProject().getOwner().getUsername()+"/" +eventVo.getProject().getName(),eventVo));

        time.setText(TimeFormatUtil.friendlyFormat(context,eventVo.getUpdated_at()));

        if(eventVo.getData() != null){

            List<EventVo.DataEntity.CommitsEntity> list = eventVo.getData().getCommits();
            if(list != null){
                infor.removeAllViews();
                for(int i = 0; i < list.size();i++){

                    if(list.get(i).getMessage().length() != 1){
                        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //宽度为100px，高为自适应最小的高度
                        TextView textView = new TextView(context);
                        textView.setBackgroundColor(Color.YELLOW);
                        textView.setText(list.get(i).getAuthor().getName() + "  →  " +list.get(i).getMessage());
                        infor.addView(textView,textParams);
                    }
                }
            }


        }

        return convertView;
    }
}
