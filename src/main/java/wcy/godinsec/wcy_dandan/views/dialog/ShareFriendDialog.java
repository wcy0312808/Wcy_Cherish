package wcy.godinsec.wcy_dandan.views.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.utils.ToastUtils;
import wcy.godinsec.wcy_dandan.utils.UmengShareUtils;


/**
 * @author yangyuan
 * @describe 自定义居中弹出dialog
 */
public class ShareFriendDialog extends Dialog implements View.OnClickListener {

    private Activity context;

    private int layoutResID;

    /**
     * 要监听的控件id
     */
    private int[] listenedItems;

    private OnCenterItemClickListener listener;
    private Window window;

    public ShareFriendDialog(Activity context, int layoutResID, int[] listenedItems) {
        super(context, R.style.DialogBase);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.animation_style); // 添加动画效果
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.alpha=0.8f;
        window.setAttributes(lp);

        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    public interface OnCenterItemClickListener {

        void OnCenterItemClick(ShareFriendDialog dialog, View view);

    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        dismiss();
//        listener.OnCenterItemClick(this, view);

        switch (view.getId()) {
            case R.id.tv_share_wechat:
                if (UmengShareUtils.isInstallApp(context, UmengShareUtils.WEIXIN_PKG)) {
                    new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN)
                            .withText("")
                            .withMedia(UmengShareUtils.shareMessage(context))
                            .setCallback(umShareListener)
                            .share();
                } else {
                    ToastUtils.showShortToast("没有安装微信客户端");
                }
                break;
            case R.id.tv_share_wechat_z:
                if (UmengShareUtils.isInstallApp(context, UmengShareUtils.WEIXIN_PKG)) {
                    new ShareAction(context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(UmengShareUtils.shareMessage(context))
                            .setCallback(umShareListener)
                            .share();
                } else {
                    ToastUtils.showShortToast("没有安装微信客户端");
                }
                break;
            case R.id.tv_share_qq:
                if (UmengShareUtils.isInstallApp(context, UmengShareUtils.QQ_PKG)) {
                    new ShareAction(context).setPlatform(SHARE_MEDIA.QQ)
                            .withMedia(UmengShareUtils.shareMessage(context))
                            .setCallback(umShareListener)
                            .share();
                } else {
                    ToastUtils.showShortToast("没有安装QQ客户端");
                }
                break;
            case R.id.tv_share_qq_z:
                if (UmengShareUtils.isInstallApp(context, UmengShareUtils.QQ_PKG)) {
                    new ShareAction(context).setPlatform(SHARE_MEDIA.QZONE)
                            .withMedia(UmengShareUtils.shareMessage(context))
                            .setCallback(umShareListener)
                            .share();
                } else {
                    ToastUtils.showShortToast("没有安装QQ客户端");
                }
                break;
            case R.id.tv_share_weibo:
                if (UmengShareUtils.isInstallApp(context, UmengShareUtils.SINA_PKG)) {
                    new ShareAction(context).setPlatform(SHARE_MEDIA.SINA)
                            .withMedia(UmengShareUtils.shareMessage(context))
                            .setCallback(umShareListener)
                            .share();
                } else {
                    ToastUtils.showShortToast("没有安装新浪微博客户端");
                }
                break;
            case R.id.bt_share_cancle:
                if (!this.isShowing())
                    dismiss();
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShortToast("分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShortToast("分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShortToast("分享取消了");
        }
    };

}

