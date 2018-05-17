package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;

import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import wcy.godinsec.wcy_dandan.R;

/**
 * Created by YJ on 2017/3/11.
 */
public class UmengShareUtils {
    public static final String QQ_PKG="com.tencent.mobileqq";
    public static final String WEIXIN_PKG="com.tencent.mm";
    public static final String SINA_PKG="com.sina.weibo";

    public static boolean isInstallApp(Context context,String pkg){
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(pkg)) {
                    return true;
                }
            }
        }

        return false;
    }
    public static UMImage getThumb(Context mContext, String imgUrl){
        UMImage image = new UMImage(mContext, imgUrl);//网络图片
        //        UMImage image = new UMImage(ShareActivity.this, file);//本地文件
//        UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
//        UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
//        UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流
        UMImage thumb =  new UMImage(mContext, imgUrl);
        image.setThumb(thumb);
        return image;
    }
    public static UMWeb shareWeb(Context mContext, String shareUrl, String title, String imgUrl, String decs){
        UMWeb web = new UMWeb(shareUrl);
        if(!TextUtils.isEmpty(imgUrl)){
            UMImage image=getThumb(mContext,imgUrl);
            web.setThumb(image);  //缩略图
        }else{
            UMImage umImage=new UMImage(mContext, R.mipmap.ic_launcher);
            web.setThumb(umImage);
        }
        web.setTitle(title);//标题
        decs=decs.replace("<p>","");
        decs=decs.replace("</p>","");
        if(decs.length()>140){
            decs=decs.substring(0,130);
            decs=decs+"...";
        }
        web.setDescription(decs);//描述
        return web;
    }

    public static UMVideo shareVideo(Context context, String videoUrl, String imgUrl, String title, String decs){
        UMVideo video = new UMVideo(videoUrl);
        video.setTitle(title);//视频的标题
        video.setThumb(getThumb(context,imgUrl));//视频的缩略图
        video.setDescription(decs);//视频的描述
        return video;
    }

    public static UMWeb shareMessage(Context context) {
        UMWeb web = new UMWeb("http://wemiyao.com/share/download");
        web.setTitle("祝你一生幸福啊");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.umeng_push_notification_default_large_icon);//这里替换一张自己工程里的图片资源
        UMImage thumb = new UMImage(context, UmengShareUtils.changeColor(bitmap));//bitmap文件
        web.setThumb(thumb);
        web.setDescription("放弃一个喜欢很久的姑娘是什么感觉");
        return web;
    }






    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }




    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite(green, alpha), getSingleMixtureWhite(blue, alpha));
    }



    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }

}
