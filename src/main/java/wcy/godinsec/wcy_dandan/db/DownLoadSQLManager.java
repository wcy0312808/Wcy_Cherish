package wcy.godinsec.wcy_dandan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import wcy.godinsec.wcy_dandan.BuildConfig;
import wcy.godinsec.wcy_dandan.application.Constance;
import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;
import wcy.godinsec.wcy_dandan.network.rxdownload.DownLoadEntity;
import wcy.godinsec.wcy_dandan.utils.SqliteUtils;

/**
 * Auther：杨玉安 on 2018/3/22 20:41
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadSQLManager {
    private SQLiteDatabase mSQLitedb;
    private CheriseDbHelper mDataBaseHelper;
    private Context context = WcyApplication.getInstance();

    private static final class SingleFactory {
        private static final DownLoadSQLManager instance = new DownLoadSQLManager();
    }

    public static DownLoadSQLManager getInstance() {
        return DownLoadSQLManager.SingleFactory.instance;
    }

    private final SQLiteDatabase sqliteDB() {
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

    /**
     * 插入下载应用数据
     *
     * @param downLoadEntity
     */
    public void insertDownLoadEntity(DownLoadEntity downLoadEntity) {
        Cursor cursor = sqliteDB().query(Constance.TABLE_DOWNLOAD_APP, null,Constance.APP_PACKAGE_NAME + " = ?", new String[]{downLoadEntity.getPackage_Name() + ""}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            //不做处理
        } else {
            ContentValues values = new ContentValues();
            values.put(Constance.APP_NAME,downLoadEntity.getApp_Name());
            values.put(Constance.APP_MD5,"");
            values.put(Constance.APP_SORT, downLoadEntity.getApp_sort());
            values.put(Constance.APP_DOWN_LINK, downLoadEntity.getApp_Link());
            values.put(Constance.APP_PACKAGE_NAME, downLoadEntity.getPackage_Name());
            values.put(Constance.APP_TOTALL_LENGTH, downLoadEntity.getDown_App_Size());
            values.put(Constance.APP_CURRENT_LENGTH, downLoadEntity.getDown_Progress());
            values.put(Constance.APP_DOWN_STATUS, downLoadEntity.getDown_State());
            values.put(Constance.APP_INSTALL_END_TIME, 0);
            values.put(Constance.APP_INSTALL_START_TIME, downLoadEntity.getInstall_start_Time());
            sqliteDB().insertOrThrow(Constance.TABLE_DOWNLOAD_APP, null, values);
        }
        SqliteUtils.closeCloseable(cursor);
    }

    public DownLoadEntity queryDownLoadEntityByPackageName(String package_name) {
        if (TextUtils.isEmpty(package_name)) return null;
        Cursor cursor = sqliteDB().query(Constance.TABLE_DOWNLOAD_APP, null, Constance.APP_PACKAGE_NAME + " = ?", new String[]{package_name}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                DownLoadEntity downLoadEntity = new DownLoadEntity();
                downLoadEntity.setApp_Id(cursor.getInt(cursor.getColumnIndex(Constance.APP_ID)));
                downLoadEntity.setApp_Md5(cursor.getString(cursor.getColumnIndex(Constance.APP_MD5)));
                downLoadEntity.setApp_sort(cursor.getInt(cursor.getColumnIndex(Constance.APP_SORT)));
                downLoadEntity.setApp_Link(cursor.getString(cursor.getColumnIndex(Constance.APP_DOWN_LINK)));
                downLoadEntity.setApp_Name(cursor.getString(cursor.getColumnIndex(Constance.APP_NAME)));
                downLoadEntity.setSave_Path(cursor.getString(cursor.getColumnIndex(Constance.APP_SAVE_PATH)));
                downLoadEntity.setPackage_Name(cursor.getString(cursor.getColumnIndex(Constance.APP_PACKAGE_NAME)));
                downLoadEntity.setDown_App_Size(cursor.getInt(cursor.getColumnIndex(Constance.APP_TOTALL_LENGTH)));
                downLoadEntity.setDown_Progress(cursor.getInt(cursor.getColumnIndex(Constance.APP_CURRENT_LENGTH)));
                downLoadEntity.setInstall_end_Time(cursor.getInt(cursor.getColumnIndex(Constance.APP_INSTALL_END_TIME)));
                downLoadEntity.setInstall_start_Time(cursor.getInt(cursor.getColumnIndex(Constance.APP_INSTALL_START_TIME)));
                downLoadEntity.setDown_State(cursor.getInt(cursor.getColumnIndex(Constance.APP_DOWN_STATUS)));
                return downLoadEntity;
            }
        }
        return null;
    }

    /**
     * 通过包名更新下载状态
     */
    public void updateInstallStatus(String packageName,int installStatus) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_DOWN_STATUS, installStatus);
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values, Constance.APP_PACKAGE_NAME+" = ?", new String[]{packageName});
    }

    /**
     * 通过包名更新下载进度
     */
    public void updateProgress(DownLoadEntity body) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_CURRENT_LENGTH, body.getDown_Progress());
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values, Constance.APP_PACKAGE_NAME+" = ?", new String[]{body.getApp_Name()});
    }


    /**
     * 下载完成
     * 安装的时候更改路径，预防下次没办法继续下载
     * 同时通过包名更改状态值
     */
    public void updateLocalPathAndStatus(String save_path, String appName, String md5) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_DOWN_STATUS, Constance.DOWN_STATE_SUCCESS);
        values.put(Constance.APP_SAVE_PATH, save_path);
        values.put(Constance.APP_MD5, md5);
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values, Constance.APP_PACKAGE_NAME+" = ?", new String[]{appName});
    }


    /**
     * 修改下载应用的下载状态和大小
     */
    public void updateStatusAndSizeByPkg(DownLoadEntity downloadApp) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_DOWN_STATUS, String.valueOf(downloadApp.getDown_State()));
        values.put(Constance.APP_CURRENT_LENGTH, downloadApp.getDown_App_Size());
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values,  Constance.APP_PACKAGE_NAME+" = ?", new String[]{downloadApp.getApp_Name()});
    }

    /**
     * 通过包名更新下载进度
     */
    public void updateProgressAndStatus(String packageName,int progress,int downStatus) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_DOWN_STATUS,downStatus);
        values.put(Constance.APP_CURRENT_LENGTH, progress);
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values,  Constance.APP_PACKAGE_NAME+" = ?", new String[]{packageName});
    }


    /**
     * 通过包名删除表格
     */
    public void deleteDownLoadEntityInfo(String app_Name) {
        sqliteDB().delete(Constance.TABLE_DOWNLOAD_APP,  Constance.APP_PACKAGE_NAME+" = ?", new String[]{app_Name});
    }

    /**
     * 监听到安装APP后更难搞当前app状态值
     */
    public void updateInstallStatus(int status, String pkg) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_DOWN_STATUS, status);
        values.put(Constance.APP_INSTALL_END_TIME, System.currentTimeMillis() / 1000);
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values,  Constance.APP_PACKAGE_NAME+" = ?", new String[]{pkg});
    }

    /**
     * 更新应用最后使用时间
     *
     * @param pkg
     */
    public void updateLastTimeByPkg(String pkg) {
        ContentValues values = new ContentValues();
        values.put(Constance.APP_INSTALL_END_TIME, System.currentTimeMillis()/1000);
        sqliteDB().update(Constance.TABLE_DOWNLOAD_APP, values,  Constance.APP_PACKAGE_NAME+" = ?", new String[]{pkg});
    }
}
