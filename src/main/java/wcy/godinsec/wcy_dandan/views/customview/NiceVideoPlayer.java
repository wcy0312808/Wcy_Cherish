package wcy.godinsec.wcy_dandan.views.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Auther：杨玉安 on 2017/8/2 19:24
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class NiceVideoPlayer extends FrameLayout {
    private Context mContext;

    private NiceVideoPlayer mNiceVideoPlayer;

    private FrameLayout mContainer;

    public NiceVideoPlayer(@NonNull Context context) {
        this(context,null);
    }

    public NiceVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        //实例化主布局
        mContainer = new FrameLayout(mContext);
        mContainer.setBackgroundColor(Color.BLACK); //背景附为黑色
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContainer,params);
    }
}
