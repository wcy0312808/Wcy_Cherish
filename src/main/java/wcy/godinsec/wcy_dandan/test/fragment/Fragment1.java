package wcy.godinsec.wcy_dandan.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseFragment;

/**
 * Auther：杨玉安 on 2018/4/11 14:17
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class Fragment1 extends BaseFragment {
    private FragmentActivity mFragmentActivity;

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_layout1;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        Button button = (Button) view.findViewById(R.id.load_fragment2_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment2 fragment2 = Fragment2.newInstance("从Fragment1传来的参数");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.add(R.id.main_layout, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    //这个方法是在Activity创建成功后回调的
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //在这个方法中获取自己的控件
        Button button = (Button) getView().findViewById(R.id.load_fragment2_btn);
        //在这个方法中获取其他Fragment的控件
        TextView textView = (TextView) getActivity().findViewById(R.id.textview);
    }
}
