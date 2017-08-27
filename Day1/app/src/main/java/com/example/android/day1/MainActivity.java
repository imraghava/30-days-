package com.example.android.day1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mReclyclerView;
    private StaggeredGridLayoutManager mManager;
    private ColorAdapter mAdapter;
    private String color[] = {
        "Red","Green","Blue","Yellow","Magenta","Cyan","Orange",
                "Aqua","Azure","Beige","Bisque","Brown","Coral","Crimson"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReclyclerView = (RecyclerView) findViewById(R.id.reclycler_view);
        mManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mReclyclerView.setLayoutManager(mManager);
        mAdapter = new ColorAdapter(getApplicationContext(),color);

        mReclyclerView.setAdapter(mAdapter);
    }
}
