package wcy.godinsec.wcy_dandan.test.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Auther：杨玉安 on 2018/3/29 14:33
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class HandlerService extends Service {
    private Looper mLooper;
    private ServiceHandler mServiceHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class  ServiceHandler extends Handler{
        public ServiceHandler(Looper looper) {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread  thread = new HandlerThread("service", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mLooper);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        return START_STICKY;
    }
}
