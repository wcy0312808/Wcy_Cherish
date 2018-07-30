package wcy.godinsec.wcy_dandan.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.interfaces.OnManagerFragmentListener;

/**
 * Auther：杨玉安 on 2018/4/12 11:09
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class MyFragmentManager implements OnManagerFragmentListener {
    private FragmentManager mFragmentManager;
    private static Fragment mCurrentFragment;  //当前展示的Fragment

    public MyFragmentManager(FragmentActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public void onSwitchFragment(Class fragmentClass) {
        //通过类名从管理器的栈中查询fragment
        Fragment showFragment = mFragmentManager.findFragmentByTag(fragmentClass.getName());
        if (showFragment == null) {   //首次显示，先创建实例
            try {
                //根据类名重新实例化Fragment，避免了实例化Fragment
                showFragment = (Fragment) fragmentClass.newInstance();

                //想要展示页 == 当前展示页 不做处理
                if (showFragment == null || showFragment == mCurrentFragment) return;

                //首次依附fragment判断
                if (mCurrentFragment == null) {
                    //该页面第一次依附fragment，直接添加
                    mFragmentManager.beginTransaction().add(R.id.fl_content, showFragment, showFragment.getClass().getName()).commitAllowingStateLoss();
                } else {
                    //当前有其他页面显示，调用显示隐藏组合拳
                    mFragmentManager.beginTransaction().add(R.id.fl_content, showFragment, showFragment.getClass().getName()).hide(mCurrentFragment).commitAllowingStateLoss();
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            //已经添加到当前管理器的栈里面无需创建直接
            if (showFragment == mCurrentFragment) return;

            mFragmentManager.beginTransaction().show(showFragment).hide(mCurrentFragment).commitAllowingStateLoss();
        }

        mCurrentFragment = showFragment;
    }
}
