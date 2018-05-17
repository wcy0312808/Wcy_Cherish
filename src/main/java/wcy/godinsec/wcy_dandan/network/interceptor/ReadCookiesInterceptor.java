package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/1/31 17:46
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：   参考网址   https://www.jianshu.com/p/1a5f14b63f47
 */
public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        HashSet<String> preferences = (HashSet) Preferences.getDefaultPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<>());
//        for (String cookie : preferences) {
//            builder.addHeader("Cookie", cookie);
//            LogUtils.e("Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
//        }
        return chain.proceed(builder.build());
    }
}

