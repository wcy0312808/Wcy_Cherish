package wcy.godinsec.wcy_dandan.views.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.List;

import wcy.godinsec.wcy_dandan.utils.BeanToString;
import wcy.godinsec.wcy_dandan.utils.DateUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.SharedPreferencesUtils;

/**
 * Auther：杨玉安 on 2017/11/23 10:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DataChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_TIME_TICK.equals(action)) {

        }
    }

}
