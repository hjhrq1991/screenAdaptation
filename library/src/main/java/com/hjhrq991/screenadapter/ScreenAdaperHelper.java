package com.hjhrq991.screenadapter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;

/**
 * @author hjhrq1991 created at 2017/9/11 14 19.
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

    public static ScreenAdaperHelper init(Application application) {
        if (mHelper == null) {
            synchronized (ScreenAdaperHelper.class) {
                return init(application, DEAULT_DESIGN_WIDTH);
            }
        }
        return mHelper;
    }

    /**
     * @param DESIGN_WIDTH the width for design drawing, use dp conversion by default
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
            if (xdpi > 0f && getMetrics(res).xdpi != xdpi)
                getMetrics(res).xdpi = xdpi;
            else if (xdpi <= 0f) setDensity(application, TypedValue.COMPLEX_UNIT_PT);
        }
    }

    /**
     * @param unit {@link TypedValue} Not recommended COMPLEX_UNIT_PX、COMPLEX_UNIT_DIP、COMPLEX_UNIT_SP
     *             recommend COMPLEX_UNIT_PT
     */
    private void setDensity(Context context, int unit) {
        if (context != null) {
            try {
                float xdpi = 0f;
                DisplayMetrics dm = getMetrics(context.getResources());
                switch (unit) {
                    case TypedValue.COMPLEX_UNIT_PT:
                        xdpi = dm.widthPixels / DESIGN_WIDTH * 72;
                        break;
                    case TypedValue.COMPLEX_UNIT_IN:
                        xdpi = dm.widthPixels / DESIGN_WIDTH;
                        break;
                    case TypedValue.COMPLEX_UNIT_MM:
                        xdpi = dm.widthPixels / DESIGN_WIDTH * 25.4f;
                        break;
                }
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putFloat("xdpi", xdpi);
                editor.apply();
                dm.xdpi = xdpi;
            } catch (Exception e) {
            }
        }
    }

    /**
     * To solve some devices use Method "resources.getDisplayMetrics()" and set xdpi no work
     */
    private static DisplayMetrics getMetrics(Resources res) {
        DisplayMetrics dm = null;
        if (res.getClass().getSimpleName().equals("MiuiResources") || res.getClass().getSimpleName().equals("XResources")) {
            try {
                Field field = Resources.class.getDeclaredField("mTmpMetrics");
                field.setAccessible(true);
                dm = (DisplayMetrics) field.get(res);
            } catch (Exception e) {
                dm = null;
            }
        }
        return dm != null ? dm : res.getDisplayMetrics();
    }

    public static int spTopx(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getMetrics(context.getResources()));
    }

    public static int dpTopx(Context context, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getMetrics(context.getResources()));
    }

    public static int ptTopx(Context context, float pt) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, pt, getMetrics(context.getResources()));
    }

    public static int inTopx(Context context, float in) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, in, getMetrics(context.getResources()));
    }

    public static int mmTopx(Context context, float mm) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, getMetrics(context.getResources()));
    }
}
