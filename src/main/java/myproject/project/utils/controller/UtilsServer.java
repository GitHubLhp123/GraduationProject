package myproject.project.utils.controller;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.net.URLEncoder;
import java.security.*;
import java.util.Base64;

public class UtilsServer {


    public static String encryptBasedDes(String data) {


        String encodeMsg = AesCipher.encrypt(data, "UTF-8");

        return encodeMsg;
    }

    /**
     * 解密
     *
     * @param cryptData
     * @return
     */
    public static String decryptBasedDes(String cryptData) {

        String decodeMsg = AesCipher.decrypt(cryptData, "UTF-8");
        System.out.println(decodeMsg);

        return decodeMsg;
    }

    public static void main(String[] args) {

        String str1 = "12014-11-15";
        // DES数据加密
//        String s1=encryptBasedDes(str);
        String s1 = encryptBasedDes(str1);

        System.out.println("加密后" + s1);

        // DES数据解密
        String s2 = decryptBasedDes(s1);

        System.err.println("解密后" + s2);
    }
}
