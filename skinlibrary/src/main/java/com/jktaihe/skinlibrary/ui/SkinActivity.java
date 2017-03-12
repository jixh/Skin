/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jktaihe.skinlibrary.bean.DynamicAttr;
import com.jktaihe.skinlibrary.bean.ISkinSet;
import com.jktaihe.skinlibrary.bean.ISkinUpdate;
import com.jktaihe.skinlibrary.loader.SkinInflaterFactory;
import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.utils.SkinLUtils;

import java.util.List;


/**
 * Created by jktaihe on 17-1-12.
 */
public abstract class SkinActivity extends AppCompatActivity implements ISkinUpdate ,ISkinSet{

    public final static String TAG = SkinActivity.class.getSimpleName();

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        mSkinInflaterFactory.setAppCompatActivity(this);
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory);
        super.onCreate(savedInstanceState);
        changeStatusColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onSkinUpdate() {
        SkinLUtils.i(TAG, "onSkinUpdate");
        mSkinInflaterFactory.applySkin();
        changeStatusColor();
    }

    private void changeStatusColor() {

        String resName = getResName();

        if (TextUtils.isEmpty(resName))return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = SkinManager.getInstance().getColor(resName);
            if (color != -1) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    protected String getResName(){
        return "colorPrimaryDark";
    }

    public final void removeSkinView(View v) {
        mSkinInflaterFactory.removeSkinView(v);
    }


    @Override
    public void setSkinViewAttrs(View view, List<DynamicAttr> attrs) {
        mSkinInflaterFactory.setSkinView(view,attrs);
    }

    @Override
    public void setSkinViewAttr(View view, String attrName, int attrResId) {
        mSkinInflaterFactory.setSkinView(view,attrName,attrResId);
    }
}
