package com.hjhrq991.screenadapter;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * @author hjhrq1991 created at 2017/9/11 14 19.
 * @Package com.hjhrq991.screenadaptation
 * @Description:
 */

public class ScreenAdapterApplication extends Application {

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


}
