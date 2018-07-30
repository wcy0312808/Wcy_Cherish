package wcy.godinsec.wcy_dandan.test.activitys;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.CheriseSQLManager;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/6/5 17:21
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class SQLiteExempleActivity extends BaseActivity {
    @BindView(R.id.tween_scale)
    Button mTweenScale;
    @BindView(R.id.tween_translate)
    Button mTweenTranslate;
    @BindView(R.id.tween_rotate)
    Button mTweenRotate;
    @BindView(R.id.tween_set)
    Button mTweenSet;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_sqlite_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContentResolver().registerContentObserver(Uri.parse("content://wcy.godinsec.wcy_dandan/_user_info"),true,mContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mContentObserver);
    }

    @OnClick({R.id.tween_scale, R.id.tween_translate, R.id.tween_rotate, R.id.tween_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tween_scale:
                CheriseSQLManager.getInstance().insertUserInfo("yangyuan");
                break;
            case R.id.tween_translate:
                break;
            case R.id.tween_rotate:
                break;
            case R.id.tween_set:
                break;
        }
    }

    ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.e("==========="+selfChange);
        }
    };

}
