package com.example.graduationproject;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by 姜维昊 on 2017/3/8.
 */
public class MyLeanCloudApp extends Application {
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"pSRJyGIKeIonR0yuyzTpCPYJ-gzGzoHsz","UqJ6JzVK05M0QNwv0NskuhDm");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
