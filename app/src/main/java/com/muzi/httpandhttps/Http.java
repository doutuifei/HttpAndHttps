package com.muzi.httpandhttps;

import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.HttpUtils;

public class Http {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        HttpUtils.get(AccountUtils.URL, AccountUtils.getParams(), false);
    }

}
