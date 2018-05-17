package wcy.godinsec.wcy_dandan.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wcy.godinsec.wcy_dandan.R;

/**
 * Auther：杨玉安 on 2018/4/11 14:22
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout2,container,false);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param");
            TextView tv =  (TextView)view.findViewById(R.id.textview);
            tv.setText(mParam1);
        }
        return view;
    }

    public static Fragment2 newInstance(String text){
        Fragment2 fragment2 = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        fragment2.setArguments(bundle);
        return fragment2;
    }
}
