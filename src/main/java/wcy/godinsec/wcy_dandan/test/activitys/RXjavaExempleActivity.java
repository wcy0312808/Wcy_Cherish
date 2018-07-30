package wcy.godinsec.wcy_dandan.test.activitys;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;

/**
 * Auther：杨玉安 on 2018/7/13 15:01
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class RXjavaExempleActivity extends BaseActivity {
    @Override
    protected int setContentlayout() {
        return 0;
    }


    @Override
    protected void initialize() {
        super.initialize();
        //创建订阅者（观察者）也就是获取（接收）数据的一方，它决定事件触发的时候将有怎样的行为。
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //创建观察者（订阅者）也就是接收（获取）消息的一方，在RXjava中，它是Observer的抽象类，同时在使用Observer时，最后还是会转换成一个Subscriber再使用。
        //在1中提供了一个unsubscribe的方法
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            //事件队列完结，Rxjava不仅把每个事件单独处理，同时还会把他们看成是一个队列，当不再有新的onNest发出时，需要触发onCompleted方法。
            @Override
            public void onComplete() {

            }
        };


        //创建一个Observable，即被观察者，用于获取数据，1中回传过来的是一个Subscriber 2 中是ObservablerEmitter(事件发射器)
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {

            }
        });
    }
}
