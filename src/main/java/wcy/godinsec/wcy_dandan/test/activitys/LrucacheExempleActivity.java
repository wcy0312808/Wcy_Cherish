package wcy.godinsec.wcy_dandan.test.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.utils.ImageUtils;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/31 16:26
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class LrucacheExempleActivity extends BaseActivity {
    @BindView(R.id.iv_lrucache)
    ImageView mIvLrucache;

    private String dataUrl = "http://img3.imgtn.bdimg.com/it/u=1548105415,3079874028&fm=27&gp=0.jpg";
    private static  LruCache<String, Bitmap> mLruCache;
    private  Bitmap mBitmap;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_lrucache_layout;
    }

    @Override
    protected void initialize() {
        super.initialize();
        Long maxSize = Runtime.getRuntime().maxMemory();//获取到运行时内存

        mLruCache = new LruCache<String, Bitmap>((int) (maxSize / 8)) {
            //key 从内存中获取或者存入的对象的名字
            //value 存取的对象
            // 返回的对象缓存
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        //从缓存中获取，也就是从LruCache中获取图片
        mBitmap = mLruCache.get(getName(dataUrl));
        LogUtils.e(" = "+mLruCache.get(getName(dataUrl)));
        if (mBitmap != null) {
            LogUtils.e("从缓存中获取图片  "+getName(dataUrl) );
            mIvLrucache.setImageBitmap(mBitmap);
        } else {
            mBitmap = ImageUtils.readBitmap(getExternalCacheDir().getAbsolutePath() + File.separator + getName(dataUrl), 500);
            if (mBitmap != null) {
                LogUtils.e("从SD卡中获取图片，并将图片缓存到内存中 == "+getExternalCacheDir().getAbsoluteFile()+File.separator+getName(dataUrl));
                mIvLrucache.setImageBitmap(mBitmap);
                mLruCache.put(getName(dataUrl), mBitmap);
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtils.e("从网络中获取");
                            URL path = new URL(dataUrl);//将String转换成URL
                            HttpURLConnection connection = (HttpURLConnection) path.openConnection();
                            if (connection.getResponseCode() == 200) {
                                final Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                                LogUtils.e("将图片放入缓存中");
                                mLruCache.put(getName(dataUrl), bitmap);
                                LogUtils.e("将图片放入到文件中");
                                saveBitmapToSDCard(getName(dataUrl),bitmap);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mIvLrucache.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        } catch (Exception e) {

                        }
                    }
                }).start();
            }
        }
    }


    public String getName(String url) {
        String name = url.substring(url.lastIndexOf("/"), url.length());
        return name;
    }

    private void saveBitmapToSDCard(String name, Bitmap bitmap) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(new File(getExternalCacheDir(), name)));//创建一个文件输出流，写入SD卡
            if (name.endsWith("png") || name.endsWith("PNG")) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);//质量压缩,100为不压缩，直接写入内存
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
