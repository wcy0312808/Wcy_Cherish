package wcy.godinsec.wcy_dandan.views.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import wcy.godinsec.wcy_dandan.db.CheriseDbHelper;

/**
 * Auther：杨玉安 on 2017/10/31 17:16
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： provider 在Android 启动的时候就会调用
 */
public class WcyUserInfoProvider extends ContentProvider {
    private CheriseDbHelper mWcySQLiteHelper;
    private static final String AUTHORITY = "com.zlz.androidpro.PersonContentProvider";

    //uri匹配器，验证uri符合哪种uri形式，如果没一个匹配返回UriMatcher.NO_MATCH 即-1
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //   content://com.zlz.androidpro.PersonContentProvider/customers 匹配1
        matcher.addURI(AUTHORITY, "customers", 1);
        //   content://com.zlz.androidpro.PersonContentProvider/customers/任意数  匹配2
        matcher.addURI(AUTHORITY, "customers/#", 2);
    }

    /**
     * 是一个回调函数，在ContentProvider创建的时候，就会运行,第二个参数为指定数据库名称，如果不指定，就会找不到数据库；
     * 如果数据库存在的情况下是不会再创建一个数据库的。（当然首次调用 在这里也不会生成数据库必须调用SQLiteDatabase的 getWritableDatabase,getReadableDatabase两个方法中的一个才会创建数据库）
     */
    @Override
    public boolean onCreate() {
        //这里会调用一个SQLite的构造方法，创建一个数据库的帮助类
        mWcySQLiteHelper = new CheriseDbHelper(this.getContext(), 4);
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

    /**
     * 当执行这个方法的时候，如果没有数据库，他会创建，同时也会创建表，但是如果没有表，下面在执行insert的时候就会出错
     * 这里的插入数据也完全可以用sql语句书写，然后调用 db.execSQL(sql)执行。
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //获得一个可写的数据库引用，如果数据库不存在，则根据onCreate的方法里创建；
        SQLiteDatabase db = mWcySQLiteHelper.getWritableDatabase();
        //利用matcher匹配Uri，判断是那个Uri操作了数据库
        int res = matcher.match(uri);

        if (res == 1) {
            //通知 观察者有人修改库了
            this.getContext().getContentResolver().notifyChange(uri, null);
            //需要将新插入的id附加在uri后，直接返回uri
            return ContentUris.withAppendedId(uri,0);
        }

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
