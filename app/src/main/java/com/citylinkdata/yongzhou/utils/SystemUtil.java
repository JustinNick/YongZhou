package com.citylinkdata.yongzhou.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.util.Locale;

/**
 * Created by liqing on 2017/11/2.
 */

public class SystemUtil {

    /**
     * 系统工具类
     * Created by zhuwentao on 2016-07-18.
     */

//    <uses-permission android:name="android.permission.READ_PHONE_STATE" />


    /**
         * 获取当前手机系统语言。
         *
         * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
         */
        public static String getSystemLanguage() {
            return Locale.getDefault().getLanguage();
        }

        /**
         * 获取当前系统上的语言列表(Locale列表)
         *
         * @return  语言列表
         */
        public static Locale[] getSystemLanguageList() {
            return Locale.getAvailableLocales();
        }

        /**
         * 获取当前手机系统版本号
         *
         * @return  系统版本号
         */
        public static String getSystemVersion() {
            return android.os.Build.VERSION.RELEASE;
        }

        /**
         * 获取手机型号
         *
         * @return  手机型号
         */
        public static String getSystemModel() {
            return android.os.Build.MODEL;
        }

        /**
         * 获取手机厂商
         *
         * @return  手机厂商
         */
        public static String getDeviceBrand() {
            return android.os.Build.BRAND;
        }

        /**
         * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
         *
         * @return  手机IMEI
         */
        public static String getIMEI(Context ctx) {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null) {
                return tm.getDeviceId();
            }
            return null;
        }

        private void showSystemParameter() {
            String TAG = "系统参数：";
            L.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
            L.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
            L.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
            L.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
//            Log.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
        }


    /**
     * 获取屏幕的默认分辨率
     * 通过Resources获取
     */
    public static String getScreenDensity_ByResources(Context ctx){
        DisplayMetrics mDisplayMetrics = ctx.getResources().getDisplayMetrics();
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        L.d("Screen Ratio: ["+width+"x"+height+"],density="+density+",densityDpi="+densityDpi);
        L.d("Screen mDisplayMetrics: "+mDisplayMetrics);
        return width+"x"+height;

    }

    /**
     *  获取版本名称
     *  @return 当前应用的版本名称
     */
    public static String getVersionName(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String version_name = info.versionName;
            int version_code = info.versionCode;
            return version_name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 版本号
     * @return  当前应用的版本号
     */
    public static int getVersionCode(Context ctx) {
        try {
            PackageManager manager = ctx.getPackageManager();
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String version_name = info.versionName;
            int version_code = info.versionCode;
            return version_code;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

