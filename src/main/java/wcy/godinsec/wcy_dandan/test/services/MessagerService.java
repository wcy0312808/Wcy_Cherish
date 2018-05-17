package wcy.godinsec.wcy_dandan.test.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/3/29 16:00
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 通过Messager + Handler 来实现service和绑定组件之间的联系
 */
public class MessagerService extends Service {
    private final int MESSGE_ONE = 1;
    private Messenger mMessenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class ServiceHandler extends Handler {
        public ServiceHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSGE_ONE:
                    LogUtils.e("====MESSGE_ONE====");
                    break;
            }
        }
    }
}
