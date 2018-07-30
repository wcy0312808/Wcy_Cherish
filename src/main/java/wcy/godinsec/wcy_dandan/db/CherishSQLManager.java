package wcy.godinsec.wcy_dandan.db;

import android.content.Context;

/**
 * Auther：杨玉安 on 2018/6/6 11:49
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CherishSQLManager {
    private Context mContext;

    private static class InstanceManager {
        private static CherishSQLManager instance = new CherishSQLManager();
    }

    private static CherishSQLManager getInstance() {
        return InstanceManager.instance;
    }
}
