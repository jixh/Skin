/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;
/**
 * Created by jktaihe on 17-1-12.
 */
public interface SkinLoadListener {

    void onStart();

    void onSuccess();

    void onFailed(String errMsg);

    void onProgress(int progress);

}
