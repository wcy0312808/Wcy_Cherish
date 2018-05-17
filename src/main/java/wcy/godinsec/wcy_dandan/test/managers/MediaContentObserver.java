package wcy.godinsec.wcy_dandan.test.managers;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import wcy.godinsec.wcy_dandan.application.WcyApplication;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/3/1 17:18
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class MediaContentObserver extends ContentObserver {
    private Uri mContentUri;

    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap"
    };
    /**
     * 读取媒体数据库时需要读取的列
     */
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MediaContentObserver(Uri contentUri, Handler handler) {
        super(handler);
        mContentUri = contentUri;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        LogUtils.e(mContentUri.toString());
        handleMediaContentChange(mContentUri);
    }


    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            // 数据改变时查询数据库中最后加入的一条数据
            cursor = WcyApplication.getInstance().getContentResolver().query(
                    contentUri,
                    MEDIA_PROJECTIONS,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1"
            );
            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }
            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            // 获取行数据
            String data = cursor.getString(dataIndex);
            long dateTaken = cursor.getLong(dateTakenIndex);
            // 处理获取到的第一行数据
            handleMediaRowData(data, dateTaken);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    /**
     * 处理监听到的资源
     */
    private void handleMediaRowData(String data, long dateTaken) {
        if (checkScreenShot(data, dateTaken)) {
            LogUtils.e(data + " " + dateTaken);
        } else {
            LogUtils.e("Not screenshot event");
        }
    }


    /**
     * 判断是否是截屏
     */
    private boolean checkScreenShot(String data, long dateTaken) {
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }
}
