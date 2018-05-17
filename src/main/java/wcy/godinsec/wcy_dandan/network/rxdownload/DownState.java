package wcy.godinsec.wcy_dandan.network.rxdownload;

/**
 * Auther：杨玉安 on 2017/9/30 17:41
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public enum DownState {
    DOWN_STATE_NOT,          //未下载
    DOWN_STATE_START,        // 开始下载
    DOWN_STATE_BEING,        //下载中
    DOWN_STATE_PAUSE,        //暂停
    DOWN_STATE_STOP,         //停止
    DOWN_STATE_FAIL,        //错误
    DOWN_STATE_SUCCESS,     //完成
}
