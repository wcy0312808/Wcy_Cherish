package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Auther：杨玉安 on 2017/11/8 19:35
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 类似于历史记录的可变布局
 */
public class Flowlayout extends ViewGroup {

    private int lineWidth = 0;//记录每一行最大的宽度
    private int lineHeight = 0;//记录每一行的高度
    private int height = 0; //记录整个FlowLayout所占的高度
    private int width = 0; //记录整个flowLayout所占的宽度

    public Flowlayout(Context context) {
        this(context, null);
    }

    public Flowlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Flowlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;

        int viewCount = getChildCount();

        for (int i = 0; i < viewCount; i++) {
            View child = getChildAt(i);

            //需要先测量，才能执行下面的步骤
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams layoutParams = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams) {
                layoutParams = (MarginLayoutParams) child.getLayoutParams();
                //获得子空间的高度和宽度
                int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                //这行目前的宽度加上当前子view的宽度是否是大于当前父view的宽度
                if (lineWidth + childWidth > widthSize) {
                    //需要换行
                    width = Math.max(lineWidth, childWidth);
                    height += lineHeight;   //高度都是子布局的高度
                    //因为由于盛不下当前控件，而将此控件调到下一行，所以将子空间的高度和宽度初始化给lineHeight,和lineWidth
                    lineHeight = childHeight;
                    lineWidth = childWidth;
                } else {
                    //否则就累加值lineWidth 和 lineheight
                    lineHeight = Math.max(lineHeight, childHeight);
                    lineWidth += childWidth;
                }

                //如果是最后一行，是不会超出width的范围的
                if(i == viewCount - 1)
                {

                }
            }
        }

        //如果模式是Exactly,代表的意思就是这个view的大小是确切的或者是match，不需要我们计算的，只需要设置xml中的大小就行。如果不是就代表的是wrap
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0,left = 0;//当前坐标的Top坐标和left坐标
        int lineWidth = 0; //当前行的宽度
        int lineHeight = 0;//当前行的高度
        int count = getChildCount();  //子控件的个数



        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);
            MarginLayoutParams layoutParams = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams) {
                layoutParams = (MarginLayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                //getMeasureWidth是父布局的宽度
                if(lineWidth + childWidth < getMeasuredWidth())
                {
                    //不换行,比较上个控件和当前控件那个高度高
                    lineHeight = Math.max(lineHeight,childHeight);
                    lineWidth += childWidth;

                }else {
                    //换行
                    top += lineHeight; //首先调用 += 高度是所有子view的高度和
                    left = 0; //从最左边开始

                    //由于是当前行第一个，所以宽度和高度都是这个子控件的自身属性
                    lineHeight = childHeight;
                    lineWidth = childWidth;
                }


                //计算childView的left ，top ，right ，bottom
                int lc = left + layoutParams.leftMargin;//左坐标+左边距是控件的开始位置
                int tc = top + layoutParams.topMargin; //同样，顶坐标加顶边距，只有换行了才会重新自动赋值
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc,tc,rc,bc);

                //将left设置成下一个控件的起始点
                left += childWidth;
            }
        }
    }

    /*
    如果要自定义ViewGroup支持子控件的layout_margin参数，则自定义的ViewGroup类必须重载generateLayoutParams()函数，
    并且在该函数中返回一个ViewGroup.MarginLayoutParams派生类对象，
    这样才能使用margin参数
    */

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}