package wcy.godinsec.wcy_dandan.appbase;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.bean.EventBean;
import wcy.godinsec.wcy_dandan.interfaces.OnPermissionsResultListener;
import wcy.godinsec.wcy_dandan.manager.MyFragmentManager;
import wcy.godinsec.wcy_dandan.utils.AppActivityUtils;
import wcy.godinsec.wcy_dandan.utils.StatusBarUtil;


/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：所有Activity的基类
 * activity 是Android 的窗口，它会保持各个界面的状态，妥善保管各个界面的生命周期，同时也是Android做持久化操作的地方。
 * activity 的生命周期是 onCreat ==> onStart ==> onResume ==> onPause  ==> onStop ==> onReStart ==> onDestory
 * 而页面切花是的时的生命周期 正确应该是  （A）onPause - (B) OnCreate - （B）onStart - (B)onResume - (A) onDestory
 * 因此，在切换界面的时候最好在onPause中就将所有的资源给清理掉。比在onDestory快很多。
 * 其中还包含两个方法，
 * 一个是onSaveInstanceState targetSDK < 3 的时候是在 onPause targetSDK > 3 的时候就是在onStop中了
 * 一个是OnRestoreInstanceState 是在onStart之后 onResume 之前调用的
 */
public abstract class BaseActivity<V, P extends BasePresenter> extends FragmentActivity {
    //presenter
    protected P mPresenter;
    private Unbinder mUnbinder;
    //申请权限的结果回调
    private OnPermissionsResultListener mListener;
    //申请权限的接口回调码
    private int mRequestCode;
    protected MyFragmentManager mMyFragmentManager;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentlayout());
        //绑定ButterKnife工具
        mUnbinder = ButterKnife.bind(this);

        //创建presenter 默认返回的是空，如果子类需要实现才会重写
        if (mPresenter == null)
            mPresenter = onCreatePresenter();

        //判断当前类是否运用MVP模式 ,绑定当前的视图
        if (mPresenter != null)
            mPresenter.attacthView((V) this);

        //注册EventBus,这里需要子类复写该方法，返回true则代表需要注册
        if (isRegisterEventBus())
            EventBus.getDefault().register(this);


        //将当前Activity添加到存放Activity的集合中
        AppActivityUtils.getAppActivityUtils().addActivity(this);

        //友盟推送的初始化
        PushAgent.getInstance(WcyApplication.getInstance()).onAppStart();

        mMyFragmentManager = new MyFragmentManager(this);
        initViews();  //子类的初始化视图
        initDatas();   //子类初始化数据
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
        //解除子类绑定的Presenter
        if (mPresenter != null)
            mPresenter.detacthView();

        if (isRegisterEventBus())
            EventBus.getDefault().unregister(this);


        //将当前Activity从存放Activity的集合中移除
        AppActivityUtils.getAppActivityUtils().removeActivity(this);


        //初始Activity中加入如下两行代码用于LeakCanary
//        RefWatcher refWatcher = WcyApplication.getRefWatcher(this);
//        refWatcher.watch(this);
    }


    /*************************************全局EventBus注册*****************************************/
    //EventBus的主线程的调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void onEventBusMain(EventBean event) {
    }

    //子线程或者异步
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventBusAsyn(EventBean event) {
    }
    /*************************************全局权限申请*****************************************/

    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void applyForPermissions(String desc, String[] permissions, int requestCode, OnPermissionsResultListener listener) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mRequestCode = requestCode;
        mListener = listener;
        //判断是否安卓版本是否大于23也就是大于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否声明了权限
            if (checkEachSelfPermission(permissions)) {
                //申请权限前查看是否需要展示给用户解释信息
                requestEachPermissions(desc, permissions, requestCode);
            }
            // 已经申请权限
            else {
                if (mListener != null) {
                    //已经申请权限的回调
                    mListener.onPermissionSuccess();
                }
            }
        } else {
            if (mListener != null) {
                //已经申请权限的回调
                mListener.onPermissionSuccess();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        // 需要再次声明
        if (shouldShowRequestPermissionRationale(permissions)) {
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            //如果当前的这个权限没有在已申请的组中就返回ture，代表需要申请权限
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkEachPermissionsGranted(grantResults)) {
                if (mListener != null) {
                    mListener.onPermissionSuccess();
                }
            } else {// 用户拒绝申请权限
                if (mListener != null) {
                    mListener.onPermissionFailed();
                }
            }
        }
    }

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /*************************************全局规范函数*****************************************/

    protected abstract int setContentlayout();

    protected P onCreatePresenter() {
        return null;
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    protected void initViews() {
    }

    protected void initDatas() {
    }

    //重写该方法，使其空实现
    @Override
    protected void onSaveInstanceState(Bundle arg0) {
    }
}
