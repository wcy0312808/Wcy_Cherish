package wcy.godinsec.wcy_dandan.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;

import static android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES;

/**
 * Auther：杨玉安 on 2018/2/9 20:29
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AppUtils {

    //根据意图，跳转到目标APP
    public static void startTagApp(Context context, String pkg) {
        long availSpace = AppUtils.getAvailSpace(Environment.getDataDirectory().getAbsolutePath());
        if (availSpace < 100 * 1024 * 1024) {//内存小于100M提示用户内存不足
            ToastUtils.showShortToast(context.getResources().getString(R.string.memory_full_not_run));
        }
        Intent socialApp_intent = AppUtils.getIntent(pkg, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        if (socialApp_intent == null) {
            return;
        }
        WcyApplication.getInstance().startActivity(socialApp_intent);
        DownLoadSQLManager.getInstance().updateLastTimeByPkg(pkg);//更新应用最后使用时间
    }

    //根据包名获取到应该跳转的意图
    public static Intent getIntent(String packageName, int launchFlags) {
        Intent intent = WcyApplication.getInstance().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return null;
        }
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        return intent;
    }

    // 安装App   path 代表的是路径
    public static void install(String path, String appName) {
        if (path == null) {
            ToastUtils.showLongToastSafe("安装地址失败");
        }
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        WcyApplication.getInstance().startActivity(intent);
    }

    /**
     * 获取apk的MD5值
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }


    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 应用是否被安装
     */
    public static PackageInfo isAppInstalled(String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = WcyApplication.getInstance().getPackageManager().getPackageInfo(packagename, GET_UNINSTALLED_PACKAGES);
        } catch (Exception e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return null;
        } else {
            return packageInfo;
        }
    }

    /**
     * 获取可用内存空间
     */
    public static long getAvailSpace(String path) {
        //获取可用内存大小
        StatFs statfs = new StatFs(path);
        //获取可用区块的个数
        long count = statfs.getAvailableBlocksLong();
        //获取区块大小
        long size = statfs.getBlockSizeLong();
        //可用空间总大小
        return count * size;
    }

    /**
     * 删除文件夹
     */
    public static void deleteFile(String path) {
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }


    /**
     * MD5加密
     */
    public static String MD5(String string) {
        if (android.text.TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保留一位小数
     */
    public static String formatFloat(float number, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);
    }

    public static void transfiguration(String activity_alias, Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        // 使ACTIVITY_ALIAS_1失效
        pm.setComponentEnabledSetting(
                new ComponentName(ctx, Constance.ACTIVITY_ALIAS_1),
                Constance.ACTIVITY_ALIAS_1.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
        // 使ACTIVITY_ALIAS_2生效
        pm.setComponentEnabledSetting(
                new ComponentName(ctx, Constance.ACTIVITY_ALIAS_2),
                Constance.ACTIVITY_ALIAS_2.equals(activity_alias) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        ActivityManager am = (ActivityManager) ctx.getSystemService(Activity.ACTIVITY_SERVICE);
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
    }

    /*
    * 获取到进程名称
    */
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
