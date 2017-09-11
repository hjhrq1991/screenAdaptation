package com.hjhrq991.screenadaptation.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * @author hjhrq1991 created at 2017/9/11 14 19.
 * @Package com.hjhrq991.screenadaptation
 * @Description:
 */

public class MyApplication extends Application {

    //绘制页面时参照的设计图宽度(以竖图为基准)
    public final static float DESIGN_WIDTH = 375;

    @Override
    public void onCreate() {
        super.onCreate();
        //如果app一启动就开始使用适配方案，请在onCreate里就先调用一次
        resetDensity(this, TypedValue.COMPLEX_UNIT_PT);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //屏幕发生变化时重置
        getResources();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        //解决某种情况下DisplayMetrics属性值被还原的问题
        SharedPreferences sp = this.getSharedPreferences("app_settings", MODE_PRIVATE);
        float xdpi = sp.getFloat("xdpi", 0f);
        if (xdpi > 0f && res.getDisplayMetrics().xdpi != xdpi) res.getDisplayMetrics().xdpi = xdpi;
        else if (xdpi <= 0f) resetDensity(this, TypedValue.COMPLEX_UNIT_PT);
        return res;
    }

    /**
     * @param context 上下文，最好传递context
     * @param unit    {@link TypedValue} 不建议使用COMPLEX_UNIT_PX、COMPLEX_UNIT_DIP、COMPLEX_UNIT_SP
     *                首推COMPLEX_UNIT_PT
     */
    public static void resetDensity(Context context, int unit) {
        SharedPreferences sp = context.getSharedPreferences("app_settings", MODE_PRIVATE);
        float xdpi = sp.getFloat("xdpi", 0f);
        if (xdpi <= 0f) {
            Point size = new Point();
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
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
            //解决某种情况下DisplayMetrics属性值被还原的问题
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("xdpi", xdpi);
            editor.apply();
        }
        context.getResources().getDisplayMetrics().xdpi = xdpi;
    }
}
