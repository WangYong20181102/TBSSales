package com.tbs.sales.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.tbs.sales.MainActivity;

import java.util.Stack;

/**
 * Created by Mr.Wang on 2019/2/27 17:04.
 */
public class AppManager {
    private static AppManager manager;
    private Stack<Activity> activityStack;

    public static AppManager getInstances() {
        if (manager == null) {
            manager = new AppManager();
        }
        return manager;
    }

    /**
     * 添加activity到栈堆
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param clz
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isActivityExist(Class<MainActivity> clz) {
        boolean res;
        Activity activity = null;
        for (int i = 0; i < activityStack.size(); i++) {
            if (clz.equals(activityStack.get(i).getClass())) {
                activity = activityStack.get(i);
                break;
            } else {
                activity = null;
            }
        }
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断一个Activity 是否存在
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Activity judgeActivityExist(Class<MainActivity> clz) {
        Activity activity = null;
        for (int i = 0; i < activityStack.size(); i++) {
            if (clz.equals(activityStack.get(i).getClass())) {
                activity = activityStack.get(i);
                break;
            } else {
                activity = null;
            }
        }
        return activity;
    }

    /**
     * 结束当前activity
     *
     * @param clz
     */
    public void finishCurrentActivity(Class<MainActivity> clz) {
        if (isActivityExist(clz)) {
            Activity activity = judgeActivityExist(clz);
            activity.finish();
        }
    }
}
