package wcy.godinsec.wcy_dandan.utils;

/**
 * Created by Seeker on 2016/9/9.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dandy on 2016/5/19.
 */

public class SharedPreferencesUtils {

    private static SharedPreferencesUtils instance;

    private static final String DEFAULT_NAME = "privacyLaucnher.xml";

    private String name = DEFAULT_NAME;

    public static SharedPreferencesUtils getInstance(){
        if(instance == null){
            synchronized (SharedPreferencesUtils.class){
                if(instance == null){
                    instance = new SharedPreferencesUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 保存数据,泛型方法
     * @param key，键值
     * @param value，数据
     * @param <V>
     */
    public <V> void setValue(Context mContext,String key,V value){
        Validate.notNull(mContext,"mContext");
        SharedPreferences sp = mContext.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(value instanceof String){
            editor.putString(key,(String)value);
        }else if(value instanceof Integer){
            editor.putInt(key,(Integer)value);
        }else if(value instanceof Long){
            editor.putLong(key,(Long)value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key,(Boolean)value);
        }else if(value instanceof Float){
            editor.putFloat(key,(Float)value);
        }
        editor.commit();
    }

    /**
     * 读取数据,泛型方法
     * @param key，键值
     * @param defaultValue，默认值
     * @param <V>
     * @return
     */

    public <V> V getValue(Context mContext, String key,V defaultValue){
        Validate.notNull(mContext,"mContext");
        SharedPreferences sp = mContext.getSharedPreferences(name,Context.MODE_PRIVATE);
        Object value = defaultValue;
        if(defaultValue instanceof String){
            value = sp.getString(key,(String)defaultValue);
        }else if(defaultValue instanceof Integer){
            value = sp.getInt(key,(Integer) defaultValue);
        }else if(defaultValue instanceof Long){
            value = sp.getLong(key,(Long) defaultValue);
        }else if(defaultValue instanceof Boolean){
            value = sp.getBoolean(key, (Boolean) defaultValue);
        }else if(defaultValue instanceof Float){
            value = sp.getFloat(key, (Float) defaultValue);
        }
        return (V)value;
    }

    /**
     * 清除数据
     */
    public void clearData(Context mContext){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(name,Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public static final class Keys{
        public static final String TREASURE_TIP = "TREASURE_TIP";
        public static final String TREASURE_APP_TIP = "TREASURE_APP_TIP";
        public static final String HAS_LAUNCHER = "has_launcher";
        public static final String CRASH_INFO = "crash_info";
        public static final String FIRST_START = "first_start";
        public static final String SHORTCUT_INTRODUCE = "shortcut_introduce";
        public static final String NOTIWARN_DIALOG = "notiwarn_dialog";
        public static final String FIRST_IMPORTED = "first_import";
        public static final String SHOW_ONCE_ONE_DAY = "2017-01-01";
        public static final String FIRST_BIAN_SHEN = "first_xavatarplus";
        public static final String RELOGIN_DIALOG_SHOWN = "show_no";
        public static final String CLOSE_NOTIWARN_DIALOG = "no";
        public static final String VERSION_CODE = "VERSION_CODE";
        public static final String OLD_TO_NEW = "old";
        public static final String WEIMI_INSTALLED = "WEIMI_INSTALLED";
        public static final String SHOW_INVITE_ONCE_ONE_DAY = "show_invite_once_one_day";
        public static final String CHECK_PUSH_ICON_ONE_DAY = "check_push_icon_one_day";
        public static final String PUSH_ICON_OLD_PATH = "push_icon_old_path";
        public static final String HAS_PAY_CHANNEL = "hasPayChannel";
        public static final String FIRST_SORT = "first_sort";
        public static final String FIRST_TIME_RED_WARS = "firstTimeRedWars";
    }

}
