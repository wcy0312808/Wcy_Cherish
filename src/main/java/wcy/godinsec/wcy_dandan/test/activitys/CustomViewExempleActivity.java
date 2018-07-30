package wcy.godinsec.wcy_dandan.test.activitys;

import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.test.customview.BlogTextView;
import wcy.godinsec.wcy_dandan.test.customview.CanvasView;
import wcy.godinsec.wcy_dandan.test.customview.GeometryView;
import wcy.godinsec.wcy_dandan.test.customview.PathView;
import wcy.godinsec.wcy_dandan.test.customview.RegionView;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/17 11:56
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomViewExempleActivity extends BaseActivity {
    @BindView(R.id.rl_group)
    RelativeLayout mRlGroup;
    private PathView mPathView;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_custom_wcyview;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mPathView = new PathView(this);
        mRlGroup.addView(mPathView);
        mPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("==OnClickListener");
                mPathView.reset();
            }
        });
    }
}
