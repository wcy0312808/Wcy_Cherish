package wcy.godinsec.wcy_dandan.network.rxdownload;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.CheriseSQLManager;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.network.RetrofitManager;
import wcy.godinsec.wcy_dandan.utils.AppUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/2/1 14:12
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadManager {
    //volatile 表示不会被编译器认定为一个。
    private volatile static DownLoadManager sDownLoadManager;
    //每个下载路径都对应了一个DownObserver,其中包含当前下载过程中的所有的信息
    private HashMap<String, DownLoadObserver> downSubMap;

    //一个线程的缓存副本，可以针对每一个线程存储一些必要资源，避免了多线程时线程不安全的问题,对象为app的下载地址
    private ThreadLocal<String> mThreadLocal = new ThreadLocal<>();

    public void setThreadUrl(String url) {
        mThreadLocal.set(url);
    }

    public String getThreadUrl() {
        return mThreadLocal.get();
    }

    public void removeThreadUrl() {
        mThreadLocal.remove();
    }


    private DownLoadManager() {
        downSubMap = new LinkedHashMap<>();
    }

    public static DownLoadManager getInstance() {
        if (sDownLoadManager == null) {
            synchronized (DownLoadManager.class) {
                if (sDownLoadManager == null) {
                    sDownLoadManager = new DownLoadManager();
                }
            }
        }
        return sDownLoadManager;
    }

    /**
     * 开始下载
     */
    public void startDown(final DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downLoadEntity == null) return;

        if (getSub(downLoadEntity.getApp_Name()) != null) return;      //表示当前正在下载
        downSubMap.put(downLoadEntity.getApp_Link(), observer);         /*记录回调sub*/


        String apkTempName = AppUtils.MD5(downLoadEntity.getApp_Link()) + ".cheirse";    //混淆名称，不再是以APK结尾的，这样可以避免未下载完全时就打开的情况
        String savePath = Constance.mDownLoadPath + File.separator + apkTempName;       //外部存储
        LogUtils.e("===savePath===" + savePath);

        //保存的地址文件是否存在，不存在就直接创建
        File filePath = new File(Constance.mDownLoadPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File file = new File(savePath);

        LogUtils.e("length = " + file.length()
                + " Down_App_Size= " + downLoadEntity.getDown_App_Size()
                + " Down_Progress= " + downLoadEntity.getDown_Progress()
                + "   " + (file.exists() && file.isFile()));

        if (file.exists() && file.isFile()) {
            //文件长度大于0说明不为空，要下载的APP长度 == 文件长度 说明完整写入了
            if (file.length() > 0 && downLoadEntity.getDown_App_Size() == file.length()) {
                AppUtils.install(savePath, downLoadEntity.getApp_Name());  //如果应用已经存在直接安装
                return;
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //当前文件长度大于记录的下载长度，有可能是极个别字节写入时未记载到数据库中，这里我们以文件的长度为主
        if (file.length() > downLoadEntity.getDown_Progress()) {
            downLoadEntity.setDown_Progress(file.length());
        } else if (file.length() == 0 && downLoadEntity.getDown_Progress() > 0) {
            downLoadEntity.setDown_Progress(0);
        }


        downLoadEntity.setDown_State(Constance.DOWN_STATE_BEING);     //设置下载状态为下载中
        downLoadEntity.setSave_Path(savePath);                          //保存下载地址
        //将当前地址，状态保存在数据库中
        DownLoadSQLManager.getInstance().insertDownLoadEntity(downLoadEntity);
        RetrofitManager.getInstance().downLoadApp(observer);  //开始下载app
    }


    public void pauseDownSub(final DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downLoadEntity == null) return;
        observer.setPuaseDownLoad();

        LogUtils.e("停止下载 = Down_Progress() = " + downLoadEntity.getDown_Progress());
        DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(), Constance.DOWN_STATE_PAUSE);


        removeDownSub(observer);
    }


    /**
     * 停止下载
     */
    public void stopDownSub(final DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downLoadEntity == null) return;
        observer.setPuaseDownLoad();


//        downLoadEntity.getDownLoadListener().onStopDown();  //停止下载在界面上应该是直接清空，然后变成初始值，但目前暂未考虑到有该种情况
        DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(), Constance.DOWN_STATE_STOP);

        removeDownSub(observer);
        /**同步数据库*/
    }


    public void stopAllDownSub() {
        Collection<DownLoadObserver> values = downSubMap.values();
        for (DownLoadObserver observer : values) {
            stopDownSub(observer);
        }
        downSubMap.clear();
    }


    public void pauseAllDownSub() {
        Collection<DownLoadObserver> values = downSubMap.values();
        for (DownLoadObserver observer : values) {
            pauseDownSub(observer);
        }
        downSubMap.clear();
    }


    private void removeDownSub(DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downSubMap != null && downSubMap.containsKey(downLoadEntity.getApp_Link())) {
            downSubMap.remove(downLoadEntity.getApp_Link());
        }

        //一个猜测写法，目前没有证实
//        Disposable disposable = observer.getDisposable();
//        if (disposable != null && !disposable.isDisposed()) {
//            disposable.dispose();
//        }
    }


    /**
     * 获取下载中的任务实例
     *
     * @param appLink app的下载地址
     */
    public DownLoadObserver getSub(String appLink) {
        if (downSubMap != null && downSubMap.containsKey(appLink)) {
            return downSubMap.get(appLink);
        } else {
            return null;
        }
    }

    /**
     * 获取当前下载实例的状态（是否正在下载）
     *
     * @param appLink
     */
    public boolean getSubExist(String appLink) {
        if (downSubMap != null && downSubMap.containsKey(appLink)) {
            if (downSubMap.get(appLink) != null)
                return true;
        }
        return false;
    }
}
