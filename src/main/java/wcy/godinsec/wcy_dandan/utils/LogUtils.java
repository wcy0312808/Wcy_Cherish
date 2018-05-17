package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import wcy.godinsec.wcy_dandan.BuildConfig;


/**
 * Auther：杨玉安 on 2017/9/1 13:37
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class LogUtils {

    private static final String HEAD_TAG = "wcy_yangyuan";

    private static boolean isDebug = BuildConfig.DEBUG;


    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    private static String createLog(String logMessage) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("==(").append(className).append(":").append(lineNumber).append(")==");
        buffer.append(logMessage);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void v(String logInfo) {

        if (!isDebug)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(HEAD_TAG +className, createLog(logInfo));
    }



    public static void i(String logInfo) {

        if (!isDebug)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(HEAD_TAG +className, createLog(logInfo));
    }


    public static void d(String logInfo) {

        if (!isDebug)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(HEAD_TAG +className, createLog(logInfo));
    }


    public static void w( String logInfo) {

        if (!isDebug)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(HEAD_TAG +className, createLog(logInfo));
    }

    public static void e( String logInfo) {

        if (!isDebug)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(HEAD_TAG +className, createLog(logInfo));
    }
}