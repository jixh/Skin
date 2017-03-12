/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.loader;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.jktaihe.skinlibrary.SkinConstant;
import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.skinlibrary.bean.ISkinSet;
import com.jktaihe.skinlibrary.bean.SkinItem;
import com.jktaihe.skinlibrary.bean.DynamicAttr;
import com.jktaihe.skinlibrary.bean.BaseAttr;
import com.jktaihe.skinlibrary.utils.SkinLUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jktaihe on 17-1-12.
 */
public class SkinInflaterFactory implements LayoutInflaterFactory {

    private static final String TAG = SkinInflaterFactory.class.getSimpleName();
    private Map<View, SkinItem> mSkinItemMap = new HashMap<>();
    private AppCompatActivity mAppCompatActivity;
    private View skinView;

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        String skinSet = attrs.getAttributeValue(SkinConstant.NAMESPACE, SkinConstant.ATTR_SKIN_SET);
        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();

        View view = delegate.createView(parent, name, context, attrs);

        if (!TextUtils.isEmpty(skinSet)) {
            if (view == null) {
                view = ViewMaker.createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            parseSkinAttr(context, skinSet, attrs, view);
        }
        return view;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    /**
     * 解析标记的属性
     */
    private void parseSkinAttr(Context context, String skinSet, AttributeSet attrs, View view) {
        List<BaseAttr> viewAttrs = new ArrayList<>();//存储换皮肤View属性的集合
        SkinLUtils.i(TAG, "viewName:" + view.getClass().getSimpleName());

        String attrName;
        String attrValue;

        for (int i = 0; i < attrs.getAttributeCount(); i++) {//遍历当前View的属性
            attrName = attrs.getAttributeName(i);//属性名
            attrValue = attrs.getAttributeValue(i);//属性值
            SkinLUtils.i(TAG, "    AttributeName:" + attrName + "|attrValue:" + attrValue);

            if ("style".equals(attrName)) {//style
                String styleName = attrValue.substring(attrValue.indexOf("/") + 1);
                int styleID = context.getResources().getIdentifier(styleName, "style", context.getPackageName());
                String[] list = skinSet.split("\\|");
                int[] skinAttrs = getSkinAttrs(list);
                TypedArray a = context.getTheme().obtainStyledAttributes(styleID, skinAttrs);

                int resourceId;
                for (int n = 0; n < list.length; n++) {
                    resourceId = a.getResourceId(n, -1);
                    if (resourceId == -1) continue;
                    String entryName = context.getResources().getResourceEntryName(resourceId);//入口名，例如text_color_selector
                    String typeName = context.getResources().getResourceTypeName(resourceId);
                    BaseAttr baseAttr = AttrFactory.get(list[n], resourceId, entryName, typeName);

                    if (baseAttr != null) {
                        if (viewAttrs.contains(baseAttr)) viewAttrs.remove(baseAttr);
                        viewAttrs.add(baseAttr);
                    }
                }
                a.recycle();
            } else if (skinSet.contains(attrName) && attrValue.startsWith("@")) {//引用类型，形如@color/red
                try {
                    int id = Integer.parseInt(attrValue.substring(1));//资源的id
                    String entryName = context.getResources().getResourceEntryName(id);//入口名，例如text_color_selector
                    String typeName = context.getResources().getResourceTypeName(id);//类型名，例如color、drawable
                    BaseAttr mBaseAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    SkinLUtils.w(TAG, "    " + attrName + "\n    resource id:" + id + "\n    attrName:" + attrName + "\n    attrValue:" + attrValue + "\n    entryName:" + entryName + "\n    typeName:" + typeName);

                    if (mBaseAttr != null) {
                        if (viewAttrs.contains(mBaseAttr)) viewAttrs.remove(mBaseAttr);
                        viewAttrs.add(mBaseAttr);
                    }
                } catch (NumberFormatException e) {
                    SkinLUtils.e(TAG, e.toString());
                }
            }
        }

        if (viewAttrs != null && viewAttrs.size() > 0) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;
            mSkinItemMap.put(skinItem.view, skinItem);
            if (SkinManager.getInstance().isExternalSkin()) {//如果当前皮肤来自于外部
                skinItem.apply();
            }
        }
    }


    private int[] getSkinAttrs(String[] attrList) {

        int lengthOfListAttrs = attrList.length;

        int[] skinAttrs = new int[lengthOfListAttrs];


        for (int i = 0; i < lengthOfListAttrs; i++) {
            skinAttrs[i] = AttrFactory.getsSupportAttr(attrList[i]) != null ? AttrFactory.getsSupportAttr(attrList[i]).androidAttrID : -1;
        }

        return skinAttrs;
    }

    public void applySkin() {
        if (mSkinItemMap.isEmpty()) {
            return;
        }
        for (View view : mSkinItemMap.keySet()) {
            if (view == null) {
                continue;
            }
            mSkinItemMap.get(view).apply();
        }
    }

    public void clean() {
        if (mSkinItemMap.isEmpty()) {
            return;
        }
        for (View view : mSkinItemMap.keySet()) {
            if (view == null) {
                continue;
            }
            mSkinItemMap.get(view).clean();
        }
    }

    private void addSkinView(SkinItem item) {

        if (mSkinItemMap.get(item.view) != null) {
            List<BaseAttr> attrs = mSkinItemMap.get(item.view).attrs;
            for (BaseAttr baseAttr : item.attrs) {
                if (attrs.contains(baseAttr)) attrs.remove(baseAttr);
                attrs.add(baseAttr);
            }

        } else {
            mSkinItemMap.put(item.view, item);
        }
    }

    public void removeSkinView(View view) {
        mSkinItemMap.remove(view);
    }


    public SkinInflaterFactory setSkinView(View view, String attrName, int attrResId) {
        List<DynamicAttr> viewAttrs = new ArrayList<>();
        viewAttrs.add(new DynamicAttr(attrName, attrResId));
        setSkinView(view, viewAttrs);
        return this;
    }

    public SkinInflaterFactory setSkinView(View view, List<DynamicAttr> attrs) {
        List<BaseAttr> viewAttrs = new ArrayList<>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : attrs) {
            int id = dAttr.refResId;
            String entryName = mAppCompatActivity.getResources().getResourceEntryName(id);
            String typeName = mAppCompatActivity.getResources().getResourceTypeName(id);
            BaseAttr mBaseAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mBaseAttr);
        }

        skinItem.attrs = viewAttrs;
        skinItem.apply();
        addSkinView(skinItem);
        return this;
    }


}
