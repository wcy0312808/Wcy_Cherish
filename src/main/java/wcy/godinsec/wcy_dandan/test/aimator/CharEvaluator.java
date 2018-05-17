package wcy.godinsec.wcy_dandan.test.aimator;

import android.animation.TypeEvaluator;

/**
 * Auther：杨玉安 on 2018/5/15 14:53
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt = (int)startValue; //将字符根据ASCII转换成int值，这里需要注意的是字符可以直接转换成int值，反之也可以。
        int endInt = (int)endValue;
        int currentInt = (int) (startInt + (endInt - startInt) * fraction);//fraction 当前动画的进度百分比
        char current = (char) currentInt;//int值转换成字符
        return current;
    }
}
