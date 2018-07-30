package wcy.godinsec.wcy_dandan.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

/**
 * Auther：杨玉安 on 2018/5/29 16:42
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 所有文件路径的获取方法类
 */
public class DocumentPathUtils {
    public static String getDocumentPath(Context context, Uri uri) {
        boolean aboveKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;        //Android 4.4以上获取图片路径的方法改动了

        LogUtils.e("getAuthority " + uri.getAuthority());        //Authority 是表示Contentprovider 的字符串。所有的内容Uri都是以这个字符串开始的。
        if (aboveKitKat && DocumentsContract.isDocumentUri(context, uri)) {                  //如果是Documents类型的Uri，则通过document id来进行处理

            String documentId = DocumentsContract.getDocumentId(uri);
            LogUtils.e("documentId " + documentId);

            String[] split = documentId.split(":");       //通过uri获取Document的id,形式类似于:image:19683  包含类型和真正的id
            String type = split[0];
            String document_id = split[1];

            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {    //是外部储存（storge/emulated/0）的文件 外部储存对应的应该就是内部储存了吧

                if ("primary".equalsIgnoreCase(type))
                    return Environment.getExternalStorageDirectory() + "/" + document_id;
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) { //是否是下载的文件

                //ContentUris是一个用来针对于被Content修饰的字符串的工具类，withAppendedId，返回一个long类型的id
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) { //是否是媒体的文件

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                LogUtils.e("contentUri = ="+contentUri);
                String selection = "_id = ?";
                String[] selectionArgs = new String[]{document_id};
                return getDataColumn(context, contentUri, selection, selectionArgs); //在Ccontent://media/external/xxx/media 中 以 _id 为条件限制查询
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};    //"_data"
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);    //利用内容联系者 获取匹配的数据游标
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    int column = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);     //从零开始返回指定名称，如果不存在将抛出异常
                    return cursor.getString(column);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
