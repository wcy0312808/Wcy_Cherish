package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.widget.RemoteViews;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import android.app.Notification;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.application.WcyApplication;

/**
 * Created by yuan on 2017/6/1.
 */

public class UmengPushUtils {

    private static final String TAG = "UmengPushUtils";


    //运用内部类的方法来实现单列
    private static class UmengPushGingle
    {
        private static UmengPushUtils umengPushUtils = new UmengPushUtils();
    }

    public static UmengPushUtils getInstanceUmengPushUtils()
    {
        return UmengPushGingle.umengPushUtils;
    }

    public void RegisterPushAgent() {
        //获取一个推送的实例   友盟提供
        PushAgent mPushAgent = PushAgent.getInstance(WcyApplication.getInstance());

        mPushAgent.setDebugMode(false);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtils.e("token = ="+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e( s + s1);
            }
        });


        //自定义CustomNotificationHandler利用里面的action对应不同的value来组合友盟统计实现统计各种推送的次数
        CustomNotificationHandlerUtils notificationClickHandler = new CustomNotificationHandlerUtils();

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        LogUtils.e("builder_id====="+msg.builder_id);
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        builder.setContent(myNotificationView)
                            .setLargeIcon(getLargeIcon(context, msg))
                            .setSmallIcon(getSmallIconId(context, msg))
                            .setTicker(msg.ticker)
                            .setAutoCancel(false);
                        Notification mNotification = builder.build();
                        //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
                        mNotification.contentView = myNotificationView;
                        return mNotification;

                    default:
                        LogUtils.e("builder_id====="+msg.builder_id);
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setNotificationClickHandler(messageHandler);

        //默认情况下，同一台设备在1分钟内收到同一个应用的多条通知时，不会重复提醒，同时在通知栏里新的通知会替换掉旧的通知。可以通过如下方法来设置冷却时间
        mPushAgent.setMuteDurationSeconds(10000);

        //通知栏最多显示数量,当参数为0时，表示不合并通知。
//        mPushAgent.setDisplayNotificationNumber(0);
    }




}
