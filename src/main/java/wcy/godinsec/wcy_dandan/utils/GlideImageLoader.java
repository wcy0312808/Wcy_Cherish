package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.godinsec.bannerlib.loader.ImageLoader;

import wcy.godinsec.wcy_dandan.R;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if(path==null||"".equals(path)){
            Glide.with(context)
                    .load(R.mipmap.banner_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }else{
            Glide.with(context)
                    .load((String) path)
                    .placeholder(R.mipmap.banner_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }

    }


}
