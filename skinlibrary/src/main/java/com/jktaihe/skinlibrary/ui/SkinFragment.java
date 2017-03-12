/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jktaihe.skinlibrary.bean.DynamicAttr;
import com.jktaihe.skinlibrary.bean.ISkinSet;

import java.util.List;

/**
 * Created by jktaihe on 17-1-12.
 */
public abstract class SkinFragment extends Fragment implements ISkinSet {

    protected SkinActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SkinActivity)
        mContext = (SkinActivity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public final LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return mContext.getLayoutInflater();
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
    }

    private final void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllView(viewGroup.getChildAt(i));
            }
            removeViewInSkinInflaterFactory(v);
        } else {
            removeViewInSkinInflaterFactory(v);
        }
    }

    private final void removeViewInSkinInflaterFactory(View v) {
        mContext.removeSkinView(v);
    }

    @Override
    public void setSkinViewAttrs(View view, List<DynamicAttr> attrs) {
         mContext.setSkinViewAttrs(view,attrs);
    }

    @Override
    public void setSkinViewAttr(View view, String attrName, int attrResId) {
         mContext.setSkinViewAttr(view,attrName,attrResId);
    }
}
