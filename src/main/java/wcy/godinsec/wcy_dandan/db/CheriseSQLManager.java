package wcy.godinsec.wcy_dandan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import wcy.godinsec.wcy_dandan.BuildConfig;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/3/21 15:02
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CheriseSQLManager {
    private SQLiteDatabase mSQLitedb;
    private CheriseDbHelper mDataBaseHelper;
    private Context context = WcyApplication.getInstance();

    private static final class SingleFactory {
        private static final CheriseSQLManager instance = new CheriseSQLManager();
    }

    public static CheriseSQLManager getInstance() {
        return SingleFactory.instance;
    }

    public final SQLiteDatabase sqliteDB() {
        if (mSQLitedb == null) {
            if (mDataBaseHelper == null) {
                mDataBaseHelper = new CheriseDbHelper(context, BuildConfig.VERSION_CODE);
            }
            mSQLitedb = mDataBaseHelper.getWritableDatabase();
        }
        return mSQLitedb;
    }

    public void insertUserInfo(String userName) {
        Cursor cursor = sqliteDB().query(Constance.TABLE_USER_INFO, null, Constance.USER_NAME + " = ? ", new String[]{userName}, null, null, null);
//        if (cursor != null && cursor.getCount() > 0) {
//
//        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constance.USER_NAME, userName);
            long l = sqliteDB().insertOrThrow(Constance.TABLE_USER_INFO, null, contentValues);
            if (l != 0) {
                LogUtils.e("插入 " + l + " 条数据");
//            }
        }
    }


}
