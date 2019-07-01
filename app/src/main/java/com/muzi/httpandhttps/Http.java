package com.muzi.httpandhttps;

import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.SocketUtils;

public class Http {

    public static void main(String[] args) {
        SocketUtils.get(AccountUtils.URL, AccountUtils.getParams(), false);
    }

}
