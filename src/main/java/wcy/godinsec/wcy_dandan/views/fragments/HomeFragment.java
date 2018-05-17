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
import wcy.godinsec.wcy_dandan.utils.GlideImageLoader;
import wcy.godinsec.wcy_dandan.utils.ScreenUtils;

/**
 * Auther：杨玉安 on 2017/8/25 11:25
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class HomeFragment extends BaseFragment {
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
        mBannerUrl.add("http://b350.photo.store.qq.com/psb?/V10Vj6mC3fauWq/sPtyfBxtnTqfXx40sYjS5EACMl7lYEMokBQ4mssq09w!/m/dIAFp9DeCQAAnull&bo=XAHcAAAAAAAFB6U!&rf=photolist&t=5");
        mBannerUrl.add("http://b194.photo.store.qq.com/psb?/V10Vj6mC3fauWq/z03XBD6pAx1YtaWZizv2ts4R.8UXr28vT4Ip2ZTqe6I!/m/dGwapXMTLAAAnull&bo=mgDcAAAAAAAFB2I!&rf=photolist&t=5");
        mBannerUrl.add("http://b148.photo.store.qq.com/psb?/V10Vj6mC3fauWq/0J3OrpvF6TWajyN4RnOSbEEn0LSnsBxlrNeVmUWBUZw!/m/dHKzOFhtKgAAnull&bo=rADcAAAAAAAFB1Q!&rf=photolist&t=5");

        mHomeBanner.setViewPagerIsScroll(true)
                .setUrls(mBannerUrl)
                .setParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (ScreenUtils.getScreenWidth() * 350f / 1080)))
                .setImageLoader(new GlideImageLoader())
                .showAd();
    }
}
