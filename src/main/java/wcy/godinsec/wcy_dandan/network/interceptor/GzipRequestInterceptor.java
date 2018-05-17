package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Auther：杨玉安 on 2017/9/1 22:02
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class GzipRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取请求实体
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }
        Request compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .header("Content-Type", "text/html;application/json;charset=utf-8")
                .header("Accept", "application/json")
                .method(originalRequest.method(), gzip(originalRequest.body())).build();

        return chain.proceed(compressedRequest);
    }

    private RequestBody gzip(final RequestBody requestBody) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return requestBody.contentType();
            }

            /**
             * 当不知道长度时直接返回-1
             * @return
             * @throws IOException
             */
            @Override
            public long contentLength() throws IOException {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                requestBody.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}