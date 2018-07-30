package wcy.godinsec.wcy_dandan.test.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;

/**
 * Auther：杨玉安 on 2018/5/18 15:03
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AnimationLayoutExempleActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.button_one)
    Button mButtonOne;
    @BindView(R.id.button_two)
    Button mButtonTwo;
    @BindView(R.id.line1)
    LinearLayout mLine1;
    private ArrayAdapter mAdapter;
    private int i = 0;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_layout_animation;
    }

    @Override
    protected void initialize() {
        super.initialize();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        mListview.setAdapter(mAdapter);
    }
    @OnClick({R.id.button, R.id.button_one, R.id.button_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                mAdapter.addAll(getData());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.button_one:
                addButtonView();
                break;
            case R.id.button_two:
                removeButtonView();
                break;
        }
    }


    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        return data;
    }

    private void addButtonView(){
        i++;
        Button button = new Button(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        button.setLayoutParams(params);
        mLine1.addView(button,0);
    }

    private void removeButtonView() {
        if (i > 0) {
            mLine1.removeViewAt(0);
        }
        i--;
    }
}
