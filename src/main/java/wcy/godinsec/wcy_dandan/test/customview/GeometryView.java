package wcy.godinsec.wcy_dandan.test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Auther：杨玉安 on 2018/5/17 11:50
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class GeometryView extends View {
    private Context mContext;

    public GeometryView(Context context) {
        super(context);
        mContext = context;
    }

    //重写onDraw()函数，在每次重绘时自主实现绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();//实例化画笔
        paint.setAntiAlias(true);//坑矩阵，主要是为了实现画图的平滑度
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);//设置画笔的宽度为5
//        paint.setShadowLayer(10, 15, 15, Color.GREEN); //设置阴影
        paint.setColor(Color.BLUE);

        canvas.drawRGB(255, 255, 255);

//        //画圆
//        canvas.drawCircle(190,200,150,paint);
//
//        //划线
//        float []Linespts={250,10,100,100,200,200,400,400};
//        canvas.drawLines(Linespts, paint);
//
//        float []Pointpts={500,100,500,200,500,300,500,400};
//        canvas.drawPoints(Pointpts, paint);


        Path path = new Path();
        RectF rect = new RectF(50,50,200,500);
        path.addOval(rect, Path.Direction.CCW);
        Region region = new Region();
        region.setPath(path,new Region(50,50,200,200));
        drawRegion(canvas, region, paint);
    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect, paint);
        }
    }


}
