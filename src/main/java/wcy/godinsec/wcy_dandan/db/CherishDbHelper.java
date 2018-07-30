package wcy.godinsec.wcy_dandan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wcy.godinsec.wcy_dandan.application.Constance;

/**
 * Auther：杨玉安 on 2018/6/6 10:32
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CherishDbHelper extends SQLiteOpenHelper {
    //必要的构造参数，利用父类方法将库创建出来，传递的参数包括库的名称  当前数据库的版本号
    public CherishDbHelper(Context context, int version) {
        super(context, CherishConstant.DATABASE_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CherishConstant.SQL_CREATE_TABLE_USER_INFO);//创建用户信息表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //静态内部类和非静态内部类都是在调用的时候才会加载，但是静态内部类并不会传入引用，所以效率更高一些
    public static class CherishConstant {
        public static final String DATABASE_NAME = "CHERISH.db";
        public static final String TABLE_DOWNLOAD_APP = "_downlaod_app";
        public static final String TABLE_USER_INFO = "_user_info";


        //user_table
        public static final String USER_ID = "user_id";
        public static final String USER_NAME = "name";
        public static final String USER_PASSWORD = "password";
        public static final String SQL_CREATE_TABLE_USER_INFO =
                "CREATE TABLE " + TABLE_USER_INFO + " ( "
                        + USER_ID + "INTEGER PRIMARY KEY AUTOINCREMENT ,"
                        + USER_NAME + "TEXT ,"
                        + USER_PASSWORD + " TEXT "
                        + ")";
    }
}
