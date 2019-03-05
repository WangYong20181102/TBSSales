package com.tbs.sales.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mr.Wang on 2019/3/4 14:16.
 */
public class DateTimeUtils {
    //获取当前的时间
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mTime = format.format(date);
        return mTime;
    }

    /**
     * 获取年月
     * @return
     */
    public static String getYYMM(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date).toString();
        return time;
    }
    /**
     * 判断是否有SD卡且是否可读写
     * creat 0118
     */
    public static boolean hasSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
