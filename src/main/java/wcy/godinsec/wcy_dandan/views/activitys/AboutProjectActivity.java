package wcy.godinsec.wcy_dandan.views.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.bean.EventBean;
import wcy.godinsec.wcy_dandan.utils.GestureDetectorUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.StatusBarUtil;
import wcy.godinsec.wcy_dandan.views.dialog.ShareFriendDialog;

/**
 * Auther：杨玉安 on 2017/7/31 15:22
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 关于我们的页面
 */
public class AboutProjectActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolBar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.about_title)
    AppBarLayout mAboutTitle;
    @BindView(R.id.nav_home_image)
    ImageView mNavHomeImage;
    @BindView(R.id.FAB_about_share)
    FloatingActionButton mFABAboutShare;
    private ShareFriendDialog mShareFriendDialog;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_aboutproject_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
    }

    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";
                break;
            }
            default:
                break;
        }
        return name;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mCollapsingToolBar.setTitle("个人中心");
        EventBus.getDefault().post(new EventBean<String>(0000));
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @OnClick(R.id.FAB_about_share)
    public void onViewClicked() {
        if (mShareFriendDialog == null)
            mShareFriendDialog = new ShareFriendDialog(this, R.layout.dialog_share_layout, new int[]{R.id.tv_share_wechat, R.id.tv_share_wechat_z, R.id.tv_share_qq, R.id.tv_share_qq_z, R.id.tv_share_weibo, R.id.bt_share_cancle});
        mShareFriendDialog.show();
    }


    /**
     * QQ 和新浪分享所要添加的回调界面
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
