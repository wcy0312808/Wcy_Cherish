package wcy.godinsec.wcy_dandan.proxy.proxy_interfaces;

/**
 * Auther：杨玉安 on 2017/8/25 10:28
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface Proxy_IHose {
    void getHouseInfo(String info); //获取房子信息
    void signContract(String sign); //签订signContract合同
    void payFees(String price);      //付钱
}
