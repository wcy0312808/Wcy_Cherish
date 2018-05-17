package wcy.godinsec.wcy_dandan.utils;

import android.util.ArrayMap;

import wcy.godinsec.wcy_dandan.BuildConfig;

/**
 * Created by Administrator on 2017/4/25.
 */

public class FirstHintUtil {
    static String WX_HOOK_HINT_FIRST = "wxHookHintFirst";
    static String LAUNCHER_CHATTING_UI = "launcher_chatting_ui";
    static String CONTACT_INFO_UI = "contact_info_ui";
    static String LAUNCHER_CONTACT_UI = "launcher_contact_ui";
    static String LAUNCHER_ADDRESS_LIST = "launcher_address_list";
    private ArrayMap<String, Boolean> firstMap = new ArrayMap<>(4);
    private static FirstHintUtil instance;

    private FirstHintUtil() {
        firstMap.put(LAUNCHER_CHATTING_UI, true);
        firstMap.put(CONTACT_INFO_UI, true);
        firstMap.put(LAUNCHER_CONTACT_UI, true);
        firstMap.put(LAUNCHER_ADDRESS_LIST, true);
    }

    public synchronized static FirstHintUtil getInstance() {
        if (instance == null)
            instance = new FirstHintUtil();
        return instance;
    }

    /**
     * This method indicate whether the guide page is first show.
     * the first show return true,otherwise return false.
     * default value is true
     *
     * @param xmlName
     * @param key
     * @return
     */
    public boolean isFirstIn(String xmlName, String key) {
       return false;
//        return firstMap.get(key) && SharedPreferencesUtil.getBooleanValue(xmlName, key, true);
    }

    /**
     * save the state when the first show of guide page is over
     *
     * @param xmlName
     * @param key
     */
    public void saveFirstInState(String xmlName, String key) {
        firstMap.put(key, false);
//        SharedPreferencesUtil.setValue(xmlName, key, false);
    }
}
