package com.hjhrq991.screenadaptation.Application;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;

import com.hjhrq991.screenadapter.ScreenAdaperHelper;

/**
 * @author hjhrq1991 created at 2017/9/11 14 19.
 * @Package com.hjhrq991.screenadaptation
 * @Description:
 */

public class MyApplication extends Application {

    private float DESIGN_WIDTH = 375f;
    private ScreenAdaperHelper mHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        //init helper with the width for design drawing
        mHelper = new ScreenAdaperHelper.Builder()
                .setApplication(this)
                .setDesign_with(DESIGN_WIDTH)
                .setUnit(TypedValue.COMPLEX_UNIT_PT)
                .setEnableDp(true)
                .setEnableOtherResources(true)
                .build();
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
}
