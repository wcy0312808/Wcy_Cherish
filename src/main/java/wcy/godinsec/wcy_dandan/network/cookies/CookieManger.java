package wcy.godinsec.wcy_dandan.network.cookies;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import wcy.godinsec.wcy_dandan.network.interceptor.PersistentCookieStore;

/**
 * Auther：杨玉安 on 2018/1/31 17:41
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CookieManger implements CookieJar {


    public static String APP_PLATFORM = "app-platform";


    private static Context mContext;

    private static PersistentCookieStore cookieStore;

    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }

    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

    static class Customer {

        private String userID;
        private String token;

        public Customer(String userID, String token) {
            this.userID = userID;
            this.token = token;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}


