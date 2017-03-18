
Android 主题换肤-给要换肤的view打标签
==
给页面中需要的view设置标签来适配换肤


更新日志
--

效果图
--
 <img src="skin.gif" width = "230" height = "370" alt="图片名称"/>


支持
--

1) 支持 textColor,textColorHint,background,src,drawableTop、drawableLeft、drawableRight、drawableBottom 属性的引用资源替换

2) 可以自定义属性

3) xml或者代码的方式的设置

4) 动态下载皮肤包


使用步骤
--
1) 导入包

```gradle

   compile 'com.jktaihe.skin:skinlibrary:1.0.0'

```
2) Application初始化
```

   public class SkinApplication extends Application {
       @Override
       public void onCreate() {
           super.onCreate();

           SkinManager.getInstance()
                   .init(this)
                   .addSupportAttr("tabIndicatorColor", new TabLayoutIndicatorAttr());
       }
   }


```




3) 继承 SkinActivity,SKinFragment

```

    public class MainActivity extends SkinActivity {

    }

```
4) xml或代码的方式设置要换肤view

xml方式：
```xml

<!--
1,添加申明

  xmlns:skins="http://schemas.android.com/android/skins"

2,添加标示

  skins:set="属性名|属性名..."

  eg: skins:set="textColor"  多个属性添加 skins:set="textColor|background"

-->



<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:skins="http://schemas.android.com/android/skins"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >

         <Button
            style="@style/btnStyle"
            android:layout_width="wrap_content"
            android:layout_weight="wrap_content"
            android:text="恢复默认皮肤"
            skins:set="textColor"
            />

</LinearLayout>
```

代码方式：
```
        setSkinViewAttr(tv,"textColor", R.color.item_tv_title_color);
```



自定义属性添加
--

1) 继承 BaseAttr类 ，实现其方法

```

public class TabLayoutIndicatorAttr extends BaseAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TabLayout) {
            TabLayout tl = (TabLayout) view;
            if (isColor()) {
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                tl.setSelectedTabIndicatorColor(color);
            }
        }
    }
}



```



2) 在Application初始化时添加
```
   SkinManager.getInstance()
                .init(this)
                .addSupportAttr("tabIndicatorColor", new TabLayoutIndicatorAttr());

```

3) 按 使用步骤 中第三步方式使用

加载皮肤包
--

1) 本地皮肤包加载  SkinManager.getInstance().loadSkin（皮肤包路径，SkinLoadListener）

```
     SkinManager
       .getInstance()
       .loadSkin("skin_style.skin"
                  ,new SkinLoadListener() {
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
                            Log.i("SkinLoadListener", "皮肤包下载中:" + progress);

                        }
                    }

            );


```

2) 网络皮肤包加载  SkinManager.getInstance().loadSkinFromUrl（皮肤包路径，SkinLoadListener）

```
    SkinManager.getInstance().loadSkinFromUrl(skinUrl, new SkinLoadListener() {
                        @Override
                        public void onStart() {
                            Log.i("SkinLoadListener", "开始切换中");
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
                            Log.i("SkinLoadListener", "皮肤包下载中:" + progress);
                            dialog.setProgress(progress);
                        }
                    });


```