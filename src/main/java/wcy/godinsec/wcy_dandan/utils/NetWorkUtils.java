package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Auther：杨玉安 on 2018/5/2 10:39
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：获取当前网络类型
 *
 */
public class NetWorkUtils {
        public static final int NETTYPE_WIFI = 1;
        public static final int NETTYPE_MOBILE = 0;
        public static final int NETTYPE_NULL = -1;
        /**
         * 检查当前网络是否可用,包含任何形式网络
         *
         * @param context
         * @return
         */
        public static boolean isNetworkAvailable(Context context) {
            // 获取手机所有连接管理对象（包括对wi-fi,data net等连接的管理）
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {
                return true;
            }
            return false;
        }

        //获取当前网络模式不可用:-1， 数据流量：0, WiFi：1
        public static int netMode(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo == null || !netInfo.isAvailable()) {
                return NETTYPE_NULL;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETTYPE_WIFI;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return NETTYPE_MOBILE;
            }
            return -1;
        }
}
