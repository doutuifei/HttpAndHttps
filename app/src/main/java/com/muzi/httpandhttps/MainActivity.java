package com.muzi.httpandhttps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.muzi.httpandhttps.enums.Method;
import com.muzi.httpandhttps.utils.AccountUtils;
import com.muzi.httpandhttps.utils.Request;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void httpDefaultRequest(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request.request(Method.POST, AccountUtils.URL, AccountUtils.getParams(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
