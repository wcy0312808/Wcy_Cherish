package wcy.godinsec.wcy_dandan.network.rxdownload;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络服务
 */
public class HttpUtils {
    private static HttpService service;
    public static HttpService getHttpService(){
        if(service==null){
            initService();
        }
        return service;
    }
    private static OkHttpClient client ;
    public static void initService(){
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                            CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                            CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        client = new OkHttpClient().newBuilder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wemiyao.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(HttpService.class);
    }
}
