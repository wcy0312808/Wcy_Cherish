package wcy.godinsec.wcy_dandan.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseFragment;

/**
 * Auther：杨玉安 on 2017/8/25 11:26
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AudioFragment extends BaseFragment {
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        TextView textView = new TextView(getContext());

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(24);
        textView.setTextColor(Color.BLUE);
        textView.setText("音频");

        return textView;
    }

    @Override
    protected int setFragmentLayoutID() {
        return 0;
    }
}
