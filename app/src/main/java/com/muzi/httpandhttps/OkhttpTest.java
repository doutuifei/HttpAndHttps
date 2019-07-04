package com.muzi.httpandhttps;

import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.MD5Utils;
import com.muzi.httpandhttps.utils.SSLUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 作者: lipeng
 * 时间: 2019/7/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class OkhttpTest {

    public static String cerPath = "C:\\Users\\mzyq\\Desktop\\cer\\ekwing.cer";

    public static void main(String[] args) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        System.out.println(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY));

        try {
            InputStream inputStream = new FileInputStream(new File(cerPath));
            SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(inputStream);
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        OkHttpClient client = builder.build();

        RequestBody requestBody = new FormBody.Builder()
                .add("v", "1.4")
                .add("osv", "7.0")
                .add("deviceToken", "HNB4T18417001159")
                .add("driverType", "bzk-w00")
                .add("os", "Android")
                .add("driverCode", "2.1.0")
                .add("username", "10278315")
                .add("password", MD5Utils.getMD5Encode("a666666"))
                .build();

        Request request = new Request.Builder()
                .url(AccountUtils.URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });

    }

}
