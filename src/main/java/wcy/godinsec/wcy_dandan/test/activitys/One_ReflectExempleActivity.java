package wcy.godinsec.wcy_dandan.test.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.presenter.impl.Student;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/7/10 10:40
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class One_ReflectExempleActivity extends BaseActivity {
    private Constructor[] mConstructors;
    private Class mClass;

    @Override
    protected int setContentlayout() {
        return 0;
    }

    @Override
    protected void initialize() {
        super.initialize();
        try {
            mClass = Class.forName("wcy.godinsec.wcy_dandan.presenter.impl.Student");

            getConstructors();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    //获取到Constructors(构造函数)
    private void getConstructors() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (mClass == null) return;

        //获取所有的public  constructor
//        mConstructors = mClass.getConstructors();

        //获取所有的constructors (构造方法)
        mConstructors = mClass.getDeclaredConstructors();
        for (Constructor constructor : mConstructors) {
//            LogUtils.e("======="+constructor.toString());
        }


        Constructor constructor = null;
        constructor = mClass.getDeclaredConstructor(String.class, String.class);
        constructor.setAccessible(true);
        Student student = (Student) constructor.newInstance("杨玉安", "平安快乐");

    }
}
