package wcy.godinsec.wcy_dandan.proxy;

import wcy.godinsec.wcy_dandan.appbase.BasePresenter;
import wcy.godinsec.wcy_dandan.interfaces.OnBindingIViewListener;

/**
 * Auther：杨玉安 on 2017/8/25 15:48
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class Proxy_BindingIView<V,P extends BasePresenter>  implements OnBindingIViewListener{

    //持有目标对象的引用
    private OnBindingIViewListener mOnBindingIViewListener;

    public Proxy_BindingIView(OnBindingIViewListener onBindingIViewListener) {
        mOnBindingIViewListener = onBindingIViewListener;
    }

    @Override
    public P createPresenter() {
        return (P) mOnBindingIViewListener.createPresenter();
    }

    @Override
    public V createView() {
        return (V) mOnBindingIViewListener.createView();
    }
}
