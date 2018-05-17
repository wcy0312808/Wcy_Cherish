package wcy.godinsec.wcy_dandan.proxy.proxy_consignor;

import wcy.godinsec.wcy_dandan.proxy.proxy_interfaces.Proxy_IShopPhone;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/8/24 15:44
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：当前类是一个委托者类，也就是说它具备买手机的能力，但是ta不想干，就找了一个代理来干一些他们共同都有的功能
 */
public class ShopPhone_Consignor implements Proxy_IShopPhone {
    @Override
    public void shopping(String phone) {
        LogUtils.e(phone);
    }
}
