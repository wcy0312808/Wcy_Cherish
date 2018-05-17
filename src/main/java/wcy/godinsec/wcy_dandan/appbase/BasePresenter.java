package wcy.godinsec.wcy_dandan.appbase;

import java.lang.ref.WeakReference;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：抽象类，保证不能实例化，只能实例化其子类
 */
public abstract class BasePresenter<T> {
    //绑定当前Activity的View接口
    private WeakReference<T> mIView;

    //绑定View
    protected void attacthView(T view) {
        if (view != null) {
            mIView = new WeakReference<T>(view);
        }
    }

    //解除绑定
    protected void detacthView() {
        if (mIView != null) {
            mIView.clear();
            mIView = null;
        }
    }

    //该方法可以直接返回当前绑定View接口的实例
    protected T getIView() {
        return mIView.get();
    }

    //用来鉴定是否绑定成功
    protected boolean isViewAttached() {
        return mIView != null && mIView.get() != null;
    }
}
