package wcy.godinsec.wcy_dandan.network.rxdownload;

/**
 * Auther：杨玉安 on 2017/9/30 16:58
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 下载进度监听器
 */
public interface OnDownLoadProgressListener {
    /**
     * 下载进度
     *
     * @param read  下载的进度 已经读取的字节数
     * @param count 总进度 响应总长度
     * @param done  什么时候停止，是否读取完毕
     */
    void updateDownLoadProgress(long read, long count, boolean done);
}
