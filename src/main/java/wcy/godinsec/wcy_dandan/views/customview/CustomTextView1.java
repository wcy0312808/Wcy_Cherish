package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import wcy.godinsec.wcy_dandan.R;

/**
 * Auther：杨玉安 on 2018/1/2 17:17
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomTextView1 extends View {
    private String mTextTitle;
    private int mTextSize;
    private int mTextColor;
    private int mImageScale;
    private Bitmap mImage;
    private Paint mPaint;
    private Rect mBound;    //文字所在的区域

    public CustomTextView1(Context context) {
        this(context, null);
    }

    public CustomTextView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性 ,TypedArray 其实是一个数组，它里面存放着系统利用xml解析我们传递进去的styleable的所有属性值，而obtainStyledAttributes就是调用系统的xml解析器的过程
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        int count = typedArray.getIndexCount();//获取当前styleable定义了几个属性值
        for (int i = 0; i < count; i++) {
            int styleable = typedArray.getIndex(i);//获取到当前属性的title

            switch (styleable) {
                case R.styleable.CustomTextView_custom_textSize:
                    //大小需要将dp或者sp转换成像素
                    mTextSize = typedArray.getDimensionPixelSize(styleable, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.CustomTextView_custom_textTitle:
                    mTextTitle = typedArray.getString(styleable); //获得xml中写的标题
                    break;

                case R.styleable.CustomTextView_custom_textColor:
                    mTextColor = typedArray.getColor(styleable, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_custom_imageScaleType:
                    mImageScale = typedArray.getInt(styleable, 0);
                    break;

                case R.styleable.CustomTextView_custom_image:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(styleable, 0));
                    break;
            }
        }
        //用完记得销毁
        typedArray.recycle();

        mPaint = new Paint();
        mBound = new Rect();

        mPaint.setTextSize(mTextSize);

        //获取到文本的联合文本，将属性赋值到mBound上面,计算了描绘字体需要的范围
        mPaint.getTextBounds(mTextTitle, 0, mTextTitle.length(), mBound);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextTitle = randomText();
                postInvalidate();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        int height = 0;
        int width = 0;

        //精确的就直接用传递过来的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //从Rect中获取到text的宽
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }


        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else {
//            int imageWidth = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
//
//            int TextWidth = getPaddingLeft() + getPaddingRight() + mBound.width();
//
//
//            if (widthMode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                int desire = Math.max(imageWidth, TextWidth);
//                width = Math.min(desire, widthSize);
//            }
//
//        }
//
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else {
//            int imageheight = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mBound.height();
//
//            int Textheight = getPaddingLeft() + getPaddingRight() + mBound.width();
//
//
//            if (heightMode == MeasureSpec.AT_MOST)// wrap_content
//            {
//                height = Math.min(imageheight, Textheight);
//            }
//
//        }


        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        canvas.drawText(mTextTitle, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }


    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }

        return sb.toString();
    }
}
