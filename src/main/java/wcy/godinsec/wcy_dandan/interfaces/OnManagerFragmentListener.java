package wcy.godinsec.wcy_dandan.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Auther：杨玉安 on 2017/7/28 19:22
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface OnManagerFragmentListener<T> {
       void onSwitchFragment(Class<T> fragmentClass);
}
