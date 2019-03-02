package com.tbs.sales.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

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
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            //友盟统计---->如果开发者调用Process.kill或者System.exit之类的方法杀死进程，
            //请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，用来保存统计数据。
//            MobclickAgent.onKillProcess(context);

            System.exit(0);
        } catch (Exception e) {
        }
    }
}
