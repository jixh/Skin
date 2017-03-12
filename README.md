
#Android 主题换肤-给要换肤的view打标签
给页面中需要的view设置标签来适配换肤


##更新日志


##效果图

![Demo](skin.gif)


##支持

1) 支持 textColor,textColorHint,background,src,drawableTop、drawableLeft、drawableRight、drawableBottom 属性的引用资源替换

2) 可以自定义属性

3) xml或者代码的方式的设置

4) 动态下载皮肤包


##使用步骤

1) 导入包
```


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



###自定义属性添加
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

##LICENSE

```html
Copyright [2017] [jktaihe]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
