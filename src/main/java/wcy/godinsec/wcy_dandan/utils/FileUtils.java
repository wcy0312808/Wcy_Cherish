package wcy.godinsec.wcy_dandan.utils;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.db.DownLoadSQLManager;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadEntity;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadListener;

/**
 * Auther：杨玉安 on 2017/12/15 15:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class FileUtils {
    private static boolean isDownLoad = true;

    public static void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

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

    /**
     * RandomAccessFile 写入文件
     *
     * @param body 断点续传时，每次请求回来的body是剩余的长度
     */
    public static boolean writeRandomFile(ResponseBody body, final DownLoadEntity downLoadEntity, final DownLoadListener downLoadListener) {
        InputStream inputStream = null;
        RandomAccessFile raf = null;
        File file = null;
        try {
            file = new File(downLoadEntity.getSave_Path());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            //当前循环中读取的长度
            int nowReadLength;
            //剩余文件的总长度
            long fileSize = body.contentLength();
            //当前本地文件的大小长度，简单来说也就是已经下载的长度，当前是从数据库中查询出来的上次保存的长度
            long localCurrentLength = downLoadEntity.getDown_Progress();


            //读取字节时的缓存数组
            byte[] fileReader = new byte[1024 * 8];
            //将文件读入写入流中
            inputStream = body.byteStream();
            //利用RanddomAccessFile工具类写入文件，这个工具类可以实现断点续传，依靠下面的方法
            raf = new RandomAccessFile(downLoadEntity.getSave_Path(), "rw");
            //从文件的当前长度开始下载，是定位到文件指针在文件中的位置，是一个绝对定位，有别于skipBytes(),是在文件中跳过给定数量的字节，是相对的定位
            raf.seek(downLoadEntity.getDown_Progress());


            //将状态值改为开始下载并保存到数据库中
            downLoadEntity.setDown_State(Constance.DOWN_STATE_BEING);
            //断点续传时，每次请求回来的body是剩余的长度，所以需要再加上已经下载的长度
            downLoadEntity.setDown_App_Size(fileSize + downLoadEntity.getDown_Progress());
            DownLoadSQLManager.getInstance().updateStatusAndSizeByPkg(downLoadEntity);


            while ((nowReadLength = inputStream.read(fileReader)) != -1) {
                raf.write(fileReader, 0, nowReadLength);  //写入
                localCurrentLength += nowReadLength;       //记录写入的当前值

                //时刻更新数据库中该任务写入的进度
                downLoadEntity.setDown_Progress(localCurrentLength);
                DownLoadSQLManager.getInstance().updateProgress(downLoadEntity);


                if (downLoadListener != null) {
                   /*接受进度消息，造成UI阻塞，如果不需要显示进度可去掉实现逻辑，减少压力*/
                    final long final_CurrentFileLength = localCurrentLength;
                    Observable.just(nowReadLength)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    //如果当前状态是不可见或者是停止状态不需要回调这个接口来刷新进度条
                                    if (downLoadEntity.getDown_State() == Constance.DOWN_STATE_PAUSE || downLoadEntity.getDown_State() == Constance.DOWN_STATE_STOP)
                                        return;

                                    downLoadListener.updateProgress(final_CurrentFileLength, downLoadEntity.getDown_App_Size());
                                }
                            });
                } else {
                    //回掉接口被系统清除，说明内存已经不足或者其他情况，这里停止下载
                    return false;
                }

                LogUtils.e("fileSize = = " + fileSize + " currentFileLength = = " + localCurrentLength + "  read = " + nowReadLength);

                //暂停下载
                if (!isDownLoad) {
                    LogUtils.e("停止写入 = Down_Progress() = " + downLoadEntity.getDown_Progress());
                    return false;
                }
            }


            return true;
        } catch (Exception e) {
            LogUtils.e("Exception " + e);
            DownLoadSQLManager.getInstance().updateProgressAndStatus(downLoadEntity.getPackage_Name(), (int) downLoadEntity.getDown_Progress(), downLoadEntity.getInstall_Status());
            isDownLoad = false;
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将.temp改成.apk
     *
     * @param path
     * @return
     */
    public static String renameFile(String path) {
        File file = new File(path);
        String newPath = path.substring(0, path.length() - 4) + "apk";
        File newFile = new File(newPath);
        file.renameTo(newFile);
        return newPath;
    }

}
