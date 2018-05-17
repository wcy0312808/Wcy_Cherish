package wcy.godinsec.wcy_dandan.proxy;

import wcy.godinsec.wcy_dandan.proxy.proxy_interfaces.Proxy_IHose;

/**
 * Auther：杨玉安 on 2017/8/25 10:35
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：租房子的代理类
 */
public class Proxy_RentingHose implements Proxy_IHose {

    private Proxy_IHose mProxy_iHose;

    public Proxy_RentingHose(Proxy_IHose proxy_iHose) {
        mProxy_iHose = proxy_iHose;
    }

    @Override
    public void getHouseInfo(String info) {
        mProxy_iHose.getHouseInfo(info);
    }

    @Override
    public void signContract(String sign) {
        mProxy_iHose.getHouseInfo(sign);
    }

    @Override
    public void payFees(String price) {
        mProxy_iHose.payFees(price);
    }
}
