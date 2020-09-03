package com.example.lbck.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class QuickAreaAdapter extends FragmentPagerAdapter {
    private List<Fragment> mHashMap;
    private List<String> mTitle;

    public QuickAreaAdapter(@NonNull FragmentManager fm, List<Fragment> hashMaps, List<String> title) {
        super(fm);
        this.mHashMap = hashMaps;
        this.mTitle = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mHashMap.get(position);
    }

    @Override
    public int getCount() {
        return mHashMap != null ? mHashMap.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
