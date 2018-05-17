package wcy.godinsec.wcy_dandan.network.rxdownload;

import io.reactivex.Observer;

/**
 * Auther：杨玉安 on 2018/2/9 16:07
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface RxRequestListener<T> {
    void addRequst(T tag, Observer observer);  //添加一个请求

    void remove(T tag); //删除一个请求

    void cancel(T tag); //取消一个请求

    void cancelAll();   //取消所有的请求
}
