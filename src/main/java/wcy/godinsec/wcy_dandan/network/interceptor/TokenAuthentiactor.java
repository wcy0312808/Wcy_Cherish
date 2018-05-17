package wcy.godinsec.wcy_dandan.network.interceptor;


import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/9/1 22:18
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class TokenAuthentiactor implements Authenticator {
    /**
     * 处理401返回码 当然用不到 你可以不写
     *
     * @param route
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        LogUtils.e("Authenticating for response : " + response);
        LogUtils.e("response.Challenges  : " + response.challenges());
        LogUtils.e("response.code  : " + response.code());

        if (response.code() == 401) {
            if (requestCount(response) >= 3) {
                //如果请求失败就直接放弃
                return null;
            } else {

            }
        }
        return response.request().newBuilder().build();
    }

    /**
     * 重复请求次数限制
     *
     * @param response
     * @return
     */
    private int requestCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
