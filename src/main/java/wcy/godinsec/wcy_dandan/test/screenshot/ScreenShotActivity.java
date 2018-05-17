package wcy.godinsec.wcy_dandan.test.screenshot;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.Window;

import wcy.godinsec.wcy_dandan.test.managers.MediaContentObserver;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Created by wei on 16-9-18.
 * <p>
 * 完全透明 只是用于弹出权限申请的窗而已
 */
public class ScreenShotActivity extends Activity {
    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;

    /**
     * 内部存储器内容观察者
     */
    private ContentObserver mInternalObserver;
    /**
     * 外部存储器内容观察者
     */
    private ContentObserver mExternalObserver;


    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如下代码 只是想 启动一个透明的Activity 而上一个activity又不被pause
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setDimAmount(0f);

        LogUtils.e(" ======= " + Build.VERSION.SDK_INT);
        LogUtils.e(" ======= " + android.os.Build.VERSION.RELEASE);
        if (Build.VERSION.SDK_INT >= 21) {

            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);


//        } else {
//            mHandlerThread = new HandlerThread("Screenshot_Observer");
//            mHandlerThread.start();
//            mHandler = new Handler(mHandlerThread.getLooper());
//
//            // 初始化
//            mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, mHandler);
//            mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mHandler);
//
//            // 添加监听
//            this.getContentResolver().registerContentObserver(
//                    MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//                    false,
//                    mInternalObserver
//            );
//            this.getContentResolver().registerContentObserver(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    false,
//                    mExternalObserver
//            );
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == -1 && data != null) {
                    ScreenShotUtil shotter = new ScreenShotUtil(ScreenShotActivity.this);
                    shotter.startScreenShot(new ScreenShotUtil.OnShotListener() {
                        @Override
                        public void onFinish() {

                        }
                    });
                }
            }
        }
    }


    protected void onDestroy() {
        super.onDestroy();

        if (mInternalObserver != null && mExternalObserver != null) {
            // 注销监听
            this.getContentResolver().unregisterContentObserver(mInternalObserver);
            this.getContentResolver().unregisterContentObserver(mExternalObserver);
        }

    }

}