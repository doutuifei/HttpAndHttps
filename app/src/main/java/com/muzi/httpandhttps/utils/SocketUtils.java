package com.muzi.httpandhttps.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.X509TrustManager;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class SocketUtils {

    public static int TIME_OUT = 30000;
    public static String cerPath = "C:\\Users\\mzyq\\Desktop\\cer\\error.cer";

    /**
     * 使用自配置证书
     *
     * @param address
     * @param isHttps
     * @return
     * @throws IOException
     */
    public static Socket getSocket(String address, boolean isHttps) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, KeyManagementException {
        URL url = new URL(address);
        String host = url.getHost();
        Socket socket;
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        if (!isHttps) {
            port = 80;
            socket = new Socket(host, port);
        } else {
            File cerFile = new File(cerPath);
            System.err.println("cer file->" + cerFile.exists());
            FileInputStream inputStream = new FileInputStream(cerFile);
            SSLUtils.SSLParams sslSocketFactory = SSLUtils.getSslSocketFactory(inputStream);
            socket = sslSocketFactory.sSLSocketFactory.createSocket(host, port);


//            SSLContext tls = SSLContext.getInstance("TLS");
//            //使用默认证书
//            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//            //去掉系统默认证书
//            keystore.load(null);
//            Certificate certificate =
//                    CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
//            //设置自己的证书
//            keystore.setCertificateEntry("dfsd", certificate);
//            //通过信任管理器获取一个默认的算法
//            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
//            //算法工厂创建
//            TrustManagerFactory instance = TrustManagerFactory.getInstance(algorithm);
//            instance.init(keystore);
//            tls.init(null, instance.getTrustManagers(), null);
//            SSLSocketFactory socketFactory = tls.getSocketFactory();
//            socket = socketFactory.createSocket(host, port);

        }
        socket.setSoTimeout(TIME_OUT);
        return socket;
    }

    /**
     * 使用系统默认证书
     *
     * @param address
     * @return
     * @throws IOException
     */
    public static Socket getDefaultSocket(String address) throws IOException {
        URL url = new URL(address);
        String host = url.getHost();
        Socket socket;
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        SSLUtils.SSLParams sslSocketFactory = SSLUtils.getSslSocketFactory((X509TrustManager) null);
        socket = sslSocketFactory.sSLSocketFactory.createSocket(host, port);
        socket.setSoTimeout(TIME_OUT);
        return socket;
    }

}
