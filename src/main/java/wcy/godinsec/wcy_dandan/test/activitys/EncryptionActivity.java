package wcy.godinsec.wcy_dandan.test.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.utils.LogUtils;


/**
 * Auther：杨玉安 on 2018/5/4 11:24
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： http加密练习
 */
public class EncryptionActivity extends BaseActivity {
    @Override
    protected int setContentlayout() {
        return R.layout.activity_launcher_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
//            aes_SecretKey(); //AES加密过程

//            mac_SecretKey();//消息认证算法

//            rsa_Sign();//RSA数字签名

            rsa_SecretKey();//RSA加解密
        } catch (Exception e) {
            LogUtils.e("============" + e.getMessage());
            e.printStackTrace();
        }


    }

    private void rsa_SecretKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //生成秘钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);//最大2048 最小40 ，但最好不要低于512
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//获得到密钥对
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();//获取到公钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();//获取到私钥。


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA256AndMGF1Padding");//防止存在重放攻击风险


        //公钥加密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] publicContent = cipher.doFinal("杨玉安".getBytes());
        LogUtils.e("公钥加密过的密文 = =" + Base64.encodeToString(publicContent,Base64.DEFAULT));


        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] privateContent = cipher.doFinal(publicContent);
        LogUtils.e("私钥解密后的明文 = =" + new String(privateContent));
    }

    private void rsa_Sign() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        //生成秘钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);//最大2048 最小40 ，但最好不要低于512
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//获得到密钥对
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();//获取到公钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();//获取到私钥。

        //签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update("杨玉安发大财".getBytes());
        LogUtils.e("RSA 数字签名签名 = =" + Base64.encodeToString(signature.sign(), Base64.DEFAULT));
    }


    private void aes_SecretKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        //AES密钥长度最少是128位，推荐使用256位。
        keyGenerator.init(256);
        //成产生密钥
        SecretKey secretkey = keyGenerator.generateKey();
        //获取秘钥
        byte[] keyBytes = secretkey.getEncoded();
        //还原密钥
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        //加密Android 提供的AES加密算法API默认使用的是ECB模式，所以要显式指定加密算法为：CBC或CFB模式，可带上PKCS5Padding填充。
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeResult = cipher.doFinal("杨玉安".getBytes());
        LogUtils.e("AES堆成加密算法 = =" + Base64.encodeToString(encodeResult, Base64.DEFAULT));
    }

    private void mac_SecretKey() throws NoSuchAlgorithmException, InvalidKeyException {
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        //产生秘钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获取密钥
        byte[] key = secretKey.getEncoded();
        //还原密钥
        SecretKey secretKey1 = new SecretKeySpec(key, "HmacSHA256");
        //实例化MAC
        Mac mac = Mac.getInstance(secretKey1.getAlgorithm());
        //初始化Mac
        mac.init(secretKey1);
        //执行摘要
        byte[] hmacSHA256Bytes = mac.doFinal("杨玉安".getBytes());
        LogUtils.e("消息认证算法 = =" + Base64.encodeToString(hmacSHA256Bytes, Base64.DEFAULT));
    }
}
