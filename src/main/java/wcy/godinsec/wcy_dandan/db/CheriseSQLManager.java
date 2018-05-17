package wcy.godinsec.wcy_dandan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wcy.godinsec.wcy_dandan.BuildConfig;
import wcy.godinsec.wcy_dandan.application.WcyApplication;

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

    public static CheriseSQLManager getInstance(){
        return SingleFactory.instance;
    }

    public final SQLiteDatabase sqliteDB() {
        if (mSQLitedb == null) {
            if (mDataBaseHelper == null) {
                mDataBaseHelper = new CheriseDbHelper(context, BuildConfig.VERSION_CODE);
            }
            if (mSQLitedb == null) {
                mSQLitedb = mDataBaseHelper.getWritableDatabase();
            }
        }
        return mSQLitedb;
    }







}
