package wcy.godinsec.wcy_dandan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Auther：杨玉安 on 2018/2/28 16:51
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class ImageDispose {

    /**
     * 将图片内容解析成字节数组
     * @param
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
    /**
     * 将字节数组转换为ImageView可调用的Bitmap对象
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }
    /**
     *  图片缩放
     * @param
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 质量压缩
     *
     */
    public static Bitmap compressBmp(Bitmap bmp,int setSize){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > setSize) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            LogUtils.e(  "压缩大小-:----------->>>>>>>>>>>>>>>>>>>>>>>>>>"+baos.toByteArray().length / 1024 );
            LogUtils.e(  "要求大小-:----------->>>>>>>>>>>>>>>>>>>>>>>>>>"+setSize );
            LogUtils.e(  "options-:----------->>>>>>>>>>>>>>>>>>>>>>>>>>"+options );
        }
        return bmp;
    }


    public static Bitmap compressBitmap(Bitmap bitmap,int size){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
        float zoom = (float)Math.sqrt(size * 1024 / (float)out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        while(out.toByteArray().length > size * 1024){
            LogUtils.e( "aout.toByteArray().length ----------->>>>>>>>>>>>: "+out.toByteArray().length );
            LogUtils.e(  "asize * 1024 ------------------------>>>>>>>>>>>>: "+size * 1024 );
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);

        }
        return result;
    }

}
