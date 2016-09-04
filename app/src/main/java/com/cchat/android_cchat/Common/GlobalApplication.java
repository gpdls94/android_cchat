/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cchat.android_cchat.Common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class GlobalApplication extends Application {
    private static volatile GlobalApplication instance = null;
    private static boolean isNetwork = false;
    private Activity act;

    public GlobalApplication(Activity act) {
        this.act = act;
    }

    public GlobalApplication() {
    }

    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public boolean isNewtWork() {
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo _wifi_network = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(_wifi_network != null) {
                // wifi, 3g 둘 중 하나라도 있을 경우
                if(_wifi_network != null && activeNetInfo != null){
                    isNetwork = true;
                }
                // wifi, 3g 둘 다 없을 경우
                else{
                    isNetwork = false;
                    new CreateDialog(act).network();
                }
            }
        } catch (Exception e) {
            Log.i("ULNetworkReceiver", e.getMessage());
        }

        return isNetwork;
    }
}
