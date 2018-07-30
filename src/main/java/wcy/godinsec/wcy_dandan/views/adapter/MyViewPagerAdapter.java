package wcy.godinsec.wcy_dandan.views.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Auther：杨玉安 on 2017/9/7 20:23
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private int pager;

    public MyViewPagerAdapter(int pager) {
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager/6;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
