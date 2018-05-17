package wcy.godinsec.wcy_dandan.manager;

import android.content.Context;
import android.view.WindowManager;

import wcy.godinsec.wcy_dandan.views.customview.FloatNormalView;

/**
 * Created by shiwe on 2017/3/7.
 * 悬浮窗管理
 * 创建，移除
 * 单例模式
 */

public class MyWindowManager {

    private FloatNormalView normalView;

    private static MyWindowManager instance;

    private MyWindowManager() {
    }

    public static MyWindowManager getInstance() {
        if (instance == null)
            instance = new MyWindowManager();
        return instance;
    }


    /**
     * 创建小型悬浮窗
     */
    public void createNormalView(Context context) {
        if (normalView == null)
            normalView = new FloatNormalView(context);
    }

    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeNormalView(Context context) {
        if (normalView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(normalView);
            normalView = null;
        }
    }
}
