package wcy.godinsec.wcy_dandan.views.activitys;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.utils.StateBarTranslucentUtils;

/**
 * Auther：杨玉安 on 2018/2/5 20:28
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class NotifictionActivity extends BaseActivity {
    @Override
    protected int setContentlayout() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        return R.layout.activity_down_laod;
    }

    public NotificationManager mNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//        mBuilder.setContentTitle("测试标题")//设置通知栏标题
//                .setContentText("测试内容") //设置通知栏显示内容</span>
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//                //  .setNumber(number) //设置通知集合的数量
//                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//                //  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
//                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
//                .setSmallIcon(R.mipmap.share_weibo_icon)//设置通知小ICON
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo));
//
//
//        mBuilder.setContentTitle("测试标题")
//                .setContentText("测试内容")
//                .setNumber(5)//显示数量
//                .setTicker("测试通知来啦");//通知首次出现在通知栏，带上升动画效果的
//        mNotificationManager.notify(1, mBuilder.build());
    }

//    public PendingIntent getDefalutIntent(int flags) {
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
//        return pendingIntent;
//    }
}
