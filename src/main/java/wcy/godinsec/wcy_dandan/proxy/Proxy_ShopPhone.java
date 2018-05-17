package wcy.godinsec.wcy_dandan.proxy;

import wcy.godinsec.wcy_dandan.proxy.proxy_interfaces.Proxy_IShopPhone;

/**
 * Auther：杨玉安 on 2017/8/24 15:40
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：本类是一个代理类，主要负责的就是购买手机
 */
public class Proxy_ShopPhone implements Proxy_IShopPhone{
    private Proxy_IShopPhone mProxy_iShopPhone;

    public Proxy_ShopPhone(Proxy_IShopPhone proxy_iShopPhone) {
        mProxy_iShopPhone = proxy_iShopPhone;
    }

    @Override
    public void shopping(String phone) {
        mProxy_iShopPhone.shopping(phone);
    }
}
