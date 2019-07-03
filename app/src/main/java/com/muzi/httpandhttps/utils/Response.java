package com.muzi.httpandhttps.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * 作者: lipeng
 * 时间: 2019/7/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class Response {

    /**
     * 解析返回结果
     *
     * @param inputStream
     * @throws IOException
     */
    public static void response(InputStream inputStream) throws IOException {
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

}
