/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.shin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jktaihe.skinlibrary.bean.SkinLoadListener;
import com.jktaihe.skinlibrary.ui.SkinFragment;
import com.jktaihe.skinlibrary.SkinManager;
import com.jktaihe.shin.R;


public class BasicFragment extends SkinFragment {


    private MaterialDialog dialog;

    private final static String skinUrl = "https://raw.githubusercontent.com/jixh/Skin/master/app/src/main/assets/skin/net_style.skin";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_baseskin, container, false);

        dialog = new MaterialDialog.Builder(getContext())
                .title("换肤中")
                .content("请耐心等待")
                .canceledOnTouchOutside(false)
                .progress(false, 100, true).build();

        view.findViewById(R.id.btn_switch_local).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SkinManager.getInstance().loadSkin("skin_style.skin",
                                new SkinLoadListener() {
                                    @Override
                                    public void onStart() {
                                        Log.i("SkinLoadListener", "正在切换中");
                                        dialog.show();
                                    }

                                    @Override
                                        public void onSuccess() {
                                        Log.i("SkinLoadListener", "切换成功");
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailed(String errMsg) {
                                        Log.i("SkinLoadListener", "切换失败:" + errMsg);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onProgress(int progress) {
                                        Log.i("SkinLoadListener", "皮肤文件下载中:" + progress);

                                    }
                                }

                        );
                    }
                }

        );

        view.findViewById(R.id.btn_switch_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkinFromUrl(skinUrl, new SkinLoadListener() {
                    @Override
                    public void onStart() {
                        Log.i("SkinLoadListener", "正在切换中");
                        dialog.setContent("正在从网络下载皮肤文件");
                        dialog.show();
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("SkinLoadListener", "切换成功");
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        Log.i("SkinLoadListener", "切换失败:" + errMsg);
                        dialog.setContent("换肤失败:" + errMsg);
                    }

                    @Override
                    public void onProgress(int progress) {
                        Log.i("SkinLoadListener", "皮肤文件下载中:" + progress);
                        dialog.setProgress(progress);
                    }
                });
            }
        });

        Button viewById = (Button) view.findViewById(R.id.btn_switch_default);
//        ColorStateList csl=(ColorStateList)getResources().getColorStateList(R.color.color_state_list);
//        viewById.setTextColor(csl);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().restoreDefaultTheme();
            }
        });

        return view;
    }

}
