package wcy.godinsec.wcy_dandan.network;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import wcy.godinsec.wcy_dandan.network.requestbean.DownLoadEntityRequest;
import wcy.godinsec.wcy_dandan.network.responsebean.BaseResponse;
import wcy.godinsec.wcy_dandan.network.exception.ApiException;
import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadObserver;


/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class RetrofitManager extends RetrofitConfig {

    private RetrofitManager() {
        super();
    }

    //静态内部类实现单列模式
    private static final class Factory {
        private static final RetrofitManager instance = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Factory.instance;
    }


    /**
     * 普通的网络异步请求
     *
     * @param observable
     * @param observer
     * @param <T>        回传过来的实际响应体
     */
    public <T> void subscribe(Observable<BaseResponse<T>> observable, final Observer observer) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BaseResponse<T>, T>() {
                    @Override
                    public T apply(BaseResponse<T> tBaseResponse) {
                        if (tBaseResponse.getHead() != null && (!tBaseResponse.getHead().getStatuscode().equals("000000"))) {
                            throw new ApiException(tBaseResponse.getHead().getStatusmsg());  //抛出自定义的异常
                        }
                        //确保不会因为返回一个空的导致map操作符直接走Error的方法
                        if (tBaseResponse.getBody() == null) return (T) "";
                        //返回需要的响应体
                        return tBaseResponse.getBody();
                    }
                }).subscribe(observer);

    }


    //普通的网络异步请求
    public <DownLoadEntity> void downLoadSubscribe(Observable<ResponseBody> observable, final Observer observer) {
        observable.subscribe(observer);
    }


    /**
     * 上传文件
     *
     * @param files
     */
    public void upLoadFiles(File... files) {
        Map<String, RequestBody> partMap = new HashMap<>();
        for (File file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);   //创建一个image类型的Reqeust
            partMap.put("file\";filename = \"" + file.getName() + "\"", fileBody);
        }
    }


    public void downLoadApp(DownLoadObserver downLoadObserver) {
        downLoadSubscribe(mRetrofit.create(RetrefitApi.class).download("bytes=" + downLoadObserver.getDownLoadEntity().getDown_Progress() + "-", downLoadObserver.getDownLoadEntity().getApp_Link()), downLoadObserver);
    }

    //获取游戏下载列表，属于公司内部网址，切勿经常使用
    public void getGameList(DownLoadEntityRequest request, RxObserver<ArrayList<DownLoadEntityResponse>> rxObserver) {
        subscribe(mRetrofit.create(RetrefitApi.class).getGameList(request), rxObserver);
    }
}
