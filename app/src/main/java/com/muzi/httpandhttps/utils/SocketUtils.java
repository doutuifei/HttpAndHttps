package com.muzi.httpandhttps.utils;

import com.muzi.httpandhttps.enums.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class SocketUtils {

    private static String cerPath = "C:\\Users\\mzyq\\Desktop\\ekwing.cer";

    public static void get(String address, Map<String, String> params, boolean isHttps) {
        try {
            request(Method.GET, address, params, isHttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post(String address, Map<String, String> params, boolean isHttps) {
        try {
            request(Method.POST, address, params, isHttps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void request(Method method, String address, Map<String, String> params, boolean isHttps) throws Exception {
        Socket socket = getSocket(address, isHttps);
        byte[] messageBytes = getRequstByte(method, address, params);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        outputStream.write(messageBytes);
        outputStream.flush();

        InputStreamUtils streamUtils = new InputStreamUtils();

        //请求行
        String requestLine = streamUtils.readLine(inputStream);
        System.out.println("响应行-->" + requestLine);

        //请求头
        Map<String, String> headers = streamUtils.readHeaders(inputStream);
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println("响应头-->" + entry.getKey() + ":" + entry.getValue());
        }

        //读响应体 ? 需要区分是以 Content-Length 还是以 Chunked分块编码
        if (headers.containsKey("Content-Length")) {
            int length = Integer.valueOf(headers.get("Content-Length"));
            byte[] bytes = streamUtils.readBytes(inputStream, length);
            System.out.println("响应实体-->" + new String(bytes));
        } else {
            //分块编码
            String response = streamUtils.readChunked(inputStream);
            System.out.println("响应实体-->" + response);
        }
    }

    public static Socket getSocket(String address, boolean isHttps) throws Exception {
        URL url = new URL(address);
        Socket socket;
        String host = url.getHost();
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        if (isHttps) {
            File cerFile = new File(cerPath);
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(new FileInputStream(cerFile));
            socket = sslSocketFactory.sSLSocketFactory.createSocket(host, port);
        } else {
            port = 80;
            socket = new Socket(host, port);
        }
        socket.setSoTimeout(30000);
        return socket;
    }

    public static byte[] getRequstByte(Method method, String address, Map<String, String> params) throws MalformedURLException {
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
