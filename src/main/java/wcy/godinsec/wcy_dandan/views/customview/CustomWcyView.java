package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import wcy.godinsec.wcy_dandan.R;

/**
 * Auther：杨玉安 on 2018/1/16 14:19
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomWcyView extends View {
    private int mTextSize;
    private String mTextTitle;
    private int mTextColor;


    private Paint mPaint; //画笔
    private Rect mRect;  //View所以的矩形区域


    public CustomWcyView(Context context) {
        this(context, null);
    }

    public CustomWcyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWcyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //从我们在res/values/attr/declear-styleable下面定义的所有属性通过xml解析器解析出来
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.customWcyView, defStyleAttr, 0);

        int attributeCount = typedArray.getIndexCount();

        for (int i = 0; i < attributeCount; i++) {
            int attributeTitle = typedArray.getIndex(i);  //获取到自定义属性的标志值
            switch (attributeTitle) {
                case R.styleable.customWcyView_wcyview_textSize:
                    mTextSize = typedArray.getDimensionPixelSize(attributeTitle, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.customWcyView_wcyview_textTitle:
                    mTextTitle = typedArray.getString(attributeTitle); //如果是title的标记值，说明该参数是string类型的，所以只用用getString通过标记值获取出来xml中设置的内容
                    break;
                case R.styleable.customWcyView_wcyview_textColor:
                    mTextColor = typedArray.getColor(attributeTitle, Color.BLACK);
                    break;
            }
        }

        typedArray.recycle();   //切记，一定要销毁


        mPaint = new Paint(); //初始化画笔
        mPaint.setTextSize(mTextSize);

        mRect = new Rect();  //实例化view所在矩形区域

        mPaint.getTextBounds(mTextTitle, 0, mTextTitle.length(), mRect); //设置当前需要绘制的view内容所占的大小

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);   //getMeasureWidth 或者getMeasureHight 都是在测量过后就可以使用了


        mPaint.setColor(mTextColor);
        canvas.drawText(mTextTitle, getWidth()/2 , getHeight()/2, mPaint);

    }
}
