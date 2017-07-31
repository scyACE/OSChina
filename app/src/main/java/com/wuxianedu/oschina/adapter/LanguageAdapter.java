package com.wuxianedu.oschina.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxianedu.oschina.R;
import com.wuxianedu.oschina.bean.Languange;
import com.wxhl.core.widget.recycleview.BaseAnimAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/3.
 */

public class LanguageAdapter extends BaseAnimAdapter<LanguageAdapter.MyViewHolder>{

    private List<Languange> list;
    private View.OnClickListener onClickListener;
    public LanguageAdapter(List<Languange> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_languange,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.languange.setText(list.get(position).getName());
        holder.num.setText(String.valueOf(list.get(position).getProjects_count()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView languange,num;

        public MyViewHolder(View itemView) {
            super(itemView);
            languange = (TextView) itemView.findViewById(R.id.language_id);
            num = (TextView) itemView.findViewById(R.id.num_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListener != null){
                        onClickListener.onClick(view);
                    }
                }
            });
        }
    }
    public void setOnclickListener(View.OnClickListener onclickListener){
        this.onClickListener = onclickListener;
    }
}


