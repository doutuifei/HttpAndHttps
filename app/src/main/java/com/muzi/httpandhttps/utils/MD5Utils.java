package com.muzi.httpandhttps.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者: lipeng
 * 时间: 2019/7/1
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class MD5Utils {

    public static String getMD5Encode(String text) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] result = digester.digest(text.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int number = b & 0xff;// 加盐
                String hexStr = Integer.toHexString(number);
                if (hexStr.length() == 1) {
                    sb.append("0");
                }
                sb.append(hexStr);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
