package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;

/**
 * Created by Seeker on 2016/9/9.
 */

public final class RxJavaHelper {

//    private ExecutorService exector = null;
//
//    private RxJavaHelper(){
//        exector = Executors.newFixedThreadPool(5,new ExecutorThreadFactory());
//    }
//
//    private static final class Factory{
//        private static final RxJavaHelper instance = new RxJavaHelper();
//    }
//
//    public static RxJavaHelper getInstance(){
//        return Factory.instance;
//    }
//
//    /**
//     * 设置线程
//     * @param observable
//     * @return
//     */
//    public <T>Observable<T> setThread(Observable<T> observable){
//        return observable.subscribeOn(Schedulers.from(exector))
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    /**
//     * 上传应用点击次数
//     * @param pck
//     * @param title
//     */
//    public void uploadClickCount(final Context context,final String pck, final String title){
//        Validate.notNull(context,"context");
//        RxJavaHelper.getInstance().setThread(Observable.create(new Observable.OnSubscribe<Request>() {
//            @Override
//            public void call(Subscriber<? super Request> subscriber) {
//                subscriber.onNext(Request.createPostClickAppInfoRequest(pck,title));
//            }
//        }))
//                .subscribe(new Action1<Request>() {
//                    @Override
//                    public void call(Request request) {
//                        RetrofitManger.getInstance().postClickAppInfo(request);
//                    }
//                });
//
//    }
//
//
//
//    public void postChannelShowTimes(final ArrayList<ChanneAppRes.Body> channeAppResBody, final int type) {
//
//        RxJavaHelper.getInstance().setThread(Observable.create(new Observable.OnSubscribe<ChannelAppReq[]>() {
//            @Override
//            public void call(Subscriber<? super ChannelAppReq[]> subscriber) {
//                int size = channeAppResBody.size();
//                ChannelAppReq [] requests = new ChannelAppReq[size];
//                for (int i = 0;i<requests.length;i++){
//                    ChanneAppRes.Body body = channeAppResBody.get(i);
//                    requests[i] = ChannelAppReq.createPostChannelTimes(body.getPackage_name(),type,body.getId());
//                }
//                subscriber.onNext(requests);
//            }
//        })).subscribe(new Action1<ChannelAppReq[]>() {
//            @Override
//            public void call(ChannelAppReq[] requests) {
//                for (int i = 0;i<requests.length;i++){
//                    RetrofitManger.getInstance().postChannelShowTimeInfo(requests[i]);
//                }
//            }
//        });
//    }
//
//
//
//    public void postChannelActiveTimes(final ChanneAppRes.Body channeAppResBody, final int type) {
//
//        RxJavaHelper.getInstance().setThread(Observable.create(new Observable.OnSubscribe<ChannelAppReq>() {
//            @Override
//            public void call(Subscriber<? super ChannelAppReq> subscriber) {
//                subscriber.onNext(ChannelAppReq.createPostChannelTimes(channeAppResBody.getPackage_name(),type,channeAppResBody.getId()));
//            }
//        })).subscribe(new Action1<ChannelAppReq>() {
//            @Override
//            public void call(ChannelAppReq request) {
//                RetrofitManger.getInstance().postChannelShowTimeInfo(request);
//            }
//        });
//    }
//
//
//    public void postChannelDownLoadTimes(final ChanneAppRes.Body channeAppResBody, final int type) {
//
//        RxJavaHelper.getInstance().setThread(Observable.create(new Observable.OnSubscribe<ChannelAppReq>() {
//            @Override
//            public void call(Subscriber<? super ChannelAppReq> subscriber) {
//                subscriber.onNext(ChannelAppReq.createPostChannelTimes(channeAppResBody.getPackage_name(),type,channeAppResBody.getId()));
//            }
//        })).subscribe(new Action1<ChannelAppReq>() {
//            @Override
//            public void call(ChannelAppReq request) {
//                RetrofitManger.getInstance().postChannelShowTimeInfo(request);
//            }
//        });
//    }
//
//
//
//    private static class ExecutorThreadFactory implements ThreadFactory {
//
//        private final AtomicInteger mCount = new AtomicInteger(1);
//
//        @Override
//        public Thread newThread(Runnable r) {
//            Thread thread = new Thread(r,"PrivacyLauncherThread_id:"+mCount.getAndIncrement());
//            thread.setPriority(Thread.NORM_PRIORITY -1);
//            return thread;
//        }
//    }

}
