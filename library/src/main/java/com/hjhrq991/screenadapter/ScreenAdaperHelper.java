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
    /**
     * {@link TypedValue}
     */
    private int unit;
    /**
     * Enable dp transformation.
     * default : disable
     */
    private boolean enableDp = false;
    /**
     * Enable the solution of MIUI or Xpose resource issues.
     * defalut : enable
     */
    private boolean enableOtherResources = true;

    /**
     * @param DESIGN_WIDTH the width for design drawing, use dp conversion by default
     */
    private static ScreenAdaperHelper init(Application application, float DESIGN_WIDTH, int unit, boolean enableDp, boolean enableOtherResources) {
        if (mHelper == null) {
            synchronized (ScreenAdaperHelper.class) {
                if (mHelper == null) {
                    mHelper = new ScreenAdaperHelper(application, DESIGN_WIDTH, unit, enableDp, enableOtherResources);
                }
            }
        }
        return mHelper;
    }

    private ScreenAdaperHelper(Application application, float DESIGN_WIDTH, int unit, boolean enableDp, boolean enableOtherResources) {
        this.application = application;
        this.DESIGN_WIDTH = DESIGN_WIDTH > 0 ? DESIGN_WIDTH : DEAULT_DESIGN_WIDTH;
        this.unit = unit;
        this.enableDp = enableDp;
        this.enableOtherResources = enableOtherResources;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        //If the app starts using the adaptation plan on startup, it needs to be called once at initialization time
        setDensity(application, unit);
    }

    public void onConfigurationChanged() {
        //Reset when the screen changes
        setDensity(application, unit);
    }

    public void getResources(Resources res) {
        if (application != null) {
            //To solve the problem that the DisplayMetrics attribute value is restored in some cases
            float xdpi = mSharedPreferences.getFloat("xdpi", 0f);
            if ((res.getClass().getSimpleName().contains("MiuiResources") || res.getClass().getSimpleName().contains("XResources")) && !enableOtherResources)
                return;
            if (xdpi > 0f && getMetrics(res).xdpi != xdpi) {
                if (unit == TypedValue.COMPLEX_UNIT_DIP) {
                    if (enableDp)
                        getMetrics(res).density = xdpi;
                } else {
                    getMetrics(res).xdpi = xdpi;
                }
            } else if (xdpi <= 0f) setDensity(application, unit);
        }
    }

    /**
     * @param unit {@link TypedValue} Not recommended COMPLEX_UNIT_PX、COMPLEX_UNIT_DIP、COMPLEX_UNIT_SP
     *             recommend COMPLEX_UNIT_PT
     */
    private void setDensity(Context context, int unit) {
        if (context != null) {
            try {
                Resources res = context.getResources();
                if ((res.getClass().getSimpleName().contains("MiuiResources") || res.getClass().getSimpleName().contains("XResources")) && !enableOtherResources)
                    return;
                float xdpi = 0f;
                DisplayMetrics dm = getMetrics(context.getResources());
                if (dm == null) return;
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
                    case TypedValue.COMPLEX_UNIT_DIP:
                        xdpi = dm.widthPixels / DESIGN_WIDTH;
                        break;
                }
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putFloat("xdpi", xdpi);
                editor.apply();
                if (unit == TypedValue.COMPLEX_UNIT_DIP) {
                    if (enableDp)
                        dm.density = xdpi;
                } else {
                    dm.xdpi = xdpi;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To solve some devices use Method "resources.getDisplayMetrics()" and set xdpi no work
     */

    private static DisplayMetrics getMetrics(Resources res) {
        DisplayMetrics dm = null;
        if (res.getClass().getSimpleName().contains("MiuiResources") || res.getClass().getSimpleName().contains("XResources")) {
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

    public static class Builder {
        private float design_with;
        /**
         * {@link TypedValue}
         */
        private int unit;
        /**
         * Enable dp transformation when the unit is TypedValue.COMPLEX_UNIT_DIP.
         * default : disable
         */
        private boolean enableDp = false;
        /**
         * Enable the solution of MIUI or Xpose resource issues.
         * defalut : enable
         */
        private boolean enableOtherResources = true;
        private Application application;

        public Builder setDesign_with(float design_with) {
            this.design_with = design_with;
            return this;
        }

        public Builder setUnit(int unit) {
            this.unit = unit;
            return this;
        }

        public Builder setEnableDp(boolean enableDp) {
            this.enableDp = enableDp;
            return this;
        }

        public Builder setEnableOtherResources(boolean enableOtherResources) {
            this.enableOtherResources = enableOtherResources;
            return this;
        }

        public Builder setApplication(Application application) {
            this.application = application;
            return this;
        }

        public ScreenAdaperHelper build() {
            if (application == null) throw new NullPointerException("Application must not null.");
            return ScreenAdaperHelper.init(application, design_with, unit, enableDp, enableOtherResources);
        }

        public ScreenAdaperHelper build(Application application) {
            this.application = application;
            if (application == null)
                throw new NullPointerException("Application must be not null.");
            return ScreenAdaperHelper.init(application, design_with, unit, enableDp, enableOtherResources);
        }
    }
}
