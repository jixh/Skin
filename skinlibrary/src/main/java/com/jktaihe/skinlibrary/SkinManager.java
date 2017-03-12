/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.jktaihe.skinlibrary.bean.SkinLoadListener;
import com.jktaihe.skinlibrary.loader.AttrFactory;
import com.jktaihe.skinlibrary.bean.BaseAttr;
import com.jktaihe.skinlibrary.bean.ISkinLoader;
import com.jktaihe.skinlibrary.utils.SkinPreferencesUtils;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.jktaihe.skinlibrary.bean.ISkinUpdate;
import com.jktaihe.skinlibrary.utils.SkinFileUtils;
import com.jktaihe.skinlibrary.utils.SkinLUtils;

import static com.jktaihe.skinlibrary.SkinConstant.DEFAULT_SKIN;
import static com.jktaihe.skinlibrary.SkinConstant.SKIN_CUSTOM_PATH;

/**
 * Created by jktaihe on 17-1-12.
 */
public class SkinManager implements ISkinLoader {

    private static final String TAG = SkinManager.class.getSimpleName();
    private List<ISkinUpdate> mSkinObservers;
    private Context context;
    private Resources mResources;
    private boolean isDefaultSkin = false;
    private String skinPackageName;//skin package name
    private String skinPath;//skin path
    private String saveCustuomPath;

    private SkinManager() {}

    public static SkinManager getInstance() {
        return Holder.SINGLETON;
    }

    private static class Holder{
        private static final SkinManager SINGLETON= new SkinManager();
    }

    public SkinManager init(Context context) {
        this.context = context;
        setUpSkinFile();
        loadSkin();
        return getInstance();
    }


