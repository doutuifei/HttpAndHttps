package com.muzi.httpandhttps;

import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.HttpUtils;

/**
 * 作者: lipeng
 * 时间: 2019/7/1
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class Https {

    public static void main(String[] args) {
        HttpUtils.post(AccountUtils.URL, AccountUtils.getParams(), true);
    }

}
