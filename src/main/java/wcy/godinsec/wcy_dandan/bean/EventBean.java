package wcy.godinsec.wcy_dandan.bean;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class EventBean<T> {
    /**
     * code是事件码，
     * 使用的时候给不同的时间类型指定不同的code
     */
    private int code;

    /**
     * 所发送的具体实体类
     */
    private T data;


    public EventBean(int code) {
        this.code = code;
    }



    public EventBean(int code, T data) {
        this.code = code;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
