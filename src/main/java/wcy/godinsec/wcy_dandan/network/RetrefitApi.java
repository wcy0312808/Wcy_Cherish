package wcy.godinsec.wcy_dandan.network;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import wcy.godinsec.wcy_dandan.network.requestbean.DownLoadEntityRequest;
import wcy.godinsec.wcy_dandan.network.responsebean.BaseResponse;
import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;

/**
 * Auther：杨玉安 on 2017/7/7 12:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface RetrefitApi {
    /*多文件上传*/
    @Multipart
    @POST("/files")
    Observable<ResponseBody> uploadFiles(@PartMap Map<String, RequestBody> params);



    /*断点续传下载接口*/
    @Streaming   //Streaming 是判断当前文件是否需要写入内存，一般都默认写入内存。
    @GET        //URL为下载路径，可以动态传递，@header（RANGE）,这个就是指定下载位置的判断，也就是断点续传的位置
    Observable<ResponseBody> download(@Header("RANGE") String start, @Url String url);


    @POST("promotion")
    Observable<BaseResponse<ArrayList<DownLoadEntityResponse>>> getGameList(@Body DownLoadEntityRequest request);

}
