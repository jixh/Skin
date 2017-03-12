/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jktaihe.skinlibrary.SkinConstant;

/**
 * Created by jktaihe on 17-1-12.
 */

public class SkinFileUtils {

    /**
     * 复制assets/skin目录下的皮肤文件到指定目录
     * @param context the context
     * @param name    皮肤名 eg; style.skin
     * @param toDir   指定目录
     * @return
     */
    public static String copySkinAssetsToDir(Context context, String name, String toDir) {
        String toFile = toDir + File.separator + name;
        try {
            InputStream is = context.getAssets().open(SkinConstant.SKIN_DIR_NAME + File.separator + name);
            File fileDir = new File(toDir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            OutputStream os = new FileOutputStream(toFile);
            int byteCount;
            byte[] bytes = new byte[1024];

            while ((byteCount = is.read(bytes)) != -1) {
                os.write(bytes, 0, byteCount);
            }
            os.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return toFile;
    }

    /**
     * 获得存放皮肤的目录
     * @param context the context
     * @return 存放皮肤的目录
     */
    public static String getSkinDir(Context context) {
        File skinDir = new File(getCacheDir(context), SkinConstant.SKIN_DIR_NAME);
        if (!skinDir.exists())
            skinDir.mkdirs();

        return skinDir.getAbsolutePath();
    }

    /**
     * 获得缓存目录
     * @param context
     * @return
     */
    private static String getCacheDir(Context context) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File cacheDir = context.getExternalCacheDir();

            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs()))
                return cacheDir.getAbsolutePath();

        }

        return context.getCacheDir().getAbsolutePath();
    }

}
