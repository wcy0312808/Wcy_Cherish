package wcy.godinsec.wcy_dandan.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.godinsec.bannerlib.view.BannerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseFragment;
import wcy.godinsec.wcy_dandan.interfaces.OnSelectAllListener;
import wcy.godinsec.wcy_dandan.utils.GlideImageLoader;
import wcy.godinsec.wcy_dandan.utils.LogUtils;
import wcy.godinsec.wcy_dandan.utils.ScreenUtils;
import wcy.godinsec.wcy_dandan.views.activitys.LauncherActivity;

/**
 * Auther：杨玉安 on 2017/8/25 11:25
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class HomeFragment extends BaseFragment implements OnSelectAllListener {
    @BindView(R.id.home_banner)
    BannerView mHomeBanner;
    private ArrayList<String> mBannerUrl;

    @Override
    protected int setFragmentLayoutID() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);
        mBannerUrl = new ArrayList<>();
        mBannerUrl.add("http://img3.imgtn.bdimg.com/it/u=1548105415,3079874028&fm=27&gp=0.jpg");
        mBannerUrl.add("http://cnews.chinadaily.com.cn/img/attachement/jpg/site1/20170608/a41f726b05111aa2ece646.jpg");
        mBannerUrl.add("http://img2.imgtn.bdimg.com/it/u=2750802454,1871560484&fm=27&gp=0.jpg");

        mHomeBanner.setViewPagerIsScroll(true)
                .setUrls(mBannerUrl)
                .setParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenWidth() * 400f / 1080)))
                .setImageLoader(new GlideImageLoader())
                .showAd();

//        getLauncherActivity().addSelectListener(new OnSelectAllListener() {
//            @Override
//            public void changeState(Boolean isChange) {
//                LogUtils.e("====changeState 接口回调====" + isChange);
//            }
//        });
    }




    public LauncherActivity getLauncherActivity() {
        return (LauncherActivity) getActivity();
    }

    @Override
    public void changeState(Boolean isChange) {
        LogUtils.e("====changeState 接口回调====" + isChange);

    }
}
