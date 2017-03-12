/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean.attr;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.bean.BaseAttr;


/**
 * Created by jktaihe on 17-1-12.
 */
public class TextColorHintAttr extends BaseAttr {

    public TextColorHintAttr(int androidAttrID) {
        this.androidAttrID = androidAttrID;
    }

    @Override
    public void apply(View view) {

        if (view instanceof TextView) {

            TextView tv = (TextView) view;

            if (isColor()) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                    tv.setHintTextColor(SkinManager.getInstance().getColorStateList(attrValueRefId));

                }else {

                    tv.setHintTextColor(SkinManager.getInstance().getColor(attrValueRefId));

                }

            }
        }
    }
}
