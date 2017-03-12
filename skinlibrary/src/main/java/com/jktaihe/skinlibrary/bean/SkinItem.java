/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by jktaihe on 17-1-12.
 */
public class SkinItem {

    public View view;

    public List<BaseAttr> attrs;

    public SkinItem() {
        attrs = new ArrayList<>();
    }

    public void apply() {
        if (attrs != null)
            for (BaseAttr at : attrs)
                at.apply(view);
    }

    public void clean() {
        if (attrs != null && attrs.size() > 0)
            attrs.clear();
    }

    @Override
    public String toString() {
        return "SkinItem{" +
                "view=" + view +
                ", attrs=" + attrs +
                '}';
    }
}
