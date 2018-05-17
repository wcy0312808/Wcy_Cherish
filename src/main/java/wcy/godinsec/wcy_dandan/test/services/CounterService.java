package wcy.godinsec.wcy_dandan.test.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.test.broadcastreceiver.CountReceiver;

/**
 * Auther：杨玉安 on 2017/11/1 16:16
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 * https://blog.csdn.net/javazejian/article/details/52709857
 * https://www.jianshu.com/p/4c798c91a613
 * https://blog.csdn.net/xuefu_78/article/details/64127844
 */
public class CounterService extends Service {

    private boolean stop = false;
    private final IBinder mIBinder = new CounterBinder();
    private CountReceiver mCountReceiver;

    /**
     * 创建一个类继承Binder，里面拥有一个返回当前Service的公共方法
     * Binder 实现了IBinder，所以可以那Binder实例化出来IBinder
     * 此方法用来返回一个Service对象，用来实现绑定启动
     * 提供数据交换的接口
     */
    public class CounterBinder extends Binder {
        public CounterService getService() {
            return CounterService.this;
        }
    }

    public class CounterBinder2 extends Binder{
        public CounterService getService()
        {
            return CounterService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind ==  ");
        return mIBinder;
    }

    //首次创建的时候才会执行
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("onCreate ==  ");
        mCountReceiver = new CountReceiver();
        //这是动态注册广播，CounterService.BROADCAST_COUNT_ACTION这个是广播的识别码，到时候通过Intent（）
        IntentFilter intentFilter = new IntentFilter(Constance.BROADCAST_COUNT_ACTION);
        //注册广播，并通过intentFilter来识别是是那个广播发来的，广播的接收器就是Recevice.
        registerReceiver(mCountReceiver, intentFilter);
    }

    /**
     * 在service中如果想要停止当前服务，可以调用stopSelf()方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy ==  ");
        unregisterReceiver(mCountReceiver);
    }

    //解除绑定时调用
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("onUnbind ==  ");
        return super.onUnbind(intent);
    }

    /**
     * 每次执行都会执行一遍
     * 它与startService对应，这里就必须手动调动stopService来结束它。
     * 返回一个整型值，描述的是当系统被杀掉服务后，系统该如何继续Service
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand ==  ");
        return super.onStartCommand(intent, flags, startId);
    }

    public void startCounter(int initVal) {
        AsyncTask<Integer, Integer, Integer> task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... vals) {
                Integer initCounter = vals[0];
                stop = false;

                while (!stop) {
                    //initCounter就是传入下面Update的参数，当前方法是一个跨线程的
                    publishProgress(initCounter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    initCounter++;
                }
                return initCounter;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int counter = values[0];
                //发送广播，携带的参数就是广播接受者识别的参数,跳到对应的broadcast
                Intent intent = new Intent(Constance.BROADCAST_COUNT_ACTION);
                //可以携带相应的参数
                intent.putExtra(Constance.COUNT_VALUE, counter);
                sendBroadcast(intent);
            }

            @Override
            protected void onPostExecute(Integer val) {
                int counter = val;
                Intent intent = new Intent(Constance.BROADCAST_COUNT_ACTION);
                intent.putExtra(Constance.COUNT_VALUE, counter);
                sendBroadcast(intent);
            }
        };

        task.execute(0);
    }

    public void stopCounter() {
        stop = true;
    }
}
