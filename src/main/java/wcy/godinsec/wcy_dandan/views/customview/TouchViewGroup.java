package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/6/20 14:50
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：测试事件分发机制的ViewGroup类
 */
public class TouchViewGroup extends ViewGroup {

    public TouchViewGroup(Context context) {
        this(context, null);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_DOWN =");
//                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_DOWN ="+super.onTouchEvent(event));
                break;

            case MotionEvent.ACTION_UP:
                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_UP =");
//                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_UP ="+super.onTouchEvent(event));
                break;

            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_MOVE =");
//                LogUtils.e("ViewGroup - - ontouchEvent = ACTION_MOVE ="+super.onTouchEvent(event));
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_DOWN =");
//                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_DOWN ="+super.dispatchTouchEvent(event));
                break;

            case MotionEvent.ACTION_UP:
                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_UP =");
//                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_UP ="+super.dispatchTouchEvent(event));
                break;

            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_MOVE =");
//                LogUtils.e("ViewGroup - - dispatchTouchEvent = ACTION_MOVE ="+super.dispatchTouchEvent(event));
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_DOWN =");
//                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_DOWN ="+super.onInterceptTouchEvent(ev));
                break;

            case MotionEvent.ACTION_UP:
                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_UP =");
//                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_UP ="+super.onInterceptTouchEvent(ev));
                break;

            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_MOVE =");
//                LogUtils.e("ViewGroup - - onInterceptTouchEvent = ACTION_MOVE ="+super.onInterceptTouchEvent(ev));
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
