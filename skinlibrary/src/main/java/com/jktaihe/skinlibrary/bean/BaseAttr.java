/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;

import android.view.View;

/**
 * Created by jktaihe on 17-1-12.
 */
public abstract class BaseAttr implements Cloneable {

    public static final String COLOR = "color";
    public static final String DRAWABLE = "drawable";
    public static final String MIPMAP = "mipmap";

    /**
     * android attribute reference id eg: android.R.attr.textColor

     */
    public int androidAttrID;

    /**
     * attribute name, eg: background
     */
    public String attrName;

    /**
     * attribute reference id
     */
    public int attrValueRefId;

    /**
     * resources name, eg: btn_background
     */
    public String attrValueRefName;

    /**
     * type of the value , such as color or drawable
     */
    public String attrValueTypeName;

    /**
     * Use to apply view with new TypedValue
     */
    public abstract void apply(View view);

    protected boolean isDrawable() {
        return DRAWABLE.equals(attrValueTypeName) || MIPMAP.equals(attrValueTypeName);
    }

    protected boolean isColor() {
        return COLOR.equals(attrValueTypeName);
    }


    @Override
    public String toString() {
        return "BaseAttr{" +
                "attrName='" + attrName + '\'' +
                ", attrValueRefId=" + attrValueRefId +
                ", attrValueRefName='" + attrValueRefName + '\'' +
                ", attrValueTypeName='" + attrValueTypeName + '\'' +
                '}';
    }

    @Override
    public BaseAttr clone() {
        BaseAttr o = null;
        try {
            o = (BaseAttr) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseAttr baseAttr = (BaseAttr) o;

        return attrName.equals(baseAttr.attrName);

    }

    @Override
    public int hashCode() {
        return attrName.hashCode();
    }
}
