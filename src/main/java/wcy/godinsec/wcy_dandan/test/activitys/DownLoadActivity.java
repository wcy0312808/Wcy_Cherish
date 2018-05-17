package wcy.godinsec.wcy_dandan.test.activitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadEntity;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadObserver;
import wcy.godinsec.wcy_dandan.presenter.DownLoadPresenter;
import wcy.godinsec.wcy_dandan.presenter.iview.IDownLoadView;
import wcy.godinsec.wcy_dandan.utils.AppUtils;
import wcy.godinsec.wcy_dandan.views.adapter.CommonRecyclerViewAdapter;
import wcy.godinsec.wcy_dandan.views.adapter.CommonViewHolder;
import wcy.godinsec.wcy_dandan.views.customview.CustomProgressBar;

/**
 * Auther：杨玉安 on 2018/3/14 19:54
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadActivity extends BaseActivity<IDownLoadView, DownLoadPresenter> implements IDownLoadView {
    @BindView(R.id.rv_downapp_list)
    RecyclerView mRvDownAppList;
    private DownLoadEntity mDownLoadEntity;
    private DownLoadObserver mDownLoadObserver;
    private ArrayList<DownLoadEntityResponse> mGameInfo;
    private CommonRecyclerViewAdapter mAdapter;
//  http://apk.bjzqb.com/chess_10370.apk
//  http://wemiyao.com/ShokeyApk/shokey/1.0.2.apk
//  https://fir.im/5j4e

    @Override
    protected DownLoadPresenter onCreatePresenter() {
        return new DownLoadPresenter(this);
    }

    @Override
    protected int setContentlayout() {
        return R.layout.activity_down_laod;
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDownAppList.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getGameList(); //拉取数据
    }

    @Override
    public void getGameSuccess(ArrayList<DownLoadEntityResponse> gameList) {
        mAdapter = new CommonRecyclerViewAdapter<DownLoadEntityResponse>(this, R.layout.item_task, gameList) {
            @Override
            public void conver(final CommonViewHolder viewHodler, final DownLoadEntityResponse data) {
                boolean isInstalled = false;
                final CustomProgressBar progressBar = viewHodler.getView(R.id.progress_bar);
                viewHodler.setImageUrl(R.id.iv_app_logo, data.getIcon());
                viewHodler.setText(R.id.tv_app_name, data.getName());
                viewHodler.setText(R.id.tv_app_version, data.getApp_version());
                viewHodler.setText(R.id.tv_app_size, String.format("%.1f", data.getApp_size() * 1f / 1024 / 1024) + "MB");
                mPresenter.setProgressState(progressBar, Constance.DOWN_STATE_NOT, 0); //首先统一设置成未下载状态


                final DownLoadEntity downLoadEntity = DownLoadSQLManager.getInstance().queryDownLoadEntityByPackageName(data.getPackage_name());//根据包名查询是否存在下载记录
                PackageInfo packageInfo = AppUtils.isAppInstalled(data.getPackage_name());      //判断当前App是否安装
                if (packageInfo != null) {
                    isInstalled = true;
                    PackageManager packageManager = getPackageManager();
                    Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager); //获取当前App真实的Icon，（因为有时候网络上配置的在更新或者其他的时候会变）
                    String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                    if (drawable != null)
                        viewHodler.setImageDrawable(R.id.iv_app_logo, drawable);
                    if (!TextUtils.isEmpty(appName))
                        viewHodler.setText(R.id.tv_app_name, data.getName());
                    mPresenter.setProgressState(progressBar, Constance.DOWN_STATE_INSTALL_SUCCESS, 0); //下载完成，可以打开状态

                    if (downLoadEntity != null) {
                        //将数据库中的状态值保持安装状态，以防万一
                        if (downLoadEntity.getInstall_Status() != Constance.DOWN_STATE_INSTALL_SUCCESS)
                            DownLoadSQLManager.getInstance().updateInstallStatus(data.getPackage_name(), Constance.DOWN_STATE_INSTALL_SUCCESS);
                    }
                }else {
                    mPresenter.handleDownLoadStatus(null, progressBar);
                }
                final boolean final_Installed = isInstalled;
                progressBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (final_Installed) {
                            AppUtils.startTagActivity(DownLoadActivity.this, downLoadEntity.getPackage_Name());
                            return;
                        }
                        if (downLoadEntity == null){
                            mPresenter.startDownLoadApp(progressBar,data);
                        }else {
                            mPresenter.restartDownLoadApp();
                        }
                    }
                });
            }
        };
        mRvDownAppList.setAdapter(mAdapter);
    }


//    @Override
//    protected void initDatas() {
//        super.initDatas();
//        mDownLoadObserver = new DownLoadObserver(mDownLoadEntity);
//        mDownLoadEntity = new DownLoadEntity();
//        mDownLoadEntity.setApp_Link("shokey/1.0.2.apk");
//        mDownLoadEntity.setApp_Name("shokey.apk");
//        mDownLoadEntity.setDownLoadListener(new DownLoadListener() {
//            @Override
//            public void onCompleteDown() {
//                LogUtils.e("====onCompleteDown====");
//            }
//
//            @Override
//            public void onStartDown() {
//                LogUtils.e("====onStartDown====");
//            }
//
//            @Override
//            public void onErrorDown(Throwable e) {
//                LogUtils.e("====onErrorDown====");
//            }
//
//            @Override
//            public void onPuaseDown() {
//                LogUtils.e("====onPuaseDown====");
//            }
//
//            @Override
//            public void onStopDown() {
//                LogUtils.e("====onStopDown====");
//            }
//
//            @Override
//            public void updateProgress(long readLength, long countLength) {
//                LogUtils.e("====updateProgress====");
//            }
//        });
//    }
}
