package wcy.godinsec.wcy_dandan.views.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Auther：杨玉安 on 2017/12/20 16:58
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class MyNestedSrollView extends NestedScrollView {
    public MyNestedSrollView(Context context) {
        this(context,null);
    }

    public MyNestedSrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNestedSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float mDownPosX = 0;
    private float mDownPosY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
