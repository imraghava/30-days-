package com.example.android.day2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by admin on 8/27/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> FragmentList = new ArrayList<>();
    List<String> title = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        FragmentList.add(fragment);
        this.title.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
