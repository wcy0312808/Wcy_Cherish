package wcy.godinsec.wcy_dandan.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Auther：杨玉安 on 2018/3/21 15:25
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class SqliteUtils {
    public static void closeCloseable(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
