/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin;


import android.app.Application;

import com.jktaihe.shin.attr.TabLayoutIndicatorAttr;
import com.jktaihe.skinlibrary.SkinManager;


public class SkinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance()
                .init(this)
                .addSupportAttr("tabIndicatorColor", new TabLayoutIndicatorAttr());
    }
}
