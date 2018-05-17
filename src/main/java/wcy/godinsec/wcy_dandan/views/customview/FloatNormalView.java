package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.manager.MyWindowManager;

/**
 * Created by shiwe on 2017/3/7.
 * 缩小的悬浮窗
 */
public class FloatNormalView extends LinearLayout {

    private Context context = null;
    private ImageView view = null;
    private WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    private static WindowManager windowManager;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private boolean initViewPlace = false;
    private MyWindowManager myWindowManager;
    private boolean isControlViewShowing = false;
    private int mScreenWidth;
    private int mScreenHeight;
    private long mLastTime ;
    private long mCurrentTime;
    private float mLastX;
    private float mLastY;
    private float mStartX;
    private float mStartY;

    public FloatNormalView(Context context) {
        super(context);
        this.context = context;
        myWindowManager = MyWindowManager.getInstance();
        LayoutInflater.from(context).inflate(R.layout.float_normal_view, this);
        view = (ImageView) findViewById(R.id.iv_show_control_view);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initLayoutParams();
        initEvent();
    }


    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        //屏幕宽高
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        //总是出现在应用程序窗口之上。
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;

        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.START | Gravity.TOP;
        //指定位置
        lp.x = mScreenWidth - view.getLayoutParams().width * 2;
        lp.y = mScreenHeight / 2 + view.getLayoutParams().height * 2;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, lp);
    }

    /**
     * 设置悬浮窗监听事件
     */
    private void initEvent() {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastTime = System.currentTimeMillis();
                        if (!initViewPlace) {
                            initViewPlace = true;
                            //获取初始位置
                            mTouchStartX += (event.getRawX() - lp.x);
                            mTouchStartY += (event.getRawY() - lp.y);
                        } else {
                            //根据上次手指离开的位置与此次点击的位置进行初始位置微调
                            mTouchStartX += (event.getRawX() - x);
                            mTouchStartY += (event.getRawY() - y);
                        }
                        mStartX = event.getRawX();
                        mStartY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取相对屏幕的坐标，以屏幕左上角为原点
                        x = event.getRawX();
                        y = event.getRawY();
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:
//                        if (initViewPlace) {
//                            //恢复按压效果
//                            setPressed(false);
//                            //Log.i("getX="+getX()+"；screenWidthHalf="+screenWidthHalf);
//                            if (rawX >= mScreenWidth / 2) {
//                                //靠右吸附
//                                animate().setInterpolator(new DecelerateInterpolator())
//                                        .setDuration(500)
//                                        .xBy(mScreenWidth - getWidth() - getX())
//                                        .start();
//                            } else {
//                                //靠左吸附
//                                ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
//                                oa.setInterpolator(new DecelerateInterpolator());
//                                oa.setDuration(500);
//                                oa.start();
//                            }
//                        }
                        mLastX = event.getRawX();
                        mLastY = event.getRawY();

                        mCurrentTime = System.currentTimeMillis();
                        if(mCurrentTime - mLastTime < 800){
                            if(Math.abs(mStartX- mLastX )< 10.0 && Math.abs(mStartY - mLastY) < 10.0){
                                //处理点击的事件
                                Toast.makeText(context,"可以处理点击事件",Toast.LENGTH_SHORT).show();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }

    /**
     * 更新浮动窗口位置参数
     */
    private void updateViewPosition() {
        lp.x = (int) (x - mTouchStartX);
        lp.y = (int) (y - mTouchStartY);
        windowManager.updateViewLayout(this, lp);
    }
}
