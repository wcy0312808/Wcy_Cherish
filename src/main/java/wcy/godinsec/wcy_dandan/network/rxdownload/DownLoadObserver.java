package wcy.godinsec.wcy_dandan.network.rxdownload;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;

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
    private boolean isDownLoad = true;                        //是否允许写入

    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

    public DownLoadEntity getDownLoadEntity() {
        return mDownLoadEntity;
    }

//    private Disposable mDisposable;
//    public Disposable getDisposable() {
//        return mDisposable;
//    }

    public DownLoadObserver(DownLoadEntity downLoadEntity) {
        mDownLoadEntity = downLoadEntity;
        this.mSubNextListener = new WeakReference<DownLoadListener>(downLoadEntity.getDownLoadListener());
    }


    //响应式网络请求的开始回调，我们在这里显示dialog
    @Override
    public void onSubscribe(Disposable d) {
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onStartDown();
            mDownLoadEntity.setDown_State(Constance.DOWN_STATE_BEING); //开始
//            mDisposable = d;
        }
    }


    @Override
    public void onNext(final ResponseBody body) {
        LogUtils.e("onNext= = " + mDownLoadEntity.toString());
        if (mSubNextListener.get() != null) {
            writeCache(body, mDownLoadEntity);
        }
    }


/**
 * 将onNext方法中的结果直接交给view处理
 *
 * @param 对下载的任务统一处理
 * @param e
 * <p>
 * 对下载的任务统一处理
 * @param e
 */


    /**
     * 对下载的任务统一处理
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        LogUtils.e("onError= = " + e.toString());
        DownLoadManager.getInstance().stopDown(this);
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onErrorDown(e);
        }
        mDownLoadEntity.setDown_State(Constance.DOWN_STATE_FAIL);
    }


    @Override
    public void onComplete() {
        LogUtils.e("onComplete= = " + mDownLoadEntity.toString());
        if (mSubNextListener.get() != null) {
            mSubNextListener.get().onCompleteDown();
        }
        mDownLoadEntity.setDown_State(Constance.DOWN_STATE_SUCCESS);
        //下载完成安装apk
        if (isDownLoad) {
            AppUtils.install(mDownLoadEntity.getSave_Path(), mDownLoadEntity.getApp_Name());
        }
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
        LogUtils.e("==========updateDownLoadProgress===========");
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

    /**
     * RandomAccessFile 写入文件
     *
     * @param body 断点续传时，每次请求回来的body是剩余的长度
     */
    private boolean writeCache(ResponseBody body, final DownLoadEntity downLoadEntity) {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        File file = null;
        try {
            byte[] fileReader = new byte[1024 * 8];
            file = new File(downLoadEntity.getSave_Path());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            long fileSize = body.contentLength();
            //将状态值改为开始下载并保存到数据库中
            downLoadEntity.setDown_State(Constance.DOWN_STATE_BEING);
            //断点续传时，每次请求回来的body是剩余的长度，所以需要再加上已经下载的长度
            downLoadEntity.setDown_App_Size(fileSize + downLoadEntity.getDown_Progress());
            DownLoadSQLManager.getInstance().updateStatusAndSizeByPkg(downLoadEntity);

            long currentFileLength = downLoadEntity.getDown_Progress(); //文件的大小长度，简单来说也就是已经下载的长度

            inputStream = body.byteStream();
            raf = new RandomAccessFile(downLoadEntity.getSave_Path(), "rw");
            raf.seek(downLoadEntity.getDown_Progress());  //从文件的当前长度开始下载
            int read;


            while ((read = inputStream.read(fileReader)) != -1) {
                raf.write(fileReader, 0, read);  //写入
                currentFileLength += read;       //记录写入的当前值

                //更新数据库
                downLoadEntity.setDown_Progress(currentFileLength);
                DownLoadSQLManager.getInstance().updateProgress(downLoadEntity);
                if (mSubNextListener.get() != null) {
                   /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
                    final long final_CurrentFileLength = currentFileLength;
                    Observable.just(read)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    //如果当前状态是不可见或者是停止状态不需要回调这个接口来刷新进度条
                                    if (downLoadEntity.getDown_State() == Constance.DOWN_STATE_PAUSE || downLoadEntity.getDown_State() == Constance.DOWN_STATE_STOP)
                                        return;
                                    mSubNextListener.get().updateProgress(final_CurrentFileLength, downLoadEntity.getDown_App_Size());
                                }
                            });
                }
                LogUtils.e("fileSize = = " + fileSize + " currentFileLength = = " + currentFileLength + "  read = " + read);
                if (!isDownLoad) {//暂停下载
                    LogUtils.e("停止写入 = Down_Progress() = " + downLoadEntity.getDown_Progress());
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            LogUtils.e("Exception " + e);
            DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(), downLoadEntity.getInstall_Status());
            isDownLoad = false;
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
