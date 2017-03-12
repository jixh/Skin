/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by jktaihe on 17-1-12.
 */
public class SkinPreferencesUtils {

    public static String PREFERENCE_NAME = "com_jktaihe_skin_pref";

    private SkinPreferencesUtils() { }


    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

}
