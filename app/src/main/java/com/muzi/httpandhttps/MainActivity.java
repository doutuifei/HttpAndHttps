package com.muzi.httpandhttps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.muzi.httpandhttps.enums.Method;
import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.HttpUtils;
import com.muzi.httpandhttps.utils.Request;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void httpRequest(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.get(AccountUtils.URL, AccountUtils.getParams(), false);
            }
        }).start();
    }

    public void httpsRequest(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtils.post(AccountUtils.URL, AccountUtils.getParams(), true);
            }
        }).start();
    }

    public void httpDefaultRequest(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request.requestDefault(Method.POST,AccountUtils.URL, AccountUtils.getParams());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
