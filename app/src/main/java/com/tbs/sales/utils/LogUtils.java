package com.tbs.sales.utils;

import android.util.Log;

/**
 * Created by Mr.Wang on 2019/3/11 11:44.
 */
public class LogUtils {
    private static final String TAG = "TBSSales";

    public static void logE(String msg) {
        Log.e(TAG, msg);
    }

    public static void logV(String msg) {
        Log.v(TAG, msg);
    }

    public static void logD(String msg) {
        Log.d(TAG, msg);
    }

    public static void logI(String msg) {
        Log.i(TAG, msg);
    }

    public static void logW(String msg) {
        Log.w(TAG, msg);
    }
}
