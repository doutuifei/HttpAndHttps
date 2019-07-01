package com.muzi.httpandhttps.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: lipeng
 * 时间: 2019/7/1
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class AccountUtils {

    public static String URL = "https://mapi.ekwing.com/wisestu/user/login";

    public static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("v", "1.4");
        params.put("osv", "7.0");
        params.put("deviceToken", "HNB4T18417001159");
        params.put("driverType", "bzk-w00");
        params.put("os", "Android");
        params.put("driverCode", "2.1.0");
        params.put("username", "10278315");
        params.put("password", MD5Utils.getMD5Encode("a666666"));
        return params;
    }

}
