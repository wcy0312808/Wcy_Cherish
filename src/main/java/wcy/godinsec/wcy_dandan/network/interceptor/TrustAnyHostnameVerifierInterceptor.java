package wcy.godinsec.wcy_dandan.network.interceptor;

import android.text.TextUtils;


import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

import wcy.godinsec.wcy_dandan.utils.FileUtils;

/**
 * Auther：杨玉安 on 2018/1/19 17:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class TrustAnyHostnameVerifierInterceptor implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        Certificate[] localCertificates = new Certificate[0];

        try {
            localCertificates = session.getPeerCertificates();
            for (Certificate certificate : localCertificates) {
//                LogUtils.e("certificate = =" + certificate);
            }


        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }

        try {
            //将证书链中的第一个写到文件
            FileUtils.createFileWithByte(localCertificates[0].getEncoded(), "ca.cer");
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }


        if (TextUtils.isEmpty(hostname) || TextUtils.isEmpty(session.getPeerHost())) {
            return false;
        }

//        LogUtils.e("hostname = =" + hostname + "      peerhost = =" + session.getPeerHost());
        return hostname.equals(session.getPeerHost());
    }
}
