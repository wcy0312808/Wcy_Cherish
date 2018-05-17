package wcy.godinsec.wcy_dandan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseFragment;

/**
 * Auther：杨玉安 on 2017/8/25 11:26
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AtlasFragment extends BaseFragment {

    @BindView(R.id.kenBurnsView)
    com.flaviofaria.kenburnsview.KenBurnsView mKenBurnsView;

    @Override
    public void onResume() {
        super.onResume();
        mKenBurnsView.resume();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(3000, new LinearInterpolator());
        mKenBurnsView .setTransitionGenerator(generator);
    }

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_audio_layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        mKenBurnsView.pause();
    }
}
