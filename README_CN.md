# ScreenAdapter

## [ENGLISH DOC](https://github.com/hjhrq1991/screenAdaptation/blob/master/README.md)</br>
ScreenAdapter项目源于开发时老被设计狮吐槽没有高度还原设计稿，加上Android屏幕分辨率众多，总是需要微调或舍弃非主流分辨率的适配。ScreenAdapter由此而生，经历了多个项目的测试，适配情况基本达到理想情况。

ScreenAdapter有以下特点：
 + 简单、方便
   - 接入简单，极少侵入
   - 代码、布局换算px全局生效
 + 布局更优
   - dp可以直接替换为pt/in/mm
   - 可直接使用设计稿标注尺寸
   - 无须布局嵌套即可实现
 + 性能更优
   - 无须依赖AutoLayout之类的第三方库
 
[怎样使用ScreenAdapter?](#使用)

如果你觉得ScreenAdapter对你有帮助，您的star和issues将是对我最大支持.`^_^`

## 示例
当你看到此图时，你会如何在各种分辨率下还原效果？
![设计图](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/design_spec.jpg) 

描述|华为荣耀8|魅族MX3|魅族MX2
----|----|----|----
分辨率 | 1920\*1080 | 1800\*1080 | 1280\*800
宽高比 | 16:9 | 15:9 | 16:10
效果图 | ![荣耀8](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/honor8_sample2.jpg) | ![mx3](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/mx3_sample2.jpg) | ![mx2](https://github.com/hjhrq1991/screenAdaptation/blob/master/screenshot/mx2_sample2.jpg)

## 下载
[![Download](https://api.bintray.com/packages/hjhrq1991/maven/ScreenAdapter/images/download.svg) ](https://bintray.com/hjhrq1991/maven/ScreenAdapter/_latestVersion)
```java
compile 'com.hjhrq991.screenadapter:ScreenAdapter:1.0.0'
```

***
## 使用
### 无Application
可在AndroidManifest使用ScreenAdapterApplication
```java
   <application
          android:name="com.hjhrq991.screenadapter.ScreenAdapterApplication">
           
      </application>
```

### 有自定义Application
如你的自定义Application继承Application，修改成继承ScreenAdapterApplication即可
```java
   public class MyApplication extends ScreenAdapterApplication {
   
   }
```

如你的自定义Application继承其他Application，可通过ScreenAdaperHelper来实现。DESIGN_WIDTH为设计稿的宽度(如果你的设计稿宽度不统一，请找你们的设计狮)，DESIGN_WIDTH的值建议使用px换算dp的结果，即px/2。
具体实现如下：
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

## 适配
由于本方案运行时才生效，因此建议写布局文件时优先使用dp做为单位，写完布局后使用pt/in/mm全局替换dp即可。
同时建议多使用FrameLayout，适当使用LinearLayout，尽量少用RelativeLayout，能更大程度减少层级。
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

## 适配情况
各款设备均能高度还原设计稿效果，布局使用pt/in/mm代替dp、sp，dp、sp由于使用频率较高继续保留。
横竖屏切换时会以当前的屏幕宽度进行换算，如你的布局非列表或者Scrollview，建议横竖屏使用不同的layout进行适配。

当前未解决问题：华为等有可动态导航栏的设备，横屏情况下，收起/展开导航栏并没有好的方式监听，且不会触发页面刷新，强行刷新UI势必浪费性能。
如您对此问题有好的解决方法，请留言反馈给我！谢谢！

## 其他
 有任何问题，可以在[issues](https://github.com/hjhrq1991/screenAdaptation/issues)给我留言反馈。</br>
或其他方式联系我：
Gmail：hjhrq1991@gmail.com
QQ：444563258

***
License
-------

[Apache 2.0](LICENSE)
