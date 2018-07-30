package wcy.godinsec.wcy_dandan.test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.view.View;

/**
 * Auther：杨玉安 on 2018/5/18 11:51
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class RegionView extends View {
    private Context mContext;
    public RegionView(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect1 = new Rect(100,100,400,200);

        Rect rect2 = new Rect(200,0,300,300);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1,paint);
        canvas.drawRect(rect2,paint);


        Region region = new Region(rect1);
        Region region1 = new Region(rect2);

        region.op(region1, Region.Op.UNION);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

        drawRegion(canvas,region,paint);
    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect, paint);
        }
    }
}
