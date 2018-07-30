package wcy.godinsec.wcy_dandan.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.bean.EventBean;
import wcy.godinsec.wcy_dandan.interfaces.OnSelectAllListener;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.StatusBarUtil;
import wcy.godinsec.wcy_dandan.views.adapter.CommonRecyclerViewAdapter;
import wcy.godinsec.wcy_dandan.views.adapter.CommonViewHolder;
import wcy.godinsec.wcy_dandan.views.dialog.ShareFriendDialog;

/**
 * Auther：杨玉安 on 2017/7/31 15:22
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 关于我们的页面
 */
public class AboutProjectActivity extends BaseActivity implements OnSelectAllListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolBar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.about_title)
    AppBarLayout mAboutTitle;
    @BindView(R.id.nav_home_image)
    KenBurnsView mNavHomeImage;
    @BindView(R.id.FAB_about_share)
    FloatingActionButton mFABAboutShare;
    @BindView(R.id.listview)
    RecyclerView mListview;
    @BindView(R.id.text)
    CheckBox mText;
    private CommonRecyclerViewAdapter mAdapter;
    private ShareFriendDialog mShareFriendDialog;
    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    protected int setContentlayout() {
        return R.layout.activity_aboutproject_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, 0, mToolbar);
//        mCollapsingToolBar.setContentScrimColor(Color.RED);

        for (int i = 0; i < 50; i++) {
            mArrayList.add("I am this " + i + " content");
        }

        mAdapter = new CommonRecyclerViewAdapter<String>(this, R.layout.item_task, mArrayList, this) {
            @Override
            public void conver(CommonViewHolder viewHodler, String data) {
                viewHodler.setText(R.id.tv_app_size, data);
                viewHodler.setCheckBox(R.id.checkbox);
            }
        };

        mListview.setLayoutManager(new LinearLayoutManager(this));
        mListview.setAdapter(mAdapter);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mText.isChecked()) {
                    mAdapter.allSelect(true);
                } else {
                    mAdapter.allSelect(false);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
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
    protected void initialize() {
        super.initialize();
        EventBus.getDefault().post(new EventBean<String>(0000));
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

    @Override
    public void onResume() {
        super.onResume();
        mNavHomeImage.resume();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(3000, new LinearInterpolator());
        mNavHomeImage.setTransitionGenerator(generator);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNavHomeImage.pause();
    }

    @Override
    public void changeState(Boolean isChange) {
        mText.setChecked(isChange);
    }
}
