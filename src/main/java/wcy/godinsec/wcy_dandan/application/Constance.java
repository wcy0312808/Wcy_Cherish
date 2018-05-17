package wcy.godinsec.wcy_dandan.application;

/**
 * Auther：杨玉安 on 2017/7/7 12:51
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：总的接口回到的父类，为以后实现多态做准备
 */
public class Constance {
    /******************************** Error - start*****************************************/
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    public static final int UNKNOWN = 1000;  //未知错误
    public static final int PARSE_ERROR = 1001; //解析错误
    public static final int NETWORD_ERROR = 1002; //网络错误
    public static final int HTTP_ERROR = 1003;  //协议出错
    public static final int SSL_ERROR = 1005;  //证书出错


    /******************************** server - start*****************************************/
    public final static String BROADCAST_COUNT_ACTION = "wcy.broadcast.countreceiver";    //计数广播的标识码
    public final static String COUNT_VALUE = "wcy.broadcast.value";


    /******************************** Handler - start*****************************************/
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int ACTIVITY_GONE = -1;     //标记异步操作返回时目标界面已经消失时的处理状态

    /******************************** 变身   - start*****************************************/
    public static final String ACTIVITY_ALIAS_1 = "wcy.godinsec.wcy_dandan.ActivityAlias1";
    public static final String ACTIVITY_ALIAS_2 = "wcy.godinsec.wcy_dandan.ActivityAlias2";

    /******************************** DBBASE  - start*****************************************/
    public static final String NAME = "CHERISE.db";
    public static final String TABLE_DOWNLOAD_APP = "_downlaod_app";
    public static final String TABLE_USER_INFO = "_user_info";

    public static final int DOWN_STATE_NOT = 0x110;                   //未下载
    public static final int DOWN_STATE_BEING = 0x111;                 //正在下载
    public static final int DOWN_STATE_PAUSE = 0x112;                 //下载暂停
    public static final int DOWN_STATE_STOP = 0x113;                  //下载停止    目前暂未用到
    public static final int DOWN_STATE_SUCCESS = 0x114;               //下载完成   正在安装   认定为同一种状态值
    public static final int DOWN_STATE_FAIL = 0x115;                  //下载错误
    public static final int DOWN_STATE_INSTALL_SUCCESS = 0x116;      //安装成功
    public static final int DOWN_STATE_INSTALL_FAIL = 0x117;         //安装失败

    //user_table
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";
    //downLoad_table
    public static final String APP_ID = "app_id";//自增长
    public static final String APP_MD5 = "app_md5";//appMd5
    public static final String APP_SORT = "app_sort";//应用排序时使用
    public static final String APP_NAME = "app_name";//应用名称
    public static final String APP_PACKAGE_NAME = "app_package_name";//app包名
    public static final String APP_DOWN_LINK = "app_down_link";//apk下载地址
    public static final String APP_SAVE_PATH = "app_save_path";//apk本地存放路径
    public static final String APP_TOTALL_LENGTH = "app_total_length";//应用总大小
    public static final String APP_CURRENT_LENGTH = "app_current_length";//apk当前下载长度，同时代表progress的长度
    public static final String APP_DOWN_STATUS = "app_down_state";           //网络下载的状态值  NOT_DOWN 未下载 1正在下载 2已下载 3暂停下载 4正在安装（没有正在卸载卸载应用的时候卸载应用同时删除数据）
    public static final String APP_INSTALL_END_TIME = "app_install_end_time";  //记录当前app的最后安装的时间
    public static final String APP_INSTALL_START_TIME = "app_install_start_time";  //记录当前app开始安装的时间


}
