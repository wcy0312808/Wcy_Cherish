package wcy.godinsec.wcy_dandan.network;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.interfaces.OnSpinkitDialogListener;

/**
 * Auther：杨玉安 on 2017/7/7 14:26
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class NetWorkDialogManager {
    //控制该dialog的工具类
    private OnSpinkitDialogListener mOnSpinkitDialogListener;

    private Context mContext;

    private Dialog mDialog;

    private LayoutInflater mLayoutInflater;


    //私有化构造方法
    public NetWorkDialogManager(Context context, OnSpinkitDialogListener mOnSpinkitDialogListener) {
        this.mContext = context;
        this.mOnSpinkitDialogListener = mOnSpinkitDialogListener;
        mLayoutInflater = LayoutInflater.from(mContext);
        initDialog();
    }

    ;

    //初始化dialog
    public void initDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.loadstyle);                  //实例化Dialog
            mDialog.setCanceledOnTouchOutside(false);                             //设置点击back按键候dialog可以消失
            View view = mLayoutInflater.inflate(R.layout.dialog_spinkit, null); //填充视图
            mDialog.setContentView(view);                                         //设置视图
        }

        /**
         * BaseDialog 的点击事件
         */
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mOnSpinkitDialogListener != null) {
                    mOnSpinkitDialogListener.onSpinkitDialogCancel();  //回调自定义的dialog消失的方法
                }
            }
        });
    }


    /**
     * dialog消失
     */
    public void dissmissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
            mLayoutInflater = null;
            mContext = null;
        }
    }

    /**
     * dialog显示
     */
    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing() && mContext != null) {
            mDialog.show();
        }
    }
}

