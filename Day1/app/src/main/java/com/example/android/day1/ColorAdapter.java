package com.example.android.day1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by admin on 8/26/2017.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.CustomViewHolder> {
    String[] data;
    Context mContext;
    Random mRandom;

    ColorAdapter(Context context , String[] color){
        data  = color;
        mContext = context;
    }

    @Override
    public ColorAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ColorAdapter.CustomViewHolder holder, int position) {
        holder.mtextView.setText(data[position]);
        holder.mtextView.getLayoutParams().height =  getRandomIntInRange(250,75);
        holder.mtextView.setBackgroundColor(customColor());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private int getRandomIntInRange(int max,int min){
        return mRandom.nextInt((max-min)+min)+min;
    }

    private int customColor(){
        float hue = mRandom.nextInt(360);

        float saturation = 1.0f;

        float value = 1.0f;

        int alfa = 255;

        return Color.HSVToColor(alfa,new float[]{hue,saturation,value});
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView mtextView;
        public CustomViewHolder(View v){
            super(v);
            mtextView = (TextView)v.findViewById(R.id.text_view);
        }

    }
}
