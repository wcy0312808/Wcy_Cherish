package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.NetWorkUtils;

/**
 * Auther：杨玉安 on 2017/9/1 20:40
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request(); //获取原始的请求，也就是上一次的请求
        Request.Builder newBuilder = originalRequest.newBuilder();
        Request compressedRequest;
        if (!NetWorkUtils.isNetworkAvailable(WcyApplication.getInstance())) {
            //如果没有网络的话，只从缓存总存取
            newBuilder.cacheControl(CacheControl.FORCE_CACHE);
        } else {
            //有网络的话，就从网络中获取
            newBuilder.cacheControl(CacheControl.FORCE_NETWORK);
        }

        compressedRequest = newBuilder.build();

        //获取当前的响应体
        Response response = chain.proceed(compressedRequest);
        if (NetWorkUtils.isNetworkAvailable(WcyApplication.getInstance())) {
            int maxAge = 60 * 60; // 有网络的时候从缓存1小时后失效
            response = response.newBuilder()
                    .removeHeader("Pragma")                                     //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)    //设置缓存超时时间
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 无网络缓存保存四周
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)  //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                    //设置缓存策略，及超时策略
                    .build();
        }
        return response;
    }


    private RequestBody gzip(final RequestBody body) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() {
                return -1; // 无法知道压缩后的数据大小
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}
