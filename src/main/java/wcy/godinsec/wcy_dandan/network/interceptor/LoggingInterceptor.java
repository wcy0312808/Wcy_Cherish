package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/9/1 21:49
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        //请求发起的时间
        long t1 = System.nanoTime();

        StringBuffer requestStr = new StringBuffer();

        requestStr.append("Sending request ")
                .append(request.url())
                .append(" on ")
                .append(chain.connection())
                .append("\n")
                .append(request.headers());

//        LogUtils.e(requestStr.toString());

        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Response response = chain.proceed(request);
        //收到响应的时间
        long t2 = System.nanoTime();


        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理


        StringBuffer responseStr = new StringBuffer();

        responseStr.append("Received response for ")
                .append(response.request().url())
                .append(" after ")
                .append((t2 - t1) / 1e6d)
                .append("\n")
                .append(response.headers());
//        LogUtils.e(responseStr.toString());

        return response;
    }
}
