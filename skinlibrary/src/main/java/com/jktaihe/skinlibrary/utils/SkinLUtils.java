/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.utils;

import android.util.Log;
import com.jktaihe.skinlibrary.BuildConfig;

/**
 * Created by jktaihe on 17-1-12.
 */
public class SkinLUtils {

    private static boolean debug = BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        if (debug) Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (debug) Log.w(tag, msg);

    }

    public static void e(String tag, String msg) {
        if (debug) Log.e(tag, msg);
    }
}
