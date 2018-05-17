package wcy.godinsec.wcy_dandan.network.rxdownload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/9/30 16:57
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：OkHttp的底层流操作实际上是Okio的操作，
 * Okio也是square的，主要简化了Java IO操作 ,
 * 当前类的作用就是继承系统的响应体，来达到进度的提取
 */
public class DownLoadResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private OnDownLoadProgressListener mDownLoadProgressListener;
    private BufferedSource mBufferedSource; //类似于一个inputStream

    public DownLoadResponseBody(ResponseBody responseBody, OnDownLoadProgressListener downLoadProgressListener) {
        mResponseBody = responseBody;
        mDownLoadProgressListener = downLoadProgressListener;
    }


    /**
     * 返回响应内容的类型，比如image/jpeg
     *
     * @return
     */
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    /**
     * 返回响应内容的长度
     *
     * @return
     */
    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    /**
     * 返回一个BufferedSource.类似于inputStream
     *
     * @return
     */
    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }


    /**
     * 通过读取ResponseBydy的字节获取当前正在下载的进度
     *
     * @param source
     * @return
     */
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;  //初始化定义值
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);          //通过sink类似于outputstream读取数据
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;    //不断统计当前下载好的数据
                if (null != mDownLoadProgressListener)
                    mDownLoadProgressListener.updateDownLoadProgress(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1);  //bytextRead==-1代表读取完毕
                return bytesRead;
            }
        };
    }
}
