package com.wxhl.core.widget.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * 2015年11月4日,0004.
 */
public abstract class BaseAnimAdapter<T extends  RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(T holder, int position) {
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
//        if (position > lastPosition) {

        RotateAnimation rotateAnimation = new RotateAnimation(0,360f,0,0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1,0.5f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(400);
        animationSet.setFillAfter(false);

        viewToAnimate.startAnimation(animationSet);

//            AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f,1.0f);
//            alphaAnimation.setDuration(400);
//            alphaAnimation.setFillAfter(false);
//            viewToAnimate.startAnimation(alphaAnimation);


//            lastPosition = position;
//        }
    }


    @Override
    public void onViewDetachedFromWindow(T holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}