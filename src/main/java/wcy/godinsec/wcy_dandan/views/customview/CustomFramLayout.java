package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Auther：杨玉安 on 2017/12/14 14:19
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomFramLayout extends FrameLayout {

    public CustomFramLayout(@NonNull Context context) {
        this(context, null);
    }

    public CustomFramLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFramLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//
//        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);


//        int width;
//        //match_parent accurate
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else {
//            width = 200;    //给一个默认的大小
//            if (widthMode == MeasureSpec.AT_MOST) {  // wrap_content
//                width = Math.min(width, widthSize); //注意取两者之间小的值
//            }
//        }
//
//        int heigth;
//        //match_parent accurate
//        if (heigthMode == MeasureSpec.EXACTLY) {
//            heigth = heigthSize;
//        } else {
//            heigth = 200;    //给一个默认的大小
//            if (heigthMode == MeasureSpec.AT_MOST) {  // wrap_content
//                heigth = Math.min(heigth, heigthSize); //注意取两者之间小的值
//            }
//        }
//
//        if (getChildCount() > 0) {
//            View childView = getChildAt(0);
//            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//        }
//
//        setMeasuredDimension(width, heigth);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(getChildCount() > 0)
        {
            View child = getChildAt(0);
            child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //刷新机制
        invalidate();
    }
}
