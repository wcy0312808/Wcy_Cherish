package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Auther：杨玉安 on 2017/12/19 17:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： Text 各种简单图形   http://blog.csdn.net/harvic880925/article/details/39080931
 */
public class CustomGraph extends View {

    private Context mContext;
    private Paint mPaint;
    private RectF mRectF;

    float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
    private Path mMPath;
    private float mPreX;
    private float mPreY;

    public CustomGraph(Context context) {
        this(context, null);
    }

    public CustomGraph(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mMPath = new Path();

        mPaint = new Paint();

        mPaint.setAntiAlias(true);   //抗锯齿，比较耗费资源，减慢绘制速度。但更加清晰
        mPaint.setDither(true);  //设置图形是否抖动，绘制出来的图像更加饱满，清晰
        mPaint.setColor(Color.RED);  //画笔的颜色
        mPaint.setStrokeWidth(5);   //设置画笔的宽度
        mPaint.setTextSize(30);  //设置画文字时的大小
//        mPaint.setTextScaleX(20); //可以实现文字的拉伸效果
//        mPaint.setTextSkewX(30);  //可以实现文字的清晰效果
//        mPaint.setStrikeThruText(true); //可以实现文字删除效果
//        mPaint.setUnderlineText(true); //可以实现文字的下划线效果
//        mPaint.setTypeface(Typeface.DEFAULT); //设置文字风格
        /**
         * Style.Fill 填充内部
         * Style.Fill_AND_STROKE 填充内部加边缘 和前面一个一样
         * Style.stroke 仅仅是边缘
         */
        mPaint.setStyle(Paint.Style.STROKE); //画笔绘出效果的样式。
        /**
         * radius 阴影的倾斜度
         * dx 水平位移
         * dy 垂直位移
         * color 阴影的颜色
         */
//        mPaint.setShadowLayer(10, 15, 15, Color.GREEN); //设置阴影
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRGB(255, 255, 255); //设置画布的背景

//        drawPoint(canvas);

//        drawLine(canvas);

//        drawRect(canvas);

//        drawPath(canvas);

//        drawText(canvas);

//        drawCircle(canvas);

//        drawArc(canvas);


//        drawRegions(canvas);


        drawQuadTo(canvas);

//        canvas.drawPath(mMPath,mPaint);

//        restoreCanvas(canvas);
    }


    /**
     * 画点
     */
    private void drawPoint(Canvas canvas) {
        mPaint.setStrokeWidth(500); //必须设置，要不然画不出来
        mPaint.setStrokeCap(Paint.Cap.ROUND);  //点的形状是方形还是圆形。
        canvas.drawPoint(100, 100, mPaint);
    }

    /**
     * 绘制直线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        canvas.drawLine(100, 100, 500, 500, mPaint);
    }

    /**
     * 绘制矩形
     */
    private void drawRect(Canvas canvas) {
        mRectF = new RectF(100, 100, 500, 500);  // RectF 和 Rect  都可用来定义一个矩形区域，主要区别是参数类型不同，一个是int类型，一个是float类型

//        canvas.drawRect(mRectF, mPaint);  //普通矩形

        canvas.drawRoundRect(mRectF, 100, 100, mPaint);//绘制带有弧度的矩形
    }

    /**
     * 绘制路径的时候首先需要设置起点，使用moveTo方法，没有使用的话默认起点是（0.9）;
     * 然后用lineTo方法指定需要连接的点
     * 最后要使用path.close();
     * 这样最后的点和起点连接到一起了。
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(3);
//        Path path = new Path();
//        path.moveTo(100, 100);   //设置起点
//        path.lineTo(200, 100);
//        path.lineTo(150, 150);
//        path.lineTo(150, 350);
//        path.close();            //闭环
//        canvas.drawPath(path, mPaint);


        //先创建两个大小一样的路径
        //第一个逆向生成
//        Path CCWRectpath = new Path();
//        RectF rect =  new RectF(50, 50, 240, 200);
//        CCWRectpath.addRect(rect, Path.Direction.CCW);
//
//        //第二个顺向生成
//        Path CWRectpath = new Path();
//        RectF rect2 =  new RectF(290, 50, 480, 200);
//        CWRectpath.addRect(rect2, Path.Direction.CW);
//
//        //先画出这两个路径
////        canvas.drawPath(CCWRectpath, mPaint);
////        canvas.drawPath(CWRectpath, mPaint);
//
//       //依据路径写出文字
//        String text="风萧萧兮易水寒，壮士一去兮不复返";
//        mPaint.setColor(Color.GRAY);
//        mPaint.setTextSize(35);
//        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, mPaint);//逆时针生成
//        canvas.drawTextOnPath(text, CWRectpath, 0, 18, mPaint);//顺时针生成


        Path path = new Path();
        RectF rect = new RectF(50, 50, 240, 200);
        path.addRoundRect(rect, 10, 15, Path.Direction.CCW);

        RectF rect1 = new RectF(290, 50, 480, 200);
        float radii[] = {10, 15, 20, 25, 30, 35, 40, 45}; //必须传递八个数，可以控制每个角度的弧度程度
        path.addRoundRect(rect1, radii, Path.Direction.CCW);

        canvas.drawPath(path, mPaint);
    }

    private void drawText(Canvas canvas) {
//        canvas.drawText(new char[]{'h', 'w', 'g', 't', '3', '1', '3', '3'}, 0, 7, 100, 100, mPaint);  //第二三个参数代表的是数组中的那个需要绘制

        canvas.drawPosText(new char[]{'h', 'w', 'g', 't'}, 0, 4, new float[]{50.0f, 50.0f, 100.0f, 100.0f, 250.0f, 250.0f, 500.0f, 500.0f,}, mPaint); //按照指定的路径绘制文本
    }


    /**
     * 画圆
     */
    private void drawCircle(Canvas canvas) {
        RectF rect = new RectF(100, 10, 300, 100);
//        canvas.drawCircle(150, 150, 100, mPaint);

        canvas.drawRect(rect, mPaint);//画矩形

        mPaint.setColor(Color.GREEN);  //设置画笔颜色

        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawOval(rect, mPaint);  //画椭圆，主要是画一个矩形里面的椭圆
    }

