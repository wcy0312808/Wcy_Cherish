package wcy.godinsec.wcy_dandan.network.rxdownload;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpService {

    @Streaming
    @GET
    Observable<ResponseBody> downloadRange(@Header("Range") String range, @Url String url);
}

