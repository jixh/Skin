/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.bean.BaseAttr;

/**
 * Created by jktaihe on 17-1-12.
 */
public class TextViewDrawableAttr extends BaseAttr {

    public TextViewDrawableAttr(int androidAttrID) {
        this.androidAttrID = androidAttrID;
    }

    @Override
    public void apply(View view) {

        if (view instanceof TextView) {

            TextView tv = (TextView) view;

            if (isDrawable()) {

                Drawable drawable = SkinManager.getInstance().getDrawable(attrValueRefId);

                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

                if (androidAttrID == android.R.attr.drawableLeft) {
                    tv.setCompoundDrawables(drawable, null, null, null);
                } else if (androidAttrID == android.R.attr.drawableRight) {
                    tv.setCompoundDrawables(null, null,drawable, null);
                } else if (androidAttrID == android.R.attr.drawableTop) {
                    tv.setCompoundDrawables(null,drawable, null, null);
                } else if (androidAttrID == android.R.attr.drawableBottom) {
                    tv.setCompoundDrawables(null, null, null,drawable);
                }

            }
        }
    }
}
