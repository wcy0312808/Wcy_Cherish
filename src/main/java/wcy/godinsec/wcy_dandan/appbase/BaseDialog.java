package wcy.godinsec.wcy_dandan.appbase;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Auther：杨玉安 on 2017/8/1 15:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class BaseDialog extends DialogFragment {

    //绑定的Activity
    protected Activity mAttachActivity;

    //取消按键上的字体
    private String mNegativeBtnStr;

    //确定按键上的字体
    private String mPositiveBtnStr;

//    private T mBuilder;


    //绑定当前的Activity
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAttachActivity = activity;
    }

    //使用自定义的XML来展示Dialog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //如果不存在就直接给他实例化出来
        if (container == null) {
            container = new FrameLayout(mAttachActivity);
            container.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
