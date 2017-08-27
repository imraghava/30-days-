package com.example.android.day2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by admin on 8/27/2017.
 */

public class DFragment extends Fragment {
    private int color;

    public DFragment(){
    }
    @SuppressLint("ValidFragment")
    public DFragment(int color){
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dfragment,container,false);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        frameLayout.setBackgroundColor(color);

        RecyclerView recylerView = (RecyclerView)view.findViewById(R.id.scrollView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

        recylerView.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(getContext());
        recylerView.setAdapter(customAdapter);

        return view;
    }
}
