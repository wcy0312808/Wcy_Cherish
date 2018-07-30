package wcy.godinsec.wcy_dandan.network.rxdownload;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.utils.AppUtils;
import wcy.godinsec.wcy_dandan.utils.FileUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/1/31 20:49
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：   下载的网络回调，主要是能够提前处理返回来的数据，在这个类中同时要实现进度的监听。
 */
public class DownLoadObserver implements Observer<ResponseBody>, OnDownLoadProgressListener {
    private WeakReference<DownLoadListener> mSubNextListener;  //当前请求回调的状态值，主要是掉给别人用的
    private DownLoadEntity mDownLoadEntity;                    //需要保存的各种数据
    private boolean downloadSuccess = false;                 //是否下载成功，这里是利用是否写入完成来实现的

//    private Disposable mDisposable;
//    public Disposable getDisposable() {
//        return mDisposable;
//    }


    //设置暂停下载
    public void setPuaseDownLoad() {
        FileUtils.setDownLoad(false);
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onPuaseDown();
        }
    }

    public DownLoadEntity getDownLoadEntity() {
        return mDownLoadEntity;
    }


    public DownLoadObserver(DownLoadEntity downLoadEntity, DownLoadListener downLoadListener) {
        mDownLoadEntity = downLoadEntity;
        this.mSubNextListener = new WeakReference<DownLoadListener>(downLoadListener);
    }


    //响应式网络请求的开始回调，我们在这里显示dialog
    @Override
    public void onSubscribe(Disposable d) {
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onStartDown();
            mDownLoadEntity.setDown_State(Constance.DOWN_STATE_BEING); //开始
//          mDisposable = d;
        }
    }


    /**
     * 请求成功，获取返回体并转换成流写入文件中
     */
    @Override
    public void onNext(final ResponseBody body) {
        LogUtils.e("onNext= = " + mDownLoadEntity.toString());

        if (mSubNextListener.get() != null) {
            //写入成功或者写入失败都会有一个返回值，通过此返回值来判断是否可以安装
            downloadSuccess = FileUtils.writeRandomFile(body, mDownLoadEntity, mSubNextListener.get());
        }
    }


    /**
     * 对下载的任务统一处理
     */
    @Override
    public void onError(Throwable e) {
        LogUtils.e("onError= = " + e.toString());
        DownLoadManager.getInstance().pauseDownSub(this);
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onErrorDown(e);
        }
        mDownLoadEntity.setDown_State(Constance.DOWN_STATE_FAIL);
    }


    @Override
    public void onComplete() {
        LogUtils.e("onComplete= = " + mDownLoadEntity.toString());
        mDownLoadEntity.setDown_State(Constance.DOWN_STATE_SUCCESS);
    }


    /**
     * 下载进度的监听事件
     *
     * @param read  下载的进度 已经读取的字节数
     * @param count 总进度 响应总长度
     * @param done  什么时候停止，是否读取完毕
     */
    @Override
    public void updateDownLoadProgress(long read, long count, boolean done) {
        if (mDownLoadEntity.getDown_App_Size() > count) {
            read = mDownLoadEntity.getDown_App_Size() - count + read;
        } else {
            mDownLoadEntity.setDown_App_Size(count);
        }


        mDownLoadEntity.setDown_Progress(read);

        if (mSubNextListener.get() != null) {
            /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
            Observable.just(read)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            //如果当前状态是不可见或者是停止状态不需要回调这个接口来刷新进度条
                            if (mDownLoadEntity.getDown_State() == Constance.DOWN_STATE_PAUSE || mDownLoadEntity.getDown_State() == Constance.DOWN_STATE_STOP)
                                return;

                            mDownLoadEntity.setDown_State(Constance.DOWN_STATE_BEING);
                            mSubNextListener.get().updateProgress(aLong, mDownLoadEntity.getDown_Progress());
                            LogUtils.e("aLong====   " + aLong + "      updateProgress====   " + mDownLoadEntity.getDown_Progress());
                        }
                    });
        }
    }


    //统一处理结果
    private void disposeResult() {
//        DownLoadManager.getInstance().deleteDownSub(mDownLoadEntity.getApp_Link());


    }
}
