/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.ImageView;

import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.bean.BaseAttr;

/**
 * Created by jktaihe on 17-1-12.
 */
public class SrcAttr extends BaseAttr {
    public SrcAttr(int androidAttrID) {
        this.androidAttrID = androidAttrID;
    }

    @Override
    public void apply(View view) {

        if (view instanceof ImageView) {

            ImageView iv = (ImageView) view;

            if (isDrawable()) {

                iv.setImageDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));

            } else if (isColor()) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                    int color = SkinManager.getInstance().getColor(attrValueRefId);
                    Drawable drawable = iv.getDrawable();
                    if (drawable instanceof ColorDrawable) {
                        ((ColorDrawable) drawable.mutate()).setColor(color);
                    } else {
                        iv.setImageDrawable(new ColorDrawable(color));
                    }

                } else {

                    ColorStateList colorStateList = SkinManager.getInstance().getColorStateList(attrValueRefId);

                    Drawable drawable = iv.getDrawable();
                    DrawableCompat.setTintList(drawable, colorStateList);
                    iv.setImageDrawable(drawable);
                }
            }
        }
    }
}
