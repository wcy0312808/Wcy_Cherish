package wcy.godinsec.wcy_dandan.test.activitys;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.test.services.CounterService;
import wcy.godinsec.wcy_dandan.test.services.IntentService;
import wcy.godinsec.wcy_dandan.test.services.MessagerService;

/**
 * Auther：杨玉安 on 2018/3/26 14:12
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class One_ServiceActivity extends BaseActivity {
    @BindView(R.id.start)
    TextView mStart;
    @BindView(R.id.stop)
    TextView mStop;
    /**
     * 代表与服务的连接，他只有两个方法
     * 前者是在炒作在连接一个服务成功时被调用，而后者是在服务奔溃或者被杀死导致的连接中断时被调用
     */
    private ServiceConnection mBinderServiceConnection, mBinderServiceMessageConnection;
    private CounterService mCounterService;



    private Intent mBinderService, mBindMessagerService;
    private Messenger mMessenger;  //代表着绑定service的对象

    @Override
    protected int setContentlayout() {
        return R.layout.activity_service_one;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**************************************************************  == startService ==  *********************************************************************************/
        mBindMessagerService = new Intent(this, MessagerService.class);
        mBinderServiceMessageConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMessenger = new Messenger(service);
                Message message = new Message();
                message.arg1 = 0;
                try {
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        /**************************************************************  == bindService ==  *********************************************************************************/
        mBinderService = new Intent(this, CounterService.class);
        mBinderServiceConnection = new ServiceConnection() {
            /**
             * 绑定服务时回调的接口，返回一个Ibinder对象
             * 通过这个Ibinder，实现宿主和Service的交互
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                CounterService.CounterBinder binder = (CounterService.CounterBinder) service;
                mCounterService = binder.getService();
                mCounterService.startCounter(1000);
            }

            /**
             * 当取消绑定的时候被回调。正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mCounterService = null;
            }
        };
        /**************************************************************  == BinderMessagerService ==  *********************************************************************************/

    }

    @OnClick({R.id.start, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
//                bindService(mBinderService, mBinderServiceConnection, Service.BIND_AUTO_CREATE);
                /**************************************************************  == bindService ==  *********************************************************************************/
//                Intent intent = new Intent();
//                intent.setAction("com.wcy.message");
//                //不要忘记了包名，不写会报错
//                intent.setPackage("wcy.godinsec.wcy_dandan");
//                bindService(intent, mBinderServiceMessageConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.stop:
                // 解除绑定
                if (mCounterService != null) {
                    mCounterService = null;
                    if (mBinderServiceConnection != null)
                        unbindService(mBinderServiceConnection);
                }
//                if (mBinderServiceConnection != null)
//                    unbindService(mBinderServiceMessageConnection);
                break;
        }
    }
}
