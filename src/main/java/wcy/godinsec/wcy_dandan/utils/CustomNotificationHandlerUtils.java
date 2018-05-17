package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Auther：杨玉安 on 2017/8/15 19:41
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CustomNotificationHandlerUtils extends UmengNotificationClickHandler {

    @Override
    public void launchApp(Context context, UMessage msg) {
        super.launchApp(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "launch_app");
//        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        super.openActivity(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_activity");
//        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        super.openUrl(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_url");
//        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        super.dealWithCustomAction(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "custom_action");
//        MobclickAgent.onEvent(context, "click_notification", map);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        super.autoUpdate(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "auto_update");
//        MobclickAgent.onEvent(context, "click_notification", map);
    }
}
