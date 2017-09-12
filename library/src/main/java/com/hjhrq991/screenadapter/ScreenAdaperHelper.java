package com.hjhrq991.screenadapter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @author hjhrq1991 created at 2017/9/11 14 19.
 * @Description:
 */
public class ScreenAdaperHelper {

    /**
     * The default width for design drawing.The default is 375dp / 750px.
     * The width of the vertical screen for the benchmark
     */
    private static float DEAULT_DESIGN_WIDTH = 375f;
    private float DESIGN_WIDTH;

    static ScreenAdaperHelper mHelper;
    private Application application;
    private SharedPreferences mSharedPreferences;
    private Point size;

    public static ScreenAdaperHelper init(Application application) {
        if (mHelper == null) {
            synchronized (ScreenAdaperHelper.class) {
                return init(application, DEAULT_DESIGN_WIDTH);
            }
        }
        return mHelper;
    }

    /**
     * @param application
     * @param DESIGN_WIDTH the width for design drawing, use dp conversion by default
     * @return
     */
    public static ScreenAdaperHelper init(Application application, float DESIGN_WIDTH) {
        if (mHelper == null) {
            synchronized (ScreenAdaperHelper.class) {
                if (mHelper == null) {
                    mHelper = new ScreenAdaperHelper(application, DESIGN_WIDTH);
                }
            }
        }
        return mHelper;
    }

    private ScreenAdaperHelper(Application application, float DESIGN_WIDTH) {
        this.DESIGN_WIDTH = DESIGN_WIDTH;
        this.application = application;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        this.size = new Point();
        //If the app starts using the adaptation plan on startup, it needs to be called once at initialization time
        setDensity(application, TypedValue.COMPLEX_UNIT_PT);
    }

    public void onConfigurationChanged() {
        //Reset when the screen changes
        setDensity(application, TypedValue.COMPLEX_UNIT_PT);
    }

    public void getResources(Resources res) {
        if (application != null) {
            //To solve the problem that the DisplayMetrics attribute value is restored in some cases
            float xdpi = mSharedPreferences.getFloat("xdpi", 0f);
            if (xdpi > 0f && res.getDisplayMetrics().xdpi != xdpi)
                res.getDisplayMetrics().xdpi = xdpi;
            else if (xdpi <= 0f) setDensity(application, TypedValue.COMPLEX_UNIT_PT);
        }
    }

    /**
     * @param context
     * @param unit    {@link TypedValue} Not recommended COMPLEX_UNIT_PX、COMPLEX_UNIT_DIP、COMPLEX_UNIT_SP
     *                recommend COMPLEX_UNIT_PT
     */
    private void setDensity(Context context, int unit) {
        if (context != null) {
            float xdpi = 0f;
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
            switch (unit) {
                case TypedValue.COMPLEX_UNIT_PT:
                    xdpi = size.x / DESIGN_WIDTH * 72;
                    break;
                case TypedValue.COMPLEX_UNIT_IN:
                    xdpi = size.x / DESIGN_WIDTH;
                    break;
                case TypedValue.COMPLEX_UNIT_MM:
                    xdpi = size.x / DESIGN_WIDTH * 25.4f;
                    break;
            }
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putFloat("xdpi", xdpi);
            editor.apply();
            context.getResources().getDisplayMetrics().xdpi = xdpi;
        }
    }
}
