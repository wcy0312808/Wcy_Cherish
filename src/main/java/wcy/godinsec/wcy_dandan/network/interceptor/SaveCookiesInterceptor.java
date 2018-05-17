package wcy.godinsec.wcy_dandan.network.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Auther：杨玉安 on 2018/1/31 17:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：   参考网址   https://www.jianshu.com/p/1a5f14b63f47
 */
public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

//        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
//            HashSet<String> cookies = new HashSet<>();
//
//            for (String header : originalResponse.headers("Set-Cookie")) {
//                cookies.add(header);
//            }

//            Preferences.getDefaultPreferences().edit()
//                    .putStringSet(Preferences.PREF_COOKIES, cookies)
//                    .apply();
//        }

        return originalResponse;
    }
    }