package wcy.godinsec.wcy_dandan.appbase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.bean.EventBean;
import wcy.godinsec.wcy_dandan.interfaces.OnPermissionsResultListener;

/**
 * Auther：杨玉安 on 2017/8/1 15:33
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public abstract class BaseFragment<V, P extends BasePresenter> extends Fragment {

    //presenter
    protected P mPresenter;
    protected Activity mBFragmentContext;  //各个activity所对应的ActivityContext

    private Unbinder mUnbinder;

    //申请权限的监听
    private OnPermissionsResultListener mPermissionsResultListener;

    //申请权限的请求码
    private int mRequestPermissionCode;
    private View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //强制转换成activity，也就意味着绑定了当前IView
            mBFragmentContext = (Activity) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (setFragmentLayoutID() != 0){
            mRootView = inflater.inflate(setFragmentLayoutID(), container, false);
        }
        mUnbinder = ButterKnife.bind(this, mRootView);
        initViews(mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注册EventBus,这里需要子类复写该方法，返回true则代表需要注册
        if (isRegisterEventBus())
            EventBus.getDefault().register(this);
        //创建presenter 默认返回的是空，如果子类需要实现才会重写
        if (mPresenter == null)
            mPresenter = onCreatePresenter();
        //判断当前类是否运用MVP模式 ,绑定当前的视图
        if (mPresenter != null)
            mPresenter.attacthView((V) this);

        initDatas();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();

        //解除子类绑定的Presenter
        if (mPresenter != null)
            mPresenter.detacthView();

        if (isRegisterEventBus())
            EventBus.getDefault().unregister(this);

    }


    /*************************************全局EventBus注册*****************************************/
    @Subscribe(threadMode = ThreadMode.MAIN) //EventBus的主线程的调用
    protected void onEventBusMain(EventBean event) {
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)  //子线程或者异步
    public void onEventBusAsyn(EventBean event) {
    }




    /*************************************全局权限申请*****************************************/
    /**
     * 申请权限的方法
     *
     * @param desc                  再次申请已拒绝权限需要展示的desc
     * @param permissions
     * @param requestPermissionCode
     * @param listener
     */
    protected void applyForPermissions(String desc, String[] permissions, int requestPermissionCode, OnPermissionsResultListener listener) {
        if (permissions == null || permissions.length == 0) return;
        mRequestPermissionCode = requestPermissionCode;
        mPermissionsResultListener = listener;
        //如果当前安卓版本号大于或等于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //这里包括两种情况，一种是第一次授权的权限申请，第二种是已经拒绝的权限再次申请
            if (checkEachSelfPermission(permissions)) {
                if (againRequestRequestPermissionRationale(permissions)) {
                    showRationableDialog(desc);
                } else {
                    requestPermissions(permissions, requestPermissionCode);
                }
            }
            //已经申请了权限
            else {
                if (mPermissionsResultListener != null)
                    mPermissionsResultListener.onPermissionSuccess();
            }
        } else {
            if (mPermissionsResultListener != null)
                mPermissionsResultListener.onPermissionSuccess();
        }
    }

    /**
     * 逐一检查申请的权限是否已经申请
     *
     * @param permissions 需要检查的权限组
     * @return
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permisson) != PackageManager.PERMISSION_GRANTED)
                return true;
        }
        return false;
    }

    /**
     * 判断当前申请的权限是否是已经拒绝的
     *
     * @param permissions
     * @return
     */
    private boolean againRequestRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission))
                return true;
        }
        return false;
    }

    /**
     * 展示再次请求已拒绝权限的dialog
     *
     * @param desc 需要描述的信息
     */
    private void showRationableDialog(String desc) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    /*************************************全局权限申请的回调结果*****************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestPermissionCode) {
            if (checkEachPermissions(grantResults)) {
                if (mPermissionsResultListener != null)
                    mPermissionsResultListener.onPermissionSuccess();
            } else {
                if (mPermissionsResultListener != null)
                    mPermissionsResultListener.onPermissionFailed();
            }
        }
    }

    /**
     * 当前组中只要有一个权限没有申请成功就返回false
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /*************************************全局规范函数*****************************************/
    //子类决定是否需要注册EventBus
    protected boolean isRegisterEventBus() {
        return false;
    }
    protected void initViews(View view) {
    }
    protected void initDatas() {
    }
    protected P onCreatePresenter() {
        return null;
    }
    protected V onCreateIView() {
        return null;
    }
    protected abstract int setFragmentLayoutID();
}
