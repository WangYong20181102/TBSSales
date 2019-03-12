package com.tbs.sales.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mr.Wang on 2019/3/8 16:49.
 */
public class ToastUtils {
    /**
     * short
     */
    public static void toastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * long
     */
    public static void toastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
