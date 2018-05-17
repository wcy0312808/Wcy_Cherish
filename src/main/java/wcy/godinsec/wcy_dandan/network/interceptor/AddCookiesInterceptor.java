package wcy.godinsec.wcy_dandan.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/1/31 17:49
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String lang) {
        super();
        this.context = context;
        this.lang = lang;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            LogUtils.e("Addchain == null");
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
//        Observable.just(sharedPreferences.getString("cookie", ""))
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String cookie) throws Exception {
//                        if (cookie.contains("lang=ch")) {
//                            cookie = cookie.replace("lang=ch", "lang=" + lang);
//                        }
//                        if (cookie.contains("lang=en")) {
//                            cookie = cookie.replace("lang=en", "lang=" + lang);
//                        }
//                        //添加cookie
////                        Log.d("http", "AddCookiesInterceptor"+cookie);
//                        builder.addHeader("cookie", cookie);
//                    }
//                });
        return chain.proceed(builder.build());
    }
}