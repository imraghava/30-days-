package com.example.android.day2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 8/27/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomVH> {

    String[]name;

    Context context;

    public CustomAdapter(Context context){
        this.context = context;
    }

    @Override
    public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.list_item,parent);

        CustomVH vh = new CustomVH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomVH holder, int position) {
        name = context.getResources().getStringArray(R.array.dessert_names);
        holder.mName.setText(name[position]);

    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class CustomVH extends RecyclerView.ViewHolder{

        public TextView mName;

        public CustomVH(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }
}
