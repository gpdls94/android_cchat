package com.cchat.android_cchat.Activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cchat.android_cchat.Common.GlobalApplication;
import com.cchat.android_cchat.R;

public class SplashActivity extends Activity {

    private static boolean isNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getActionBar().hide();
        isNetwork = new GlobalApplication(this).isNewtWork();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String TAG = getIntent().getStringExtra("TAG");

//        if(isNetwork) {
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    finish();

                    if (TAG != null && TAG.equals("ConnectActivity")) {
                        /**
                         *
                         *  캐릭터 생성 프로세스
                         *
                         */

//                        Toast.makeText(SplashActivity.this, "캐/릭/터/생/성/중....", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SplashActivity.this, TabActivity.class));

                    } else {
                        startActivity(new Intent(SplashActivity.this, AccountActivity.class));
                    }
                }
            };

            handler.sendEmptyMessageDelayed(0, 2000);
//        } else {
//            (new CreateDialog(this)).network();
//        }
    }
}