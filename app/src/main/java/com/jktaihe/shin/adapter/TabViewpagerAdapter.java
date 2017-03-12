/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jktaihe.skinlibrary.ui.SkinFragment;
import com.jktaihe.shin.fragment.BasicFragment;
import com.jktaihe.shin.fragment.DynamicFragment;


public class TabViewpagerAdapter extends FragmentPagerAdapter {

    private String[] list;

    public TabViewpagerAdapter(FragmentManager fm, String[] list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        CharSequence title = getPageTitle(position);

        SkinFragment fragment = null;

        if (list[0].equals(title)) {
            fragment = new BasicFragment();
        } else if (list[1].equals(title)) {
            fragment = new DynamicFragment();
        }

        return fragment;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list[position];
    }
}
