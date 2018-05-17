package wcy.godinsec.wcy_dandan.network.exception;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：  自定义的错误处理类
 */
public class ApiException extends RuntimeException {
    //返回回来的错误信息
    private String message;

    /**
     * 这是直接传递信息的构造方法
     * @param message
     */
    public ApiException(String message) {
        this.message = message;
    }

    //提供给其他地方可以得到message的方法
    @Override
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
