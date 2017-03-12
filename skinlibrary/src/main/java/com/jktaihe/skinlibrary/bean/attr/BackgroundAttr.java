/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean.attr;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.bean.BaseAttr;


/**
 * Created by jktaihe on 17-1-12.
 */
public class BackgroundAttr extends BaseAttr {

    public BackgroundAttr(int androidAttrID) {
        this.androidAttrID = androidAttrID;
    }

    @Override
    public void apply(View view) {

        if (isColor()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                ColorStateList colorStateList = SkinManager.getInstance().getColorStateList(attrValueRefId);

                if (view instanceof CardView) {
                    ((CardView) view).setCardBackgroundColor(colorStateList);

                }else {
                    view.setBackgroundTintList(colorStateList);
                }

            }else {

                int color = SkinManager.getInstance().getColor(attrValueRefId);

                if (view instanceof CardView) {
                    ((CardView) view).setCardBackgroundColor(color);
                }else {
                    view.setBackgroundColor(color);
                }

            }

        } else if (isDrawable()) {

            view.setBackgroundDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));

        }
    }
}
