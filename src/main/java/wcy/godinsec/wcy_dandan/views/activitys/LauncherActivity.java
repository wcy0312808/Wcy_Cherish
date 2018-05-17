package wcy.godinsec.wcy_dandan.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.zaaach.citypicker.CityPickerActivity;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.bean.EventBean;
import wcy.godinsec.wcy_dandan.interfaces.OnPerfectClickListener;
import wcy.godinsec.wcy_dandan.utils.AppActivityUtils;
import wcy.godinsec.wcy_dandan.utils.AppUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.StateBarTranslucentUtils;
import wcy.godinsec.wcy_dandan.utils.StatusBarUtil;
import wcy.godinsec.wcy_dandan.views.fragments.AtlasFragment;
import wcy.godinsec.wcy_dandan.views.fragments.AudioFragment;
import wcy.godinsec.wcy_dandan.views.fragments.HomeFragment;
import wcy.godinsec.wcy_dandan.views.fragments.RecreationFragment;

/**
 * Auther：杨玉安 on 2017/7/12 11:34
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class LauncherActivity extends BaseActivity {
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.bottom_navigation_bar)
    AHBottomNavigation mBottomNavigationBar;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    // 保存用户按返回键的时间
    private long mExitTime = 0;
    private CircleImageView mCircleImageView;
    private int REQUEST_CODE_PICK_CITY = 0x11;

//    private ActionBarDrawerToggle mToggle;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int setContentlayout() {
        return R.layout.activity_launcher_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mToggle.syncState();
//        mDrawerLayout.setDrawerListener(mToggle);

        StatusBarUtil.setTranslucentForDrawerLayout(this,mDrawerLayout);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, mDrawerLayout, 0);      //将DrawLayout抽出后状态栏显示为透明色并去掉阴影部分
        mMyFragmentManager.onSwitchFragment(HomeFragment.class);


        View headerView = mNavView.getHeaderView(0);//获取头部控件
        headerView.findViewById(R.id.ll_personal_center).setOnClickListener(mListener);
        headerView.findViewById(R.id.ll_project_introduction).setOnClickListener(mListener);
        headerView.findViewById(R.id.ll_personal_invite).setOnClickListener(mListener);
        headerView.findViewById(R.id.ll_personal_service).setOnClickListener(mListener);
        headerView.findViewById(R.id.ll_personal_exit).setOnClickListener(mListener);
        headerView.findViewById(R.id.ll_personal_transfiguration).setOnClickListener(mListener);
        mCircleImageView = (CircleImageView) headerView.findViewById(R.id.image_header);      //获取头像控件
        mCircleImageView.setImageResource(R.mipmap.icon_nav_header);                         //设置头像
        mCircleImageView.setOnClickListener(mListener);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getResources().getString(R.string.tab_1), R.mipmap.ic_bottom_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getResources().getString(R.string.tab_2), R.mipmap.ic_bottom_music);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getResources().getString(R.string.tab_3), R.mipmap.ic_bottom_atlas);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getResources().getString(R.string.tab_4), R.mipmap.ic_bottom_scan);
        mBottomNavigationBar.addItem(item1);
        mBottomNavigationBar.addItem(item2);
        mBottomNavigationBar.addItem(item3);
        mBottomNavigationBar.addItem(item4);


        mBottomNavigationBar.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.bg_bottom_navigation));  // 默认背景颜色
        mBottomNavigationBar.setAccentColor(ContextCompat.getColor(this, R.color.orange));                             // 切换时颜色的转变
        mBottomNavigationBar.setInactiveColor(ContextCompat.getColor(this, R.color.brown));
        mBottomNavigationBar.setForceTint(true);                                                                        // 强制着色
        mBottomNavigationBar.setForceTitlesDisplay(true);                                                               // 强制展示标题
        mBottomNavigationBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
//                        mToolbar.setVisibility(View.GONE);
                        mMyFragmentManager.onSwitchFragment(HomeFragment.class);
                        return true;
                    case 1:
//                        mToolbar.setVisibility(View.VISIBLE);
                        mMyFragmentManager.onSwitchFragment(AudioFragment.class);
                        return true;
                    case 2:
//                        mToolbar.setVisibility(View.VISIBLE);
                        mMyFragmentManager.onSwitchFragment(AtlasFragment.class);
                        return true;
                    case 3:
//                        mToolbar.setVisibility(View.VISIBLE);
                        mMyFragmentManager.onSwitchFragment(RecreationFragment.class);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    protected void initDatas() {
        super.initDatas();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(LauncherActivity.this, "再按一次退出程序哦", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }


    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Toast.makeText(LauncherActivity.this, "您当前的定位城市是 " + city, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onEventBusMain(EventBean event) {
        super.onEventBusMain(event);
        LogUtils.e("===+onEventBusMain===");
    }

    private OnPerfectClickListener mListener = new OnPerfectClickListener() {
        @Override
        protected void onNoDoubleClick(final View v) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()) {
                        case R.id.ll_personal_center:
                            startActivity(new Intent(LauncherActivity.this, AboutProjectActivity.class));
                            Toast.makeText(LauncherActivity.this, getResources().getString(R.string.personalcenter), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.ll_project_introduction:
                            Toast.makeText(LauncherActivity.this, getResources().getString(R.string.projectintroduction), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.ll_personal_service:
                            Toast.makeText(LauncherActivity.this, getResources().getString(R.string.service), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.ll_personal_invite:
                            Toast.makeText(LauncherActivity.this, getResources().getString(R.string.invite), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.ll_personal_transfiguration:
                            //模拟网络推送消息的过程，其实这个应该放在一个service里面悄悄运行的，就不演示了
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.transfiguration(Constance.ACTIVITY_ALIAS_2, WcyApplication.getInstance());
                                }
                            }, 5000);
                            Toast.makeText(LauncherActivity.this, getResources().getString(R.string.transfiguration), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.ll_personal_exit:
                            AppActivityUtils.getAppActivityUtils().AppExit(LauncherActivity.this);
                            break;
                    }
                }
            }, 260);
        }
    };
}
