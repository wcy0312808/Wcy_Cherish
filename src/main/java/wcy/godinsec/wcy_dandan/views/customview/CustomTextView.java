package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import wcy.godinsec.wcy_dandan.R;

/**
 * Auther：杨玉安 on 2017/12/11 14:54
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomTextView extends View {
    private int mTitleSize;
    private int mTitleColor;
    private String mTitleText;
    private Paint mPaint;  //初始化画笔
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        //TypedArray的是一个存储资源数组的容器,通过obtainStyledAttributes来获取到xml解析器解析出来的自定义的属性
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.percentTextTitle);
//        //获取到当前数组中有几个属性
//        int next = typedArray.getIndexCount();
//        for (int i = 0; i < next; i++) {
//            //通过坐标获取到当前属性的资源名称
//            int attr = typedArray.getIndex(i);
//            switch (attr) {
//                case R.styleable.percentTextTitle_percentTitle:
//                    mTitleText = typedArray.getString(attr);
//                    break;
//
//                case R.styleable.percentTextTitle_percentTitleColor:
//                    mTitleColor = typedArray.getColor(attr, Color.BLACK);
//                    break;
//
//                case R.styleable.percentTextTitle_percentTitleSize:
//                    mTitleSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
//                    break;
//            }
//        }
        //获取完毕记得清除
//        typedArray.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleSize);
        mPaint.setUnderlineText(true);//设置下划线
        mPaint.setStrikeThruText(true);//设置带有删除线效果
        mPaint.setAntiAlias(true); //抗锯齿功能
        mPaint.setStyle(Paint.Style.FILL); //设置填充画笔颜色
        mPaint.setStrokeWidth(30);  //设置画笔宽度
        mPaint.setShadowLayer(10,15,20,Color.GREEN);  //设置阴影，阴影的倾斜度，水平位移，垂直位移，颜色

//        mPaint.setColor(mTitleColor);
        //文本的联合边界，主要是规范文本大小
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    /**
     * 系统的测量方法，这里我们不做处理
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //MeasureSpec高两位是代表模式，指测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //MeasureSpec低30位是代表大小，这里又区分不同的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //MeasureSpec.Exactly  一般代表设置了固定的值，或者是MATCH_PARENT
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //设置我们自己定义的属性值
            mPaint.setTextSize(mTitleSize);
            //获得到当前文本内容，长度，还有文本的联合边界
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            int textWidth = mBound.width();
            int desired = (int) (getPaddingLeft()+textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleColor);
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