    /**
     * 画弧
     * 弧是椭圆的一部分，而椭圆又是矩形的一部分，所以弧也相当于是矩形的一部分
     */
    private void drawArc(Canvas canvas) {
        RectF rect = new RectF(100, 10, 300, 100);
        /**
         * rect 在那个矩形面积中画弧形
         * startAngle 开始的角度。一x的正方向为0度
         * sweepAngle 持续扫过的角度
         * usecenter  是否要绘制两条边
         */
        canvas.drawArc(rect, 0, 90, false, mPaint);
    }

    private void drawRegions(Canvas canvas) {
//        Region region = new Region(10,10,100,100);
//        region.set(100,100,200,200);    //set方法，相当于一个构造方法，重新赋值的作用
//        drawRegion(canvas,region,mPaint);


        //构造一个椭圆路径
        Path ovalPath = new Path();
//        RectF rect =  new RectF(50, 50, 200, 500);
//        ovalPath.addOval(rect, Path.Direction.CCW);
        //SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
        Region rgn = new Region();
        rgn.setPath(ovalPath, new Region(50, 50, 200, 200));
        //画出路径
        drawRegion(canvas, rgn, mPaint);
    }


    /**
     * 绘制贝塞尔曲线
     * //二阶贝赛尔
     * quadTo(float x1, float y1, float x2, float y2)
     * rQuadTo(float dx1, float dy1, float dx2, float dy2)
     * //三阶贝赛尔
     * cubicTo(float x1, float y1, float x2, float y2,float x3, float y3)
     * rCubicTo(float x1, float y1, float x2, float y2,float x3, float y3)
     * <p>
     * <p>
     * 整条线的起始点是通过Path.moveTo(x,y)来指定的，如果初始没有调用Path.moveTo(x,y)来指定起始点，则默认以控件左上角(0,0)为起始点；
     * 而如果我们连续调用quadTo()，前一个quadTo()的终点，就是下一个quadTo()函数的起点
     */
    private void drawQuadTo(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 300);  //画贝塞尔曲线的第一步是需要用moveTo来确认原点的。
//        path.quadTo(100, 200, 200, 300);   // 前两个点是控制点，后来个是目标点
//        path.quadTo(300, 400, 400, 300);
//        path.quadTo(500, 200, 600, 300);
//        path.quadTo(700, 400, 800, 300);
//        path.quadTo(900, 200, 1000, 300);
//        path.quadTo(1100, 400, 1200, 300);
//        path.quadTo(1300, 200, 1400, 300);
//        path.quadTo(1500, 400, 1600, 300);
//        path.quadTo(1700, 200, 1800, 300);
//        path.quadTo(1700, 400, 2000, 300);

        path.rQuadTo(100,-100,200,0);
        path.rQuadTo(100,100,200,0);
        path.rQuadTo(100,-100,200,0);
        path.rQuadTo(100,100,200,0);
        path.rQuadTo(100,-100,200,0);
        path.rQuadTo(100,100,200,0);
        path.rQuadTo(100,-100,200,0);
        path.rQuadTo(100,100,200,0);
        path.rQuadTo(100,100,200,0);
        path.rQuadTo(100,-100,200,0);
        canvas.drawPath(path, mPaint);
    }


    private void restoreCanvas(Canvas canvas) {
        mPaint.setFakeBoldText(true); // 将画笔设置为粗体
        canvas.drawText("00000000", 0, 75, mPaint);
//        canvas.save();       //每次调用都会把当前的画布的状态进行保存，然后放入特定的栈中
        canvas.translate(0, 250);
        canvas.drawText("||||||||||||||||", 0, 75, mPaint);
//        canvas.restore();    //当调用就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画
        canvas.drawText("—————", 0, 75, mPaint);
    }


    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();

                mMPath.moveTo(mPreX, mPreY); //设置其实点
                return true;  //是将该事件消费掉，后续的绘制动作也会继续执行，达到连续绘制的过程
            case MotionEvent.ACTION_MOVE:
//                mMPath.lineTo(event.getX(), event.getY());  //设置将要连接的点， 但直接通过画路径的方式，容易出现连接点不平滑的现象
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mMPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
//                postInvalidate();  // 任何线程都可以，原理就是利用了Handler发送消息调用invalidate;
                invalidate(); //必须保证在主线程
                break;
        }


        return super.onTouchEvent(event);
    }

    public void reset(){
        mMPath.reset();
        invalidate();
    }
}
