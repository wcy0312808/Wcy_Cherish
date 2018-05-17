package wcy.godinsec.wcy_dandan.test.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/4/20 11:26
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：配合 计数service联系的
 */
public class CountReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("=========="+intent.getIntExtra(Constance.COUNT_VALUE,0));
    }
}
