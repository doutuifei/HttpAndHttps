package com.muzi.httpandhttps;

import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.HttpUtils;

public class Http {

    public static void main(String[] args) {
        HttpUtils.get(AccountUtils.URL, AccountUtils.getParams(), false);
    }

}
