package wcy.godinsec.wcy_dandan.test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Auther：杨玉安 on 2018/5/21 17:35
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class BlogTextView extends View {
    private Paint mPaint;
    private int mBaseLineX = 0;
    private int mBaseLineY = 200;//自定义的基线暂定为200

    public BlogTextView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mBaseLineX, mBaseLineY, 3000, mBaseLineY, mPaint); //两个坐标对应一个点

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(120);//以PX为单位
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("yangyuan", 200, mBaseLineY, mPaint);
    }
}
