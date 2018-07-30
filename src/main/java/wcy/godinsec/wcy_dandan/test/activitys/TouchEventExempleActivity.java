package wcy.godinsec.wcy_dandan.test.activitys;

import android.view.MotionEvent;
import android.widget.Switch;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/6/20 11:51
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class TouchEventExempleActivity extends BaseActivity {
    @Override
    protected int setContentlayout() {
        return R.layout.activity_touch_layout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_DOWN ="+super.dispatchTouchEvent(ev));
                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_DOWN =");
                break;

            case MotionEvent.ACTION_UP:
//                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_UP ="+super.dispatchTouchEvent(ev));
                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_UP =");
                break;

            case MotionEvent.ACTION_MOVE:
//                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_MOVE ="+super.dispatchTouchEvent(ev));
                LogUtils.e("Activity - - dispatchTouchEvent = ACTION_MOVE =");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("Activity - - ontouchEvent = ACTION_DOWN =");
//                LogUtils.e("Activity - - ontouchEvent = ACTION_DOWN ="+super.onTouchEvent(event));
                break;

            case MotionEvent.ACTION_UP:
                LogUtils.e("Activity - - ontouchEvent = ACTION_UP =");
//                LogUtils.e("Activity - - ontouchEvent = ACTION_UP ="+super.onTouchEvent(event));
                break;

            case MotionEvent.ACTION_MOVE:
                LogUtils.e("Activity - - ontouchEvent = ACTION_MOVE =");
//                LogUtils.e("Activity - - ontouchEvent = ACTION_MOVE ="+super.onTouchEvent(event));
                break;
        }
        return super.onTouchEvent(event);
    }

}
