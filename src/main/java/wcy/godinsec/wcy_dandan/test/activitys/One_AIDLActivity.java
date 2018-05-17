package wcy.godinsec.wcy_dandan.test.activitys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.IWcyAidl;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;

/**
 * Auther：杨玉安 on 2018/4/27 10:37
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class One_AIDLActivity extends BaseActivity {
    private ServiceConnection mServiceConnection;
    private IWcyAidl mIWcyAidl;
    private Intent mIntent;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_service_one;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = new Intent();
        mIntent.setComponent(new ComponentName("wcy.godinsec.wcy_dandan.IWcyAidl", "wcy.godinsec.wcy_dandan.IWcyAidl.AidlService"));
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIWcyAidl = IWcyAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                if (mIWcyAidl != null)
                    mIWcyAidl = null;
            }
        };


    }

    @OnClick({R.id.start, R.id.stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (mServiceConnection != null)
                    bindService(mIntent,mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.stop:
                break;
        }
    }
}
