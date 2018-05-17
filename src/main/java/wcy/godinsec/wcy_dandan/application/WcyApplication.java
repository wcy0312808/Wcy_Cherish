package wcy.godinsec.wcy_dandan.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import wcy.godinsec.wcy_dandan.utils.UmengPushUtils;


/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class WcyApplication extends Application {
    private static Context mApplicationContent;
    private UmengPushUtils mUmengPushUtils; //实例化友盟推送的工具类
    public static int result = 0;            //请求录制屏幕的返回码
    public static Intent intent = null;     //请求录制屏幕的返回实体

    private RefWatcher refWatcher;          //添加LeakCanary打包时记得注释掉

    //友盟分享各个平台的配置
    static {//微信
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("2671053344", "6e134125c38172c8f9c4e4f5e41326b0", "http://sns.whalecloud.com/sina2/callback");
        //QQ
        PlatformConfig.setQQZone("1106316290", "qBFBcAaYxwNHjpne");
    }


    public static RefWatcher getRefWatcher(Context context) {
        WcyApplication application = (WcyApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    public static Context getInstance() {
        return mApplicationContent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Application的上下文
        mApplicationContent = this;
        //初始化Fresco
        Fresco.initialize(mApplicationContent);


        //初始化友盟分享sdk
        UMShareAPI.get(this);
        //初始化友盟推送sdk
        mUmengPushUtils = UmengPushUtils.getInstanceUmengPushUtils();
        mUmengPushUtils.RegisterPushAgent();

        /** * 初始化common库
         *  参数1:上下文，必须的参数，不能为空
         *  * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         *  * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         *  * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         *  * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "64712ee9a5a98ae583a4a49eae3dfba8");

        //当应用在后台运行超过30秒（默认）再回到前端，将被认为是两个独立的session(启动)
        MobclickAgent.setSessionContinueMillis(30000);

        //如果开发者调用kill或者exit之类的方法杀死进程，请务必在此之前调用
        MobclickAgent.onKillProcess(this);

        //初始化百度地图sdk
//        SDKInitializer.initialize(mApplicationContent);

        //LeakCanary的初始化
        refWatcher = LeakCanary.install(this);
    }


    public void setResult(int result) {
        this.result = result;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

}
