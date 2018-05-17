package wcy.godinsec.wcy_dandan.interfaces;

import wcy.godinsec.wcy_dandan.appbase.BasePresenter;

/**
 * Auther：杨玉安 on 2017/8/25 15:42
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface OnBindingIViewListener<V, P extends BasePresenter> {
    //创建Presenter
    P createPresenter();

    //创建View
    V createView();
}
