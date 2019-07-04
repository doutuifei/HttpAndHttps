package com.muzi.httpandhttps.utils;

import com.muzi.httpandhttps.enums.Method;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class Request {

    /**
     * 使用自配置证书
     *
     * @param method
     * @param address
     * @param params
     * @param isHttps
     */
    public static void request(Method method, String address, Map<String, String> params, boolean isHttps) throws Exception {
        Socket socket = SocketUtils.getSocket(address, isHttps);
        byte[] messageBytes = getParamsByte(method, address, params);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        outputStream.write(messageBytes);
        outputStream.flush();
        Response.response(inputStream);
        outputStream.close();
        inputStream.close();
        socket.close();
    }

    /**
     * 封装请求报文
     *
     * @param method
     * @param address
     * @param params
     * @return
     * @throws MalformedURLException
     */
    public static byte[] getParamsByte(Method method, String address, Map<String, String> params) throws MalformedURLException {
        URL url = new URL(address);

        StringBuilder builder = new StringBuilder();

        //请求行 GET relativePath HTTP/1.1
        switch (method) {
            case POST:
                builder.append("POST ");
                builder.append(url.getFile());
                break;
            case GET:
                builder.append("GET ");
                builder.append(url.getFile());
                builder.append("?").append(getEncodeParams(params));
                break;
        }
        builder.append(" HTTP/1.1");
        builder.append("\r\n");

        //请求头
        builder.append("Host: " + url.getHost());
        builder.append("\r\n");
        builder.append("Connection: Keep-Alive");
        builder.append("\r\n");
        builder.append("Accept-Encoding: gzip");
        builder.append("\r\n");
        builder.append("Content-Type: application/x-www-form-urlencoded");
        builder.append("\r\n");

        //正文
        switch (method) {
            case POST:
                String param = getEncodeParams(params);
                //head length
                builder.append("Content-Length: " + param.length());
                builder.append("\r\n");
                //空行
                builder.append("\r\n");

                //拼接参数
                builder.append(param);
                break;
            case GET:
                //空行
                builder.append("\r\n");
                break;
        }
        String string = builder.toString();
        System.out.println(string);
        return string.getBytes();
    }

    /**
     * 拼接转码参数
     *
     * @param params
     * @return
     */
    private static String getEncodeParams(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();
                String encodeKey = URLEncoder.encode(key);
                String encodeValue = URLEncoder.encode(value);
                builder.append(encodeKey).append("=").append(encodeValue).append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

}
