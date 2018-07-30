package wcy.godinsec.wcy_dandan.utils;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Auther：杨玉安 on 2018/3/1 17:01
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class ImageUtils {
    /**
     * bitmap的二次采样
     * 简单原理就是由于在Android中图片都是以Bitmap的形式存在的，
     * 但是Bitmap在系统中是很好内存的，而且回收机制也需要我们自己去释放，如果处理不好的特别容易出现oom
     * 而二次采样针对的其实是图片的分别率做处理的。
     * 第一次采样  计算出 将要加载图片的ImageView和实际图片的宽高比。
     * 第二次采样  利用上面的压缩比计算出需要加载的宽高比传递给BitmapFactory，这样取图片的是偶就不会将整张图加载进来了，而是一张压缩图。提高了加载速率，也节省了内存
     *
     * @param imgPath      //图片的路径
     * @param imageMaxSize //加载图片的ImageView的最大值（宽度 或者是 高度 取最大值）
     * @return
     */
    public  static Bitmap readBitmap(String imgPath, int imageMaxSize) {
        try {
            /* 第一次采样 start*/
            BitmapFactory.Options options = new BitmapFactory.Options();   //对Bitmap 进行解码时使用的一个配置参数类
            options.inJustDecodeBounds = true;//可以简单的理解为开启了获取基本信息的权限，不去真的解析图片，只是获取图片包含宽高等最基本的信息，不会加载图片具体的像素点
            float imageHight = options.outHeight;//图片的实际高度
            float imageWidth = options.outWidth;//图片的实际宽度


            BitmapFactory.decodeFile(imgPath, options);
            int sampleSize = 1;              //需要采样的比例
            if (imageHight > imageMaxSize || imageWidth > imageMaxSize) {
                sampleSize = (int) Math.pow(2, (int) Math.round(Math.log(imageMaxSize / (double) Math.max(imageHight, imageWidth)) / Math.log(0.5)));
            }
             /* 第一次采样 end*/

            options.inJustDecodeBounds = false;         //下次加载就必须将整个bitmap的都加在出来了。
            options.inSampleSize = sampleSize;           //设置采样率
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;


            return BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }

    /**
     * @param imgPath
     * @return
     */
    public static Bitmap imgToBase64(String imgPath) {
        Bitmap bitmap = null;

        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath,600);
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
//            byte[] imgBytes = out.toByteArray();
//            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * @param base64Data
     * @param imgName
     * @param imgFormat  图片格式
     */
    public static void base64ToBitmap(String base64Data, String imgName, String imgFormat) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        File myCaptureFile = new File("/sdcard/", imgName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myCaptureFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean isTu = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if (isTu) {
            // fos.notifyAll();
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}
