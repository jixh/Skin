/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;

import android.support.annotation.AnyRes;


/**
 * Created by jktaihe on 17-1-12.
 */
public class DynamicAttr {

    public String attrName;

    public int refResId;

    public DynamicAttr(String attrName, @AnyRes int refResId) {
        this.attrName = attrName;
        this.refResId = refResId;
    }
}
