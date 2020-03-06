package com.example.administrator.myapplication;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

//import com.qihoo360.replugin.RePluginApplication;
//
//public class MainApplication extends RePluginApplication {
//
//}
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}