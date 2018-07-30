package wcy.godinsec.wcy_dandan.broadcastreceiver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import wcy.godinsec.wcy_dandan.application.Constance;

/**
 * Auther：杨玉安 on 2018/6/5 19:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CheriseProvider extends ContentProvider {
    private static final String AUTHORITY = "wcy.godinsec.cherise";//授权域名，必须唯一，且与AndroidManifest里面注册的需保持一致
    private final Uri URI_USER_INFO = Uri.parse("content://" + AUTHORITY + Constance.TABLE_USER_INFO);//用户信息表对应的的ContentProvider路径
    private static UriMatcher mUriMatcher = null;//一个Android提供的匹配器，里面维护了一个集合，主要用于匹配外部查询时对应的数据
    private static final int USER_INFO_ID = 1; //个人中心对应的id
    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//实例化匹配器
        mUriMatcher.addURI(AUTHORITY,Constance.TABLE_USER_INFO,USER_INFO_ID);//先将各项注册进去，才能在后面用到时进行匹配
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
