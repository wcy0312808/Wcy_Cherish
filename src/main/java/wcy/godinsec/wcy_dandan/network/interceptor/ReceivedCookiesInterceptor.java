package wcy.godinsec.wcy_dandan.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Auther：杨玉安 on 2018/1/31 17:57
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (chain == null)
//            Log.d("http", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
//        Log.d("http", "originalResponse" + originalResponse.toString());
//        if (!originalResponse.headers("set-cookie").isEmpty()) {
//            final StringBuffer cookieBuffer = new StringBuffer();
//            Observable.just(originalResponse.headers("set-cookie"))
//                    .map(new Function<String, String>() {
//                        @Override
//                        public String apply(String s) throws Exception {
//                            String[] cookieArray = s.split(";");
//                            return cookieArray[0];
//                        }
//
//                    })
//                    .subscribe(new Con);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("cookie", cookieBuffer.toString());
//            Log.d("http", "ReceivedCookiesInterceptor" + cookieBuffer.toString());
//            editor.commit();
//        }
        return originalResponse;
    }
}
