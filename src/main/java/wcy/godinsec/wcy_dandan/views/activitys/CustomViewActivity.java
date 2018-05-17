package wcy.godinsec.wcy_dandan.views.activitys;

import android.widget.ImageView;
import android.widget.SeekBar;

import butterknife.BindView;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.manager.MyWindowManager;
import wcy.godinsec.wcy_dandan.utils.StateBarTranslucentUtils;
import wcy.godinsec.wcy_dandan.views.customview.CustomFloatImageButton;

/**
 * Auther：杨玉安 on 2017/11/13 14:18
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomViewActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.custom_imagebutton)
    CustomFloatImageButton mCustomImagebutton;
    @BindView(R.id.imageView)
    ImageView mImageView;
    /*====================================customWcyView=====================start=============*/



   /*====================================customWcyView======================end============*/

    @Override
    protected int setContentlayout() {
        return R.layout.activity_custom_wcyview;

    }

    @Override
    protected void initViews() {
        super.initViews();
        MyWindowManager.getInstance().createNormalView(this.getApplicationContext());
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
