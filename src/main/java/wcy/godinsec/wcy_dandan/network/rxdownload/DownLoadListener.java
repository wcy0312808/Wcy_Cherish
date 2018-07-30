package wcy.godinsec.wcy_dandan.network.rxdownload;

/**
 * Auther：杨玉安 on 2018/1/31 20:44
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 不可实例化,主要记录下载时的各种状态值
 */
public interface DownLoadListener<T> {
    /**
     * 成功后回调方法
     */
    public void onCompleteDown();

    /**
     * 开始下载
     */
    public void onStartDown();


    /**
     * 下载失败
     *
     * @param e
     */
    public void onErrorDown(Throwable e);


    /**
     * 暂停下载，保留当前的数据
     */
    public void onPuaseDown();


    /**
     * 停止下载，将当前数据全部清空
     */
    public void onStopDown();


    /**
     * 下载进度
     *
     * @param currentLength 下载了多少
     * @param TotalLength   总值是多少
     */
    public abstract void updateProgress(long currentLength, long TotalLength);
}
