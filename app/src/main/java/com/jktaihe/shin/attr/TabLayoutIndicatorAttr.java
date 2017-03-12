/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin.attr;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.jktaihe.skinlibrary.bean.BaseAttr;
import com.jktaihe.skinlibrary.SkinManager;

public class TabLayoutIndicatorAttr extends BaseAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TabLayout) {
            TabLayout tl = (TabLayout) view;
            if (isColor()) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                tl.setSelectedTabIndicatorColor(color);
            }
        }
    }
}
