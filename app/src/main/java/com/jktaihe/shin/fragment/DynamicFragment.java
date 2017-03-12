/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jktaihe.skinlibrary.bean.DynamicAttr;
import com.jktaihe.skinlibrary.ui.SkinFragment;
import com.jktaihe.shin.R;
import java.util.ArrayList;
import java.util.List;

public class DynamicFragment extends SkinFragment {

    private LinearLayout llDynamicView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_add, container, false);
        llDynamicView = (LinearLayout) view.findViewById(R.id.ll_dynamic_view);
        initDynamicView();
        return view;
    }

    private void initDynamicView() {

        TextView tv = new TextView(getContext());
        tv.setText("The component which is dynamically generated");
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
        params.setMargins(40, 20, 40, 20);
        tv.setLayoutParams(params);
        llDynamicView.addView(tv);

        setSkinViewAttr(tv,"textColor", R.color.item_tv_title_color);


        ImageView iv = new ImageView(getContext());
        ViewGroup.LayoutParams ivparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llDynamicView.addView(iv, ivparams);

        List<DynamicAttr> dynamicAttrs = new ArrayList<>();
        dynamicAttrs.add(new DynamicAttr("src", R.drawable.icon1));
        dynamicAttrs.add(new DynamicAttr("background", R.color.colorPrimary));
        setSkinViewAttrs(iv, dynamicAttrs);


    }
}
