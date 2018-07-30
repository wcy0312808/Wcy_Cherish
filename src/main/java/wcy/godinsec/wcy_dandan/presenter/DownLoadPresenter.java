package wcy.godinsec.wcy_dandan.presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BasePresenter;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.network.RetrofitManager;
import wcy.godinsec.wcy_dandan.network.RxObserver;
import wcy.godinsec.wcy_dandan.network.requestbean.DownLoadEntityRequest;
import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadEntity;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadListener;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadManager;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadObserver;
import wcy.godinsec.wcy_dandan.presenter.iview.IDownLoadView;
import wcy.godinsec.wcy_dandan.utils.AppUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.NetWorkUtils;
import wcy.godinsec.wcy_dandan.utils.ToastUtils;
import wcy.godinsec.wcy_dandan.views.customview.CustomProgressBar;

/**
 * Auther：杨玉安 on 2018/3/23 17:48
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadPresenter extends BasePresenter<IDownLoadView> {
    private Context mContext;

    public DownLoadPresenter(Context context) {
        mContext = context;
    }

    public void getGameList() {
        DownLoadEntityRequest request = new DownLoadEntityRequest();
        RetrofitManager.getInstance().getGameList(request, new RxObserver<ArrayList<DownLoadEntityResponse>>(mContext, true) {
            @Override
            protected void onRequestSuccess(ArrayList<DownLoadEntityResponse> bean) {
                if (isViewAttached() && bean != null && bean.size() > 0) {
                    getIView().getGameSuccess(bean);
                }
            }

            @Override
            protected void onRequestError(Throwable e) {

            }
        });
    }

    public void setProgressState(CustomProgressBar progressBar, int downStatus, int progress) {
        if (downStatus == Constance.DOWN_STATE_NOT || downStatus == Constance.DOWN_STATE_STOP) {//未下载
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("下载");  //提示用户点击下载
            progressBar.setProgress(1000);// 下载未开始，全部填充
        } else if (downStatus == Constance.DOWN_STATE_BEING) { //正在下载
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("下载中");  //这里可以展示下载进度数据，待会做
            progressBar.setProgress(progress);  // 动态设置进度条
        } else if (downStatus == Constance.DOWN_STATE_PAUSE) { //暂停下载
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("继续");  //这里可以展示下载进度数据，待会做
            progressBar.setProgress(progress);  // 动态设置进度条
        } else if (downStatus == Constance.DOWN_STATE_SUCCESS) {//正在安装
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("正在安装...");
            progressBar.setProgress(1000);  // 下载完毕，全部填充
        } else if (downStatus == Constance.DOWN_STATE_INSTALL_SUCCESS) {//安装成功
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("打开");
            progressBar.setProgress(1000);  // 下载完毕，全部填充
        } else if (downStatus == Constance.DOWN_STATE_INSTALL_FAIL) {//安装失败
            progressBar.setTextColor(Color.WHITE);
            progressBar.setText("重新安装");
            progressBar.setProgress(1000);  // 下载完毕，全部填充
        }
    }

    public void handleDownLoadStatus(DownLoadEntity downLoadEntity, CustomProgressBar progressBar) {
        if (downLoadEntity == null) return;
        if (downLoadEntity.getInstall_Status() == Constance.DOWN_STATE_NOT) {
            //未下载
            setProgressState(progressBar, Constance.DOWN_STATE_NOT, 0);
        } else if (downLoadEntity.getInstall_Status() == Constance.DOWN_STATE_PAUSE) {
            //暂停下载
            setProgressState(progressBar, Constance.DOWN_STATE_PAUSE, downLoadEntity.getDown_Progress() == 0 ? 0 : (int) (downLoadEntity.getDown_Progress() * 1000 / downLoadEntity.getDown_App_Size()));
        } else if (downLoadEntity.getInstall_Status() == Constance.DOWN_STATE_BEING) {
            //正在下载
            if (DownLoadManager.getInstance().getSubExist(downLoadEntity.getPackage_Name())) {//数据库中状态值是正在下载，同时下载队列中存在
                setProgressState(progressBar, Constance.DOWN_STATE_BEING, downLoadEntity.getDown_Progress() == 0 ? 0 : (int) (downLoadEntity.getDown_Progress() * 1000 / downLoadEntity.getDown_App_Size()));
            } else {//数据库中状态值是正在下载，但下载队列中不存在，意味着下载过程当前队列被清除掉了，也就是处于暂停状态
                DownLoadSQLManager.getInstance().updateInstallStatus(downLoadEntity.getPackage_Name(), Constance.DOWN_STATE_PAUSE);
                setProgressState(progressBar, Constance.DOWN_STATE_PAUSE, downLoadEntity.getDown_Progress() == 0 ? 0 : (int) (downLoadEntity.getDown_Progress() * 1000 / downLoadEntity.getDown_App_Size()));
            }

        } else if (downLoadEntity.getInstall_Status() == Constance.DOWN_STATE_SUCCESS) {
            //下载完成,也就是在正在安装
            String save_path = downLoadEntity.getSave_Path();
            if (!TextUtils.isEmpty(save_path)) {
                File file = new File(save_path);
                if (file.exists() && file.isFile()) {
                    if (file.length() > 0 && downLoadEntity.getDown_App_Size() == file.length()) {
                        setProgressState(progressBar, Constance.DOWN_STATE_SUCCESS, 0);
                        return;
                    }
                    file.delete();
                }
            }
            AppUtils.deleteFile(save_path);
            setProgressState(progressBar, Constance.DOWN_STATE_NOT, 0);
            DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), 0, Constance.DOWN_STATE_NOT);

        } else if (downLoadEntity.getInstall_Status() == Constance.DOWN_STATE_INSTALL_SUCCESS) {
            //安装完成
            PackageInfo appInstalled = AppUtils.isAppInstalled(downLoadEntity.getPackage_Name());
            if (appInstalled != null) {//数据库中为下载完成，同时系统查询出来有安装该程序
                setProgressState(progressBar, Constance.DOWN_STATE_INSTALL_SUCCESS, 0);
            } else {//数据库中为下载成功，但是系统查询出来的确实未安装，意味着可能被用户手动删除了，所以我们也需要手动把数据给清除掉
                setProgressState(progressBar, Constance.DOWN_STATE_NOT, 0);
                AppUtils.deleteFile(downLoadEntity.getSave_Path());
                DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), 0, Constance.DOWN_STATE_NOT);
            }
        }
    }


    //第一次下载APP
    public void startDownLoadApp(final CustomProgressBar progressBar, DownLoadEntityResponse data) {
        if (!NetWorkUtils.isNetworkAvailable(mContext) || (NetWorkUtils.netMode(mContext) == -1)) {
            ToastUtils.showShortToast(mContext.getResources().getString(R.string.net_error));
            return;
        }

        long availSpace = AppUtils.getAvailSpace(Environment.getDataDirectory().getAbsolutePath());
        if (NetWorkUtils.netMode(mContext) == 1) { //wifi
            if ((availSpace < (2 * data.getApp_size() + 50 * 1024 * 1024))) {//可用内存大小小于二倍的应用大小加50M提示用户不下载新应用
                ToastUtils.showShortToast(R.string.memory_full_not_download);
                return;
            }

            final DownLoadEntity downLoadEntity = new DownLoadEntity();
            downLoadEntity.setApp_Link(data.getApp_link());
            downLoadEntity.setPackage_Name(data.getPackage_name());
            downLoadEntity.setApp_Name(data.getName());
            downLoadEntity.setApp_sort(data.getSort());
            downLoadEntity.setDown_App_Size(data.getApp_size());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DownLoadManager.getInstance().startDown(new DownLoadObserver(downLoadEntity, new DownLoadListener() {
                        @Override
                        public void onCompleteDown() {
                            LogUtils.e("====onCompleteDown====");
                            setProgressState(progressBar, Constance.DOWN_STATE_SUCCESS, 1000);
                        }

                        @Override
                        public void onStartDown() {
                            LogUtils.e("====onStartDown====");
                        }

                        @Override
                        public void onErrorDown(Throwable e) {
                            LogUtils.e("====onErrorDown====");
                            setProgressState(progressBar, Constance.DOWN_STATE_PAUSE, 1000);
                        }

                        @Override
                        public void onPuaseDown() {
                            LogUtils.e("====onPuaseDown====");
                            setProgressState(progressBar, Constance.DOWN_STATE_PAUSE, 1000);
                        }

                        @Override
                        public void onStopDown() {
                            LogUtils.e("====onStopDown====");
                            setProgressState(progressBar, Constance.DOWN_STATE_STOP, 0);
                        }

                        @Override
                        public void updateProgress(long currentLength, long TotalLength) {
                            setProgressState(progressBar, Constance.DOWN_STATE_BEING, (int) (currentLength * 1000 / TotalLength));
                            if (currentLength == TotalLength) {
                                setProgressState(progressBar, Constance.DOWN_STATE_SUCCESS, 1000);
                            }
                            LogUtils.e("====updateProgress====" + currentLength + "   ===   " + TotalLength);
                        }
                    }));
                }
            }).start();
        }
    }

    //从其他状态开始下载APP
    public void restartDownLoadApp() {

    }

    private void downLoadApp() {

    }
}
