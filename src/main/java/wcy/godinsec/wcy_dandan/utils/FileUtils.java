package wcy.godinsec.wcy_dandan.utils;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadEntity;

/**
 * Auther：杨玉安 on 2017/12/15 15:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class FileUtils {

    //写到文件
    public static void createFileWithByte(byte[] bytes, String fileName) {
        // TODO Auto-generated method stub
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入文件
     * 这里主要是执行断点下载的功能，
     * 这里使用了一个RandomAccessFile类的主要功能是完成随机读取功能，
     * 可以读取文件的指定的位置
     *
     * @param responseBody    下载的时请求的实体，也就是写入的实体
     * @param mDownLoadEntity
     * @param isCancel        是否需要继续写入
     * @throws IOException
     */
    public static boolean writeCache(ResponseBody responseBody, DownLoadEntity mDownLoadEntity, boolean isCancel) {
        LogUtils.e("==========" + mDownLoadEntity.getSave_Path() + "  isCannel == " + isCancel);
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        MappedByteBuffer mappedBuffer = null;
        File file = null;
        try {
            file = new File(mDownLoadEntity.getSave_Path());

            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            long allLength;
            if (mDownLoadEntity.getDown_App_Size() == 0) {
                allLength = responseBody.contentLength();
            } else {
                allLength = mDownLoadEntity.getDown_App_Size();
            }

            mDownLoadEntity.setDown_App_Size(allLength);
            mDownLoadEntity.setDown_State(Constance.DOWN_STATE_BEING);
            DownLoadSQLManager.getInstance().updateStatusAndSizeByPkg(mDownLoadEntity);

            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();

            if (!isCancel) {
                //将文件映射到内存中 参数  读写模式  ， 当前读到的位置  ，  还剩的字节数
                mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, mDownLoadEntity.getDown_Progress(), allLength - mDownLoadEntity.getDown_Progress());
                byte[] buffer = new byte[1024 * 8];
                int len;
                int record = 0;
                while ((len = responseBody.byteStream().read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                    record += len;
                    LogUtils.e("allLength = =" + allLength + "     current = = " + record + "        len = " + len);
                }
            }
            return true;
        } catch (Exception e) {
            LogUtils.e("========" + e.toString());
//            CheriseSQLManager.getInstance().updateStatus(downloadApp);
//            downloadFailed(e.toString(), pck);
//            iscancel = true;
            return false;
        } finally {
            if (responseBody != null && responseBody.byteStream() != null) {
                try {
                    responseBody.byteStream().close();
                    if (channelOut != null) {
                        channelOut.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    mappedBuffer = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

}
