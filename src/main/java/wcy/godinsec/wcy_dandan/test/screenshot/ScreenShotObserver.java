package wcy.godinsec.wcy_dandan.test.screenshot;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;

import wcy.godinsec.wcy_dandan.utils.ImageUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/3/1 11:43
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class ScreenShotObserver extends FileObserver {
    private static final String TAG = "ScreenShotObserver";
    private static final String PATH = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";

    private OnScreenshotTakenListener listener;
    private String lastTakenPath;

    public ScreenShotObserver(OnScreenshotTakenListener listener) {
        super(PATH, FileObserver.CLOSE_WRITE);
        this.listener = listener;
    }

    @Override
    public void onEvent(int event, String path) {
        LogUtils.e("========="+path);
        if (path==null || event!=FileObserver.CLOSE_WRITE){

        }
        else if (lastTakenPath!=null && path.equalsIgnoreCase(lastTakenPath)){

        }
        else {
            lastTakenPath = path;
            File file = new File(PATH+path);
            ImageUtils.imgToBase64(PATH+path);
            listener.onScreenshotTaken(Uri.fromFile(file));
        }
    }

    public void start() {
        super.startWatching();
    }

    public void stop() {
        super.stopWatching();
    }


  interface OnScreenshotTakenListener {
        void onScreenshotTaken(Uri uri);
        void onScreenshotTaken(String uri);
    }
}
