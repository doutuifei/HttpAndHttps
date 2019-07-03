package com.muzi.httpandhttps;

import com.muzi.httpandhttps.enums.Method;
import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.Request;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class HttpsDefault {

    public static void main(String[] args) {
        try {
            Request.requestDefault(Method.POST,AccountUtils.URL, AccountUtils.getParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
