package com.muzi.httpandhttps.utils;

import com.muzi.httpandhttps.enums.Method;

import java.util.Map;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class HttpUtils {

    public static void get(String address, Map<String, String> params, boolean isHttps) {
        try {
            Request.request(Method.GET, address, params, isHttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post(String address, Map<String, String> params, boolean isHttps) {
        try {
            Request.request(Method.POST, address, params, isHttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
