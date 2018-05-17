package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import wcy.godinsec.wcy_dandan.R;

/**
 * Auther：杨玉安 on 2017/12/26 17:07
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：联系矩阵的view
 */
public class CustomMatrix extends View {
    private Paint mPaint ;
    private Bitmap mBitmap;

    public CustomMatrix(Context context) {
        this(context,null);
    }

    public CustomMatrix(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.seekbar_thumb_pressed);

        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setARGB(255,200,100,100);

        canvas.drawBitmap(mBitmap,null,new Rect(0,0,500,500*mBitmap.getHeight()/mBitmap.getWidth()),mPaint);

        canvas.translate(550,0);

        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));


        canvas.drawBitmap(mBitmap, null, new Rect(0, 0, 500, 500 * mBitmap.getHeight() / mBitmap.getWidth()), mPaint);
    }
}
