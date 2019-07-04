package com.muzi.httpandhttps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class SocketUtils {

    public static int TIME_OUT = 30000;
    public static String cerPath = "C:\\Users\\mzyq\\Desktop\\cer\\ekwing.cer";

    /**
     * 使用自配置证书
     *
     * @param address
     * @param isHttps
     * @return
     * @throws IOException
     */
    public static Socket getSocket(String address, boolean isHttps) throws IOException {
        URL url = new URL(address);
        String host = url.getHost();
        Socket socket;
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        if (!isHttps) {
            port = 80;
            socket = new Socket(host, port);
        } else {
            File cerFile = new File(cerPath);
            FileInputStream inputStream = new FileInputStream(cerFile);
            SSLUtils.SSLParams sslSocketFactory = SSLUtils.getSslSocketFactory(inputStream);
            socket = sslSocketFactory.sSLSocketFactory.createSocket(host, port);
        }
        socket.setSoTimeout(TIME_OUT);
        return socket;
    }

}
