package wcy.godinsec.wcy_dandan.test.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.presenter.impl.Student;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/2 16:12
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class ReflectActivity extends BaseActivity {

    private Class mStudent;
    private Constructor[] mConstructors;
    private Field[] mFields;
    private Field mField;
    private Method[] mMethods;
    private Method mMethod;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_launcher_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            //必须是完整路径
            mStudent = Class.forName("wcy.godinsec.wcy_dandan.presenter.impl.Student");

            /************************************构造方法*******************************************/
//            getConstructors();


            /************************************Fieled*******************************************/
//            getFields();

            /************************************Fieled*******************************************/
            getMethods();

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("========"+e.getMessage());
        }


    }

    @NonNull
    private void getFields() throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        mFields = mStudent.getFields();//获取到所有公有的字段

        mFields = mStudent.getDeclaredFields();//获取所有字段（包括私有，受保护，默认的）

        Object o = mStudent.getConstructor().newInstance();//公共的无参的构造函数

        mField = mStudent.getField("name");
        mField.set(o, "杨玉安发财");//利用反射给变量赋值，需要有对象的构造函数实例才可以。指明给那个对象的当前字段赋值
        Student student = (Student) o;
        LogUtils.e("========" + student.name);


        mField = mStudent.getDeclaredField("phoneNum");
        mField.setAccessible(true);//暴利反射，接触任何修饰限定
        mField.set(o, "1511005256");
    }

    private void getConstructors() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        //获取所有的共有构造方法
        mConstructors = mStudent.getConstructors();
        //获取所有的构造方法（包括私有的，默认的，受保护的）
        mConstructors = mStudent.getDeclaredConstructors();

        Constructor constructor = mStudent.getConstructor();  //获取公有的无参的构造方法

        constructor = mStudent.getConstructor(char.class);//获取是一个char类型的公共的构造方法
        constructor.setAccessible(true); //暴利访问（忽略掉发访问修饰符）
        constructor.newInstance('男');   //创建该构造方法的声明类的新实例，并用指定的初始化参数初始化该实例。

        constructor = mStudent.getDeclaredConstructor(String.class, String.class);
        constructor.setAccessible(true); //暴利访问（忽略掉发访问修饰符）
        constructor.newInstance("杨玉安", "男");   //创建该构造方法的声明类的新实例，并用指定的初始化参数初始化该实例。
    }

    //    Computer have opend up new era in manufactuing through the techniques of automation
    public void getMethods() throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        mMethods = mStudent.getMethods();//获取所有的公共方法。
        mMethods = mStudent.getDeclaredMethods();//获取所有的方法，包括私有的。

        //获取一个叫show1的方法，并且这个方法的参数是一个String的类型
        mMethod = mStudent.getMethod("show1", String.class);
        Object o = mStudent.getConstructor().newInstance();
        mMethod.invoke(o, "杨玉安发大财");

        mMethod = mStudent.getDeclaredMethod("show4", int.class);
        mMethod.setAccessible(true);
        Object invoke = mMethod.invoke(o, 20);
        LogUtils.e("======" + invoke);

        mMethod = mStudent.getDeclaredMethod("main", String[].class);
        mMethod.setAccessible(true);
        //由于是需要调用静态方法，所以在传值时不需要将反射对象传入进去，只需要传递一个null就好了
        mMethod.invoke(null, new Object[]{new String[]{"a", "b", "c"}});
    }
}
