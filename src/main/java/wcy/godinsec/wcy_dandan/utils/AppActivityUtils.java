package wcy.godinsec.wcy_dandan.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */

public class AppActivityUtils {
    private static Stack<Activity> activityStack;
    private static AppActivityUtils instance;

    private AppActivityUtils() {
    }

    /**
     * 单一实例
     */
    public static AppActivityUtils getAppActivityUtils() {
        if (instance == null) {
            synchronized (AppActivityUtils.class) {
                if (instance == null) {
                    instance = new AppActivityUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        Activity activity = activityStack.lastElement();
        LogUtils.e( "=getCurrentActivity=" + activity.getLocalClassName());
        return activity;
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishLastActivity() {
        Activity activity = activityStack.lastElement();
        LogUtils.e(  "=finishLastActivity=" + activity.getLocalClassName());
        removeActivity(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        LogUtils.e(  "removeActivity  ==   " + activity.getLocalClassName());
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                LogUtils.e( "=finishActivity=" + activity.getLocalClassName());
                removeActivity(activity);
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null || activityStack.size() == 0) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && !activityStack.get(i).isFinishing()) {
                LogUtils.e(  "=finishAllActivity=" + activityStack.get(i).getLocalClassName());
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}

