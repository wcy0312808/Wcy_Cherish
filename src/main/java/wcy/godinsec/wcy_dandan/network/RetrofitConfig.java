package wcy.godinsec.wcy_dandan.network;


import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wcy.godinsec.wcy_dandan.BuildConfig;
import wcy.godinsec.wcy_dandan.network.interceptor.GzipRequestInterceptor;
import wcy.godinsec.wcy_dandan.network.interceptor.HeaderInterceptor;
import wcy.godinsec.wcy_dandan.network.interceptor.LoggingInterceptor;
import wcy.godinsec.wcy_dandan.network.interceptor.TokenAuthentiactor;
import wcy.godinsec.wcy_dandan.network.interceptor.TrustAnyHostnameVerifierInterceptor;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadObserver;
import wcy.godinsec.wcy_dandan.network.rxdownload.OnDownLoadProgressListener;
import wcy.godinsec.wcy_dandan.utils.FileUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：创建被观察者，及创建RxApi的实例
 */
public class RetrofitConfig {
    private static final String BASEURL = BuildConfig.ServerAddress + BuildConfig.apiVersion;
    private static final long TIMEOUT = 20 * 1000;
    protected Retrofit mRetrofit;
    protected Gson mGson;
    protected JsonParser mJsonParser;


    public RetrofitConfig() {
        mGson = new GsonBuilder().setPrettyPrinting().create();
        mJsonParser = new JsonParser();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.godinsec.cn/api/v1.3/")
                .client(createOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //绑定RxJava
                .addConverterFactory(GsonConverterFactory.create())          //自带的gson解析器
                .build();
    }


    /**
     * Okhttp的拦截器其实是有两种的，
     * 一种是addInterceptor           添加的是aplication拦截器，他只会在response被调用一次
     * 一种是addNetWorkInterceptor    添加的是网络拦截器，他会在在request和resposne是分别被调用一次
     *
     * @return
     */
    public OkHttpClient createOkHttpClient() {
        LauncherTrust launcherTrust = new LauncherTrust();
        //为了保证初始化线程安全的
        synchronized (RetrofitConfig.this) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .retryOnConnectionFailure(true)          //连接失败后是否重新连接
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)   //链接超时
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)     //写入超时
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)      //读取超时
                    .sslSocketFactory(sslSocketFactory(launcherTrust), launcherTrust)
                    .hostnameVerifier(new TrustAnyHostnameVerifierInterceptor())
                    .addInterceptor(new HeaderInterceptor())          //配置通用header
                    .addNetworkInterceptor(new LoggingInterceptor())  //配置log拦截器
                    .build();

//                    .sslSocketFactory(sslSocketFactory(launcherTrust), launcherTrust)
//                    .hostnameVerifier(new TrustAnyHostnameVerifierInterceptor())
//                    .cookieJar(new CookieManger(WcyApplication.getInstance()))
//                    .sslSocketFactory(sslSocketFactory(launcherTrust), launcherTrust)
//                    .hostnameVerifier(new TrustAnyHostnameVerifierInterceptor())
//                    .cache(new Cache(new File(WcyApplication.getmApplicationContext().getExternalCacheDir(), "wcy_cache"), 1024 * 1024 * 100))  //创建文件缓存目录
//                    .authenticator(new TokenAuthentiactor())//限制刷新token次数为3次
//                    .addNetworkInterceptor(new CommonInterceptor())  //配置缓存拦截器
//                    .addInterceptor(new GzipRequestInterceptor())    //配置header拦截器
//                    .addInterceptor(new DownLoadInterceptor(new OnDownLoadProgressListener() {  //配置下载进度的拦截器
//                        @Override
//                        public void updateDownLoadProgress(long read, long count, boolean done) {
//
//                        }
//                    }))
            return client;
        }
    }


    SSLSocketFactory sslSocketFactory(LauncherTrust launcherTrust) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{launcherTrust}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


    class LauncherTrust implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
