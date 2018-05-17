package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import wcy.godinsec.wcy_dandan.network.rxdownload.OnDownLoadProgressListener;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadResponseBody;

/**
 * Auther：杨玉安 on 2017/9/30 17:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：  下载时的进度拦截器，能够自动实现断点续传时的进度监听
 */
public class DownLoadInterceptor implements Interceptor {

    private OnDownLoadProgressListener mOnDownLoadProgressListener;

    public DownLoadInterceptor(OnDownLoadProgressListener onDownLoadProgressListener) {
        mOnDownLoadProgressListener = onDownLoadProgressListener;
    }

    /**
     * 在这里通过Response对象的建造器Builder对其进行修改，
     * 把Response.body()替换成我们的ProgressResponseBody
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new DownLoadResponseBody(response.body(), mOnDownLoadProgressListener))
                .build();
    }
}
