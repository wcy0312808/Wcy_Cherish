package wcy.godinsec.wcy_dandan.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/4/16 17:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CheriseDbHelper extends SQLiteOpenHelper {
    private static CheriseDbHelper mCheriseDbHelper;

    public CheriseDbHelper(Context context, int version) {
        super(context, Constance.NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.beginTransaction();

        createUserTable(db);
        createDownLoadTable(db);

//        db.setTransactionSuccessful();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e("oldVersion  " + oldVersion + "  newVersion  " + newVersion);
        onCreate(db); //将基础的表现创建一遍
        /**
         * oldVersion 旧版本
         * newVersion 新版本
         */
        for (int i = oldVersion; i < newVersion; i++) {
            //旧版本 + 1
            switch (i + 1) {
                case 2:
                    LogUtils.e("======2======");
                    break;
                case 3:
                    LogUtils.e("======3======");
                    versionThreeUP(db);
                    break;
            }
        }
    }

    private void createUserTable(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(Constance.TABLE_USER_INFO)
                .append("(")
                .append(Constance.USER_ID + " integer primary key autoincrement, ")
                .append(Constance.USER_NAME + " TEXT, ")
                .append(Constance.USER_PASSWORD + " TEXT ")
                .append(")");
        db.execSQL(String.valueOf(sql));
    }


    //创建downloadstatus表，主要记录所有下载的状态值
    private void createDownLoadTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + Constance.TABLE_DOWNLOAD_APP
                + "("
                + Constance.APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constance.APP_NAME + " TEXT, "
                + Constance.APP_PACKAGE_NAME + " TEXT, "
                + Constance.APP_DOWN_LINK + " TEXT, "
                + Constance.APP_SAVE_PATH + " TEXT, "
                + Constance.APP_MD5 + " TEXT, "
                + Constance.APP_SORT + " INTEGER, "
                + Constance.APP_DOWN_STATUS + " INTEGER, "
                + Constance.APP_TOTALL_LENGTH + " INTEGER, "
                + Constance.APP_CURRENT_LENGTH + " INTEGER, "
                + Constance.APP_INSTALL_START_TIME + " INTEGER, "
                + Constance.APP_INSTALL_END_TIME + " INTEGER "
                + ")";
        db.execSQL(sql);
    }


    /********************************************************************************************************************************/
    private void versionThreeUP(SQLiteDatabase db) {
        String oldSql = "("
                + Constance.APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constance.APP_NAME + " TEXT, "
                + Constance.APP_INSTALL_END_TIME + " INTEGER, "
                + Constance.APP_CURRENT_LENGTH + " INTEGER "
                + ")";
        String sql;
        try {
            db.beginTransaction();
            String newTableName = Constance.TABLE_DOWNLOAD_APP + "temp"; //命名为新表，但是其实它才是老表
            sql = "ALTER TABLE " + Constance.TABLE_DOWNLOAD_APP + " RENAME TO " + newTableName; //更改成新的表名
            db.execSQL(sql); //执行
            createDownLoadTable(db); //重新创建新的表，这里已经将新的字段都添加到里面了
            sql = "insert into " + Constance.TABLE_DOWNLOAD_APP + oldSql + " select " + oldSql + " from " + newTableName;//将老表中的数据查询出来，再添加到新表中
            db.execSQL(sql);
            sql = " DROP TABLE " + newTableName; //删除替代表
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


}
