package wcy.godinsec.wcy_dandan.test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Auther：杨玉安 on 2018/5/21 16:22
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CanvasView extends View {
    private Context mContext;
    private Paint mPaint;//画笔
    private Rect mRect;

    public CanvasView(Context context) {
        super(context);
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mRect = new Rect(400, 400, 500, 600);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect, mPaint);

//        canvas.save();
//        canvas.clipRect(new RectF(400,400,500,600));
//        canvas.drawColor(Color.RED);

        canvas.restore();
        mPaint.setColor(Color.GREEN);
        canvas.drawColor(Color.YELLOW);
    }
}
