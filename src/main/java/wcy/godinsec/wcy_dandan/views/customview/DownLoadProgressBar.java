package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import wcy.godinsec.wcy_dandan.utils.ScreenUtil;

/**
 * Auther：杨玉安 on 2018/3/23 15:15
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadProgressBar extends ProgressBar {
    private Paint mPaint;
    private String mContent; //在view中间显示的内容
    private Rect mRect;

    public DownLoadProgressBar(Context context) {
        this(context, null);
    }

    public DownLoadProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownLoadProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(ScreenUtil.dip2px(context, 14));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.getTextBounds(mContent, 0, mContent.length(), mRect); //获取内容所需要占用的区域
        int x = (getWidth() / 2) - mRect.centerX(); //获取整个view的宽度，然后减去内容的宽度
        int y = (getHeight() / 2) - mRect.centerY();
        canvas.drawText(mContent, x, y, mPaint);
    }

    public void setText(String text) {
        mContent = text;
        //刷新
        invalidate();
    }
}
