package wcy.godinsec.wcy_dandan.test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/21 19:42
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class PathView extends View {
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mGusture;
    private float mPreX,mPreY;

    public PathView(Context context) {
        super(context);
        mPaint = new Paint();
        mGusture = new Path();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
//        drawRectPath();
//        drawStraightPath();
//        drawBezierPath();
        canvas.drawPath(mGusture,mPaint);
    }

    //画直线
    private void drawStraightPath() {
        Path path = new Path();
        path.moveTo(10, 10);    //设置起始点
        path.lineTo(200, 200);  //设置终点，但它也可能是以后线段的起始点
        path.lineTo(300, 200);
        path.close();          //形成闭环
        mCanvas.drawPath(path, mPaint);
    }

    //矩形路径
    private void drawRectPath() {
        String s = "风萧萧兮易水寒，壮士一去兮不复返";
        Path path = new Path();
        RectF rect = new RectF(200, 300, 800, 600);
        path.addRect(rect, Path.Direction.CW); //逆时针方向
        mCanvas.drawPath(path, mPaint);
        mPaint.setTextSize(35);
        mPaint.setColor(Color.BLUE);
        mCanvas.drawTextOnPath(s, path, 0, 18, mPaint);
    }

    //贝塞尔曲线路径
    private void drawBezierPath() {
        Path path = new Path();
        path.moveTo(100, 300);
        path.quadTo(200, 200, 300, 300);
        path.quadTo(400, 400, 500, 300);
        path.quadTo(600, 200, 700, 300);
        path.quadTo(800, 400, 900, 300);
        mCanvas.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("ACTION_DOWN  X = "+event.getX() + "  Y = "+event.getY());
                mGusture.moveTo(event.getX(),event.getY());
                mPreX = event.getX();//主要是用来表示手指的前一个点，这个点其实是相当于控制点来使用的
                mPreY = event.getY();
                //返回true，代表当前控件已将消费了按下统建，并且以后的move和up事件都会被执行。不往上一层继续传递，也就是说在activity的OnClickListener。
                //返回false，也是表示当前控件被消费，但是不在执行下面的操作
                return true;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("ACTION_MOVE  X = "+event.getX() + "  Y = "+event.getY());
                mGusture.lineTo(event.getX(),event.getY());
                //PostInvalidate();内部其实是通过Handler发消息来实现的，所以它可以在任何线程，但是正因为这一点，所以它刷新界面的速度没有Invalitate快
//                postInvalidate();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("ACTION_UP  X = "+event.getX() + "  Y = "+event.getY());
                break;
        }
        //默认的返回是true
        return super.onTouchEvent(event);
    }

    public void reset()
    {
        mGusture.reset();
        invalidate();//必须在UI线程中执行，如果是子线程就会报错
    }
}
