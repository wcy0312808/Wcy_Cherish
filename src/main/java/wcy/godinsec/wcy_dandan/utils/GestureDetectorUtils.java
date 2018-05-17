package wcy.godinsec.wcy_dandan.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Auther：杨玉安 on 2017/8/17 17:35
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class GestureDetectorUtils extends GestureDetector.SimpleOnGestureListener {

    //单击事件
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtils.d("====onSingleTapUp====" + getActionName(e.getAction()));
        return super.onSingleTapUp(e);
    }

    //确认为单击事件，不可能是双击事件
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogUtils.d("====onSingleTapConfirmed====" + getActionName(e.getAction()));
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtils.d("====onLongPress====" + getActionName(e.getAction()));
        super.onLongPress(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogUtils.d("====onScroll====e1==" + getActionName(e1.getAction())+"e2=="+"distanceX="+distanceX+"e2=="+"distanceY="+distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtils.d("====onFling====e1==" + getActionName(e1.getAction())+"e2=="+"distanceX="+velocityX+"e2=="+"distanceY="+velocityY);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        LogUtils.d("====onShowPress====" + getActionName(e.getAction()));
        super.onShowPress(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogUtils.d("====onDoubleTapEvent====" + getActionName(e.getAction()));
        return super.onDoubleTapEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        LogUtils.d("====onDown====" + getActionName(e.getAction()));
        return super.onDown(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        LogUtils.d("====onDoubleTap====" + getActionName(e.getAction()));
        return super.onDoubleTap(e);
    }



    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";
                break;
            }
            default:
                break;
        }
        return name;
    }
}
