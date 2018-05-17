package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Auther：杨玉安 on 2018/1/20 10:28
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {


        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .header("Content-Type", "text/html;application/json;charset=utf-8;application/xhtml+xml")
                .header("Accept", "application/json")
                .header("Accept-Encoding", "gzip;deflate");

//        RegisterResponse response = CheriseSQLManager.getInstance().queryGodinUserInfo();
//        final String token = response != null ? response.getToken() + ":" : "";
//
//        if (TextUtils.isEmpty(request.header("Authorization")) && !TextUtils.isEmpty(token)) {
//            String result = Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
//            builder.header("Authorization", "Basic " + result);
//        }

        return chain.proceed(builder.build());
    }
}
