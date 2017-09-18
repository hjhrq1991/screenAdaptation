# ScreenAdapter

## [中文文档](https://github.com/hjhrq1991/screenAdaptation/blob/master/README_CN.md)</br>
The ScreenAdapter project comes from the developer when the designer makes fun of the lack of a highly reversion design, and the Android screen has a lot of resolution. It always needs to fine-tune or discard the non-mainstream resolution. The ScreenAdapter is born and has experienced multiple project practices, and the adaptation situation has basically achieved the ideal situation.
   
The ScreenAdapter has the following features:
 + Simple and convenient
   - access is simple, rarely accessed
   - the code and layout are converted to the global entry
 + Better layout
   -dp can be replaced by pt/in/mm
   - you can use the design draft to mark the dimensions directly
   - no layout nesting can be implemented
 + performance better
   - no need to rely on third-party libraries such as AutoLayout
 
[How to use the ScreenAdapter?](#Use)

If you feel that the ScreenAdapter is helpful to you, your star and issues will be the biggest support for me.`^_^`

## Sample
When you look at this design, how do you restore it at all resolutions?
![design](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/design_spec.jpg) 

![effect](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/screenadpater_480270.gif) 

Adaptation case：

Describe|Huawei Honor8|Meizu MX3|Meizu MX2
:----|:----:|:----:|:----:
resolution | 1920\*1080 | 1800\*1080 | 1280\*800
aspect ratio | 16:9 | 15:9 | 16:10
effect | ![honor8](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/honor8_sample1.jpg) | ![mx3](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/mx3_sample1.jpg) | ![mx2](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/mx2_sample1.jpg)

## Download
[![Download](https://api.bintray.com/packages/hjhrq1991/maven/ScreenAdapter/images/download.svg) ](https://bintray.com/hjhrq1991/maven/ScreenAdapter/_latestVersion)
```java
compile 'com.hjhrq991.screenadapter:ScreenAdapter:1.0.2'
```

***
## Use
### No Application
You can use ScreenAdapterApplication in AndroidManifest
```java
   <application
          android:name="com.hjhrq991.screenadapter.ScreenAdapterApplication">
           
      </application>
```

### Has custom Application
If your custom Application extends Application, modify it to extend ScreenAdapterApplication
```java
   public class MyApplication extends ScreenAdapterApplication {
   
   }
```

If your custom Application extends other Application, it can be done by ScreenAdaperHelper. DESIGN_WIDTH is the width of the design draft (if your design draft is not uniform, please find your designer), and the value of DESIGN_WIDTH is recommended to use the result of px, which is px/ 2.
The specific code is as follows:
```java
   private float DESIGN_WIDTH = 375f;
   
       private ScreenAdaperHelper mHelper;
   
       @Override
       public void onCreate() {
           super.onCreate();
           //init helper with default with.
   //        mHelper = ScreenAdaperHelper.init(this);
           //init helper with the width for design drawing
           mHelper = ScreenAdaperHelper.init(this, DESIGN_WIDTH);
       }
   
       @Override
       public void onConfigurationChanged(Configuration newConfig) {
           super.onConfigurationChanged(newConfig);
           mHelper.onConfigurationChanged();
       }
   
       @Override
       public Resources getResources() {
           Resources res = super.getResources();
           //Will call before init
           if (mHelper != null)
               mHelper.getResources(res);
           return res;
       }
```

## Adaptation
#### Layout adaptation
Given the runtime the scheme is valid, so suggest writing layout file is preferred to use dp as a unit, after finished the layout using pt/in/mm global replace dp. Of course, if take the trouble of can in the Android studiod preview function can be used when the simulator equipment preview, fill out the design draft size, good screen size conversion to preview.
It is also recommended to use the FrameLayout or linear layout to minimize the use of RelativeLayout, which can reduce the hierarchy.
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="375pt"
    android:layout_height="240pt"
    android:background="@color/white"
    android:layout_marginBottom="15pt"
    android:orientation="vertical">

    <FrameLayout
        android:id="@+id/layout_left"
        android:layout_width="160pt"
        android:layout_height="240pt">

        <View
            android:layout_width="150pt"
            android:layout_height="150pt"
            android:layout_gravity="bottom|center_horizontal"
            android:layout_margin="5pt"
            android:background="@color/gray" />

        <View
            android:layout_width="match_parent"
            android:layout_height="14pt"
            android:layout_marginLeft="15pt"
            android:layout_marginRight="15pt"
            android:layout_marginTop="10pt"
            android:background="@color/gray" />

        <View
            android:layout_width="90pt"
            android:layout_height="10pt"
            android:layout_marginLeft="15pt"
            android:layout_marginTop="32pt"
            android:background="@color/gray"
            android:gravity="center_vertical" />

        <View
            android:layout_width="60pt"
            android:layout_height="26pt"
            android:layout_marginLeft="15pt"
            android:layout_marginRight="15pt"
            android:layout_marginTop="45pt"
            android:background="@color/gray" />
    </FrameLayout>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/gridview"
        android:layout_width="215pt"
        android:layout_height="240pt"
        android:layout_gravity="right"
        android:listSelector="@color/white" />

    <View
        android:layout_width="0.5pt"
        android:layout_height="match_parent"
        android:layout_marginLeft="160pt"
        android:background="@color/ececec" />

    <View
        android:layout_width="0.5pt"
        android:layout_height="match_parent"
        android:layout_gravity="right"
        android:layout_marginRight="107.5pt"
        android:background="@color/ececec" />

    <View
        android:layout_width="215pt"
        android:layout_height="0.5pt"
        android:layout_gravity="center_vertical|right"
        android:background="@color/ececec" />
        
</FrameLayout>
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="120pt"
    android:paddingBottom="2pt"
    android:paddingLeft="15pt"
    android:paddingRight="15pt"
    android:paddingTop="10pt">

    <View
        android:layout_width="match_parent"
        android:layout_height="14pt"
        android:background="@color/gray" />

    <View
        android:layout_width="match_parent"
        android:layout_height="10pt"
        android:layout_marginRight="15pt"
        android:layout_marginTop="16pt"
        android:background="@color/gray" />

    <View
        android:layout_width="77pt"
        android:layout_height="77pt"
        android:layout_gravity="bottom|center_horizontal"
        android:background="@color/gray" />
</FrameLayout>
```

#### Code adaptation
If you need to write code to convert px, you can call the ScreenAdaperHelper API method:
```java
ScreenAdaperHelper.ptTopx(mContext, 210);
```
Of course, it's ok to call your original method, which is already global.

## Adaptation situation
All devices can be highly restored to the design draft effect. The layout USES pt/in/mm to replace dp and sp, but the dp and sp are retained with high frequency of use.
The horizontal vertical screen switching will be converted to the current screen width, such as your layout non-list or Scrollview, which suggests that the horizontal vertical screen should be adapted with different layout.

 + v1.0.0
   - most of the models have been adapted to most models such as huawei, meizu, vivo, oppo, samsung, one plus, zte, coolpad, hammer, Letv, etc
   - solve the problem that DisplayMetrics is reset in some cases
 + v1.0.1
   - optimize the access method for visual area width
 + v1.0.2
   - fix the problem of the failure of the adaptation programme under the xiaomi android 5.1.1
   - add code to convert px tools
   
Current unresolved issues：For example, huawei mobile phone, in the case of horizontal screen, hide/show the navigation bar does not have a good way to monitor, and it will not trigger page refresh. Forcing the UI to refresh the UI is bound to waste performance.
                          Of course, if you need to deal with this problem, you can redraw the UI by yourself in the Activity listening to the changes in android. Id. R.content width.
                          If you have a good solution to this problem, please give me feedback! Thank you!

## Other
If you have any questions, please give me feedback on [issues](https://github.com/hjhrq1991/screenAdaptation/issues)</br>
Or contact me in other ways：</br>
Gmail：hjhrq1991@gmail.com</br>
QQ：444563258</br>

***
License
-------

[Apache 2.0](LICENSE)
