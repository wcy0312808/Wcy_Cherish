package wcy.godinsec.wcy_dandan.proxy.proxy_consignor;

import wcy.godinsec.wcy_dandan.proxy.proxy_interfaces.Proxy_IHose;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/8/25 10:30
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class Hose_Consignor implements Proxy_IHose {

    @Override
    public void getHouseInfo(String info) {
           LogUtils.e(info);
    }

    @Override
    public void signContract(String sign) {
        LogUtils.e(sign);
    }

    @Override
    public void payFees(String price) {
        LogUtils.e(price);
    }
}
