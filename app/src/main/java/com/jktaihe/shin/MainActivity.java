/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.jktaihe.shin.adapter.TabViewpagerAdapter;
import com.jktaihe.skinlibrary.ui.SkinActivity;

public class MainActivity extends SkinActivity {


    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private String[] tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }

    private void setUpView() {
        tabs = getResources().getStringArray(R.array.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar Title");
        setSupportActionBar(toolbar);

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        for (int i = 0; i < tabs.length; i++) {
            tablayout.addTab(tablayout.newTab().setText(tabs[i]));
        }

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new TabViewpagerAdapter(getSupportFragmentManager(), tabs));
        tablayout.setupWithViewPager(viewpager);
    }
}
