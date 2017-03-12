/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;
import android.view.View;

import java.util.List;

/**
 * Created by jktaihe on 17-1-12.
*/

 public interface ISkinSet {
    void setSkinViewAttrs(View view, List<DynamicAttr> attrs);
    void setSkinViewAttr(View view, String attrName, int attrResId);
}
