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
    private volatile static DownLoadManager sDownLoadManager;  //volatile 表示不会被编译器认定为一个。
    private String mPath = Environment.getExternalStorageDirectory() + File.separator + "cheirse";
    private HashMap<String, DownLoadObserver> subMap;    //每个下载包名都对应了一个DownObserver ,其中包含当前下载过程中的所有的信息

    private DownLoadManager() {
        subMap = new LinkedHashMap<>();
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

        String savePath = mPath + File.separator + AppUtils.MD5(downLoadEntity.getApp_Name()) + File.separator + downLoadEntity.getApp_Name() + ".temp";//外部存储
        LogUtils.e("===savePath===" + savePath);

        //保存的地址文件是否存在，不存在就直接创建
        File filePath = new File(mPath);
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

        if (getSub(downLoadEntity.getApp_Name()) != null) return; //表示当前正在下载
        subMap.put(downLoadEntity.getApp_Name(), observer);  /*记录回调sub*/
        observer.setDownLoad(true);   //允许写入

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


    public void pause(final DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downLoadEntity == null) return;
        observer.setDownLoad(false);

        LogUtils.e("停止下载 = Down_Progress() = " + downLoadEntity.getDown_Progress());
        DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(),Constance.DOWN_STATE_PAUSE);
        downLoadEntity.getDownLoadListener().onPuaseDown();


        removeSub(observer);
    }


    /**
     * 停止下载
     */
    public void stopDown(final DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (downLoadEntity == null) return;
        observer.setDownLoad(false);

        downLoadEntity.getDownLoadListener().onStopDown();
        DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(),Constance.DOWN_STATE_STOP);

        removeSub(observer);
        /**同步数据库*/
    }


    public void stopAllDown() {
        Collection<DownLoadObserver> values = subMap.values();
        for (DownLoadObserver observer : values) {
            stopDown(observer);
        }
        subMap.clear();
    }


    public void pauseAllDown() {
        Collection<DownLoadObserver> values = subMap.values();
        for (DownLoadObserver observer : values) {
            pause(observer);
        }
        subMap.clear();
    }


    public void deleteDown(DownLoadObserver observer) {
        stopDown(observer);

        /*同步数据库*/
    }


    private void removeSub(DownLoadObserver observer) {
        DownLoadEntity downLoadEntity = observer.getDownLoadEntity();
        if (subMap != null && subMap.containsKey(downLoadEntity.getApp_Name())) {
//            Disposable disposable = observer.getDisposable();
//            if (disposable != null && !disposable.isDisposed()) {
//                disposable.dispose();
//            }
            subMap.remove(downLoadEntity.getApp_Name());
        }
    }

    /**
     * 获取下载工具
     *
     * @param pkg
     * @return
     */
    public DownLoadObserver getSub(String pkg) {
        if (subMap != null && subMap.containsKey(pkg)) {
            return subMap.get(pkg);
        } else {
            return null;
        }
    }

    /**
     * 获取下载工具
     *
     * @param pkg
     * @return
     */
    public boolean getSubExist(String pkg) {
        if (subMap != null && subMap.containsKey(pkg)) {
            if (subMap.get(pkg) != null)
                return true;
        }
        return false;
    }
}