    private void setUpSkinFile() {
        try {
            String[] skinFiles = context.getAssets().list(SkinConstant.SKIN_DIR_NAME);
            for (String fileName : skinFiles) {
                File file = new File(SkinFileUtils.getSkinDir(context), fileName);
                if (!file.exists())
                    SkinFileUtils.copySkinAssetsToDir(context, fileName, SkinFileUtils.getSkinDir(context));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }

    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    public Resources getResources() {
        return mResources;
    }


    public String getCustomPath() {
        if (TextUtils.isEmpty(saveCustuomPath))
               saveCustuomPath =  SkinPreferencesUtils.getString(SkinManager.getInstance().getContext(), SKIN_CUSTOM_PATH, DEFAULT_SKIN);;

        return saveCustuomPath;
    }

    public void saveSkinPath(String path) {
        saveCustuomPath = path;
        SkinPreferencesUtils.putString(SkinManager.getInstance().getContext(), SKIN_CUSTOM_PATH, path);
    }

    //是否是当前皮肤
    public boolean isCurrentSkin() {
        return DEFAULT_SKIN.equals(getCustomPath());
    }

    public void restoreDefaultTheme() {
        saveSkinPath(SkinConstant.DEFAULT_SKIN);
        isDefaultSkin = true;
        mResources = context.getResources();
        skinPackageName = context.getPackageName();
        notifySkinUpdate();
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }
        if (!mSkinObservers.contains(observer)) {
            mSkinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (mSkinObservers == null) return;
        if (mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (mSkinObservers == null) return;
        for (ISkinUpdate observer : mSkinObservers) {
            observer.onSkinUpdate();
        }
    }

    public void loadSkin() {
        loadSkin(null);
    }

    public void loadSkin(SkinLoadListener callback) {
        String skin = getCustomPath();

        if (isCurrentSkin()) {
            return;
        }

        loadSkin(skin, callback);
    }

    public void loadSkin(String skinName, final SkinLoadListener callback) {

        new AsyncTask<String, Void, Resources>() {

            protected void onPreExecute() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Resources doInBackground(String... params) {
                try {
                    if (params.length == 1) {
                        String skinPkgPath = SkinFileUtils.getSkinDir(context) + File.separator + params[0];
                        SkinLUtils.i(TAG, "skinPackagePath:" + skinPkgPath);
                        File file = new File(skinPkgPath);
                        if (!file.exists()) {
                            return null;
                        }
                        PackageManager mPm = context.getPackageManager();
                        PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                        skinPackageName = mInfo.packageName;
                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPkgPath);


                        Resources superRes = context.getResources();
                        Resources skinResource = getResources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                        saveSkinPath(params[0]);

                        skinPath = skinPkgPath;
                        isDefaultSkin = false;
                        return skinResource;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(Resources result) {
                mResources = result;

                if (mResources != null) {
                    if (callback != null) callback.onSuccess();
                    notifySkinUpdate();
                } else {
                    isDefaultSkin = true;
                    if (callback != null) callback.onFailed("没有获取到资源");
                }
            }

        }.execute(skinName);
    }

    public void loadSkinFromUrl(String skinUrl, final SkinLoadListener callback) {
        String skinPath = SkinFileUtils.getSkinDir(context);
        final String skinName = skinUrl.substring(skinUrl.lastIndexOf("/") + 1);
        String skinFullName = skinPath + File.separator + skinName;
        File skinFile = new File(skinFullName);
        if (skinFile.exists()) {
            loadSkin(skinName, callback);
            return;
        }

        Uri downloadUri = Uri.parse(skinUrl);
        Uri destinationUri = Uri.parse(skinFullName);

        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH);
        callback.onStart();
        downloadRequest.setStatusListener(new DownloadStatusListenerV1() {
            @Override
            public void onDownloadComplete(DownloadRequest downloadRequest) {
                loadSkin(skinName, callback);
            }

            @Override
            public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {
                callback.onFailed(errorMessage);
        }

            @Override
            public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                callback.onProgress(progress);
            }
        });

        ThinDownloadManager manager = new ThinDownloadManager();
        manager.add(downloadRequest);


    }

    private Resources getResources(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration) {
        Resources resources= null;
        try {
            resources = new Resources(assetManager, displayMetrics, configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resources;
    }

    private int getSkinThemeResId(int resId, String defType) {
        String resName = context.getResources().getResourceEntryName(resId);
        int trueResId = mResources.getIdentifier(resName, defType, skinPackageName);
        return trueResId;
    }

    private int getResId(int resId,String defType){
        int getResId = getSkinThemeResId(resId,defType);
        return getResId == 0? resId:getResId;
    }

    public int getColor(int resId) {

        if (isExternalSkin()){
            int trueResId = mResources.getIdentifier(context.getResources().getResourceEntryName(resId), "color", skinPackageName);
            if (trueResId != 0) return mResources.getColor(trueResId);
        }
        return ContextCompat.getColor(context, resId);
    }

    public int getColor(String resName) {

        if (isExternalSkin()) {
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            if (trueResId != 0)return mResources.getColor(trueResId);
        }
        return context.getResources().getIdentifier(resName,"color",skinPackageName);

    }

    public ColorStateList getColorStateList(int resId) {
        if (isExternalSkin()) {
            int trueResId = mResources.getIdentifier(context.getResources().getResourceEntryName(resId), "color", skinPackageName);
            if (trueResId != 0) return mResources.getColorStateList(trueResId);
        }
        return context.getResources().getColorStateList(resId);
    }

    public Drawable getDrawable(int resId) {

        if (isExternalSkin()){

            String resName = context.getResources().getResourceEntryName(resId);

            int externalResId = mResources.getIdentifier(resName, "drawable", skinPackageName);

            if (externalResId == 0) externalResId = mResources.getIdentifier(resName, "mipmap", skinPackageName);

            if (externalResId != 0) {

                Drawable trueDrawable;

                if (Build.VERSION.SDK_INT <= 21) {
                    trueDrawable = mResources.getDrawable(externalResId);
                } else {
                    trueDrawable = mResources.getDrawable(externalResId, null);
                }

                return trueDrawable;
            }

        }

        return ContextCompat.getDrawable(context, resId);

    }

    public SkinManager addSupportAttr(String attrName, BaseAttr baseAttr) {
        AttrFactory.addSupportAttr(attrName, baseAttr);
        return getInstance();
    }

}
