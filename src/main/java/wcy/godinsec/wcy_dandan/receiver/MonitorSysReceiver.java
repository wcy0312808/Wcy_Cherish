package wcy.godinsec.wcy_dandan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/3/23 20:00
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：  监听系统卸载安装软件
 */
public class MonitorSysReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Set<String> keySet = intent.getExtras().keySet();
        if (intent != null && intent.getData() != null && !TextUtils.isEmpty(intent.getData().getSchemeSpecificPart())) {
            String packName = intent.getData().getSchemeSpecificPart(); //获取正在安装的App包名
            if (action.equals(Intent.ACTION_PACKAGE_ADDED) && !keySet.contains("android.intent.extra.REPLACING")) { //安装
            }
        }
    }

    public void updateDownLoadApp(final String packageName) {
        Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        LogUtils.e("=====安装====="+packageName);
//                        DownLoadEntity downLoadEntity = CheriseSQLManager.getInstance().queryDownLoadEntity(packageName);
//                        if (downLoadEntity != null){
//                            AppUtils.deleteFile(downLoadEntity.getSave_Path());
//                            DownLoadSQLManager.getInstance().updateInstallStatus(Constance.INSTALL_STATE_SUCCESS,packageName);
//                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }
}
