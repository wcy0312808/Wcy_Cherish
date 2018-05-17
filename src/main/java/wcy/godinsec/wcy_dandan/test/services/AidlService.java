package wcy.godinsec.wcy_dandan.test.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import wcy.godinsec.wcy_dandan.IWcyAidl;

/**
 * Auther：杨玉安 on 2018/4/27 10:30
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AidlService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return IWcyAidl;
    }

    private IBinder IWcyAidl = new IWcyAidl.Stub() {
        @Override
        public String aidlTest(String aString) throws RemoteException {
            return "身体健康，开开心心，发大财";
        }
    };
}
