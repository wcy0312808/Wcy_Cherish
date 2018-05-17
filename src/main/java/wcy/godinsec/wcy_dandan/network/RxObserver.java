package wcy.godinsec.wcy_dandan.network;


import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import wcy.godinsec.wcy_dandan.network.exception.ApiException;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wcy.godinsec.wcy_dandan.interfaces.OnSpinkitDialogListener;
import wcy.godinsec.wcy_dandan.utils.ToastUtils;

/**
 * Auther：杨玉安 on 2017/7/7 12:57
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 创建观察者，及上游事件，处理事件的类
 * Observer在retrefit2中改变为一个借口代替了一中的那个Subject
 */
public abstract class RxObserver<T> implements Observer<T>, OnSpinkitDialogListener {
    private NetWorkDialogManager mSpinKitDialogUtils;
    private boolean onShowable = false;  //是否有能力显示dialog，默认为不显示

    public RxObserver(Context context, boolean onShowable) {
        this.onShowable = onShowable;
        if (onShowable && context != null)
            mSpinKitDialogUtils = new NetWorkDialogManager(context, this);
    }


    /**
     * 订阅的一瞬间就调用
     *
     * @param d
     */
    @Override
    public void onSubscribe(Disposable d) {
        if (mSpinKitDialogUtils != null && onShowable) {
            mSpinKitDialogUtils.showDialog();
        }
    }

    /**
     * 请求数据成功返回数据
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        if (t != null) {
            onRequestSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("e: = " + e);
        if (e instanceof ApiException) {
            ToastUtils.showShortToast(e.getMessage());
        } else if ((e instanceof UnknownHostException)) {
            ToastUtils.showShortToast("网络异常");
        } else if (e instanceof JsonSyntaxException) {
            ToastUtils.showShortToast("数据异常");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShortToast("连接超时");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShortToast("连接服务器失败");
        } else {
            ToastUtils.showShortToast("未知错误");
        }


        onRequestError(e);

        if (mSpinKitDialogUtils != null && onShowable)
            mSpinKitDialogUtils.dissmissDialog();
    }

    @Override
    public void onComplete() {
        if (mSpinKitDialogUtils != null && onShowable)
            mSpinKitDialogUtils.dissmissDialog();
    }


    @Override
    public void onSpinkitDialogCancel() {
        Log.e("TAG", "==============取消请求，释放内存===========");
    }


    protected abstract void onRequestSuccess(T bean);              //返回bean的方法

    protected abstract void onRequestError(Throwable e);           //返回错误的方法
}
