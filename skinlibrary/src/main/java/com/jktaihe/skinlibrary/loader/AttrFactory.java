/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.loader;

import android.text.TextUtils;

import com.jktaihe.skinlibrary.bean.attr.BackgroundAttr;
import com.jktaihe.skinlibrary.bean.BaseAttr;
import com.jktaihe.skinlibrary.bean.attr.TextViewDrawableAttr;
import com.jktaihe.skinlibrary.bean.attr.TextColorHintAttr;
import com.jktaihe.skinlibrary.bean.attr.SrcAttr;
import com.jktaihe.skinlibrary.bean.attr.TextColorAttr;

import java.util.HashMap;


/**
 * Created by jktaihe on 17-1-12.
 */
public class AttrFactory {

    private static final HashMap<String, BaseAttr> mSupportAttrMap = new HashMap<>();

    public static BaseAttr getsSupportAttr(String attrName) {

        if (TextUtils.isEmpty(attrName))return null;

        BaseAttr baseAttr = mSupportAttrMap.get(attrName);

        if (baseAttr != null) return baseAttr;

        if (attrName.equals("textColor")) {
            baseAttr = new TextColorAttr(android.R.attr.textColor);
        } else if (attrName.equals("textColorHint")) {
            baseAttr = new TextColorHintAttr(android.R.attr.textColorHint);
        } else if (attrName.equals("background")) {
            baseAttr = new BackgroundAttr(android.R.attr.background);
        } else if (attrName.equals("src")) {
            baseAttr = new SrcAttr(android.R.attr.src);
        } else if (attrName.equals("drawableLeft")) {
            baseAttr = new TextViewDrawableAttr(android.R.attr.drawableLeft);
        } else if (attrName.equals("drawableRight")) {
            baseAttr = new TextViewDrawableAttr(android.R.attr.drawableRight);
        } else if (attrName.equals("drawableTop")) {
            baseAttr = new TextViewDrawableAttr(android.R.attr.drawableTop);
        } else if (attrName.equals("drawableBottom")) {
            baseAttr = new TextViewDrawableAttr(android.R.attr.drawableBottom);
        }

        if (baseAttr !=null){
            mSupportAttrMap.put(attrName, baseAttr);
        }

        return baseAttr;
    }

    public static BaseAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        BaseAttr mBaseAttr = getsSupportAttr(attrName);
        if (mBaseAttr == null) return null;
        mBaseAttr = mBaseAttr.clone();
        mBaseAttr.attrName = attrName;
        mBaseAttr.attrValueRefId = attrValueRefId;
        mBaseAttr.attrValueRefName = attrValueRefName;
        mBaseAttr.attrValueTypeName = typeName;
        return mBaseAttr;
    }

    /**
     * add support's attribute
     */
    public static void addSupportAttr(String attrName, BaseAttr baseAttr) {
        mSupportAttrMap.put(attrName, baseAttr);
    }

}
