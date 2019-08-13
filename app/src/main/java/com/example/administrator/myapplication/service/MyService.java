package com.example.administrator.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {


    private MyBinder myBinder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("服务","创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("服务", "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("服务","销毁");
    }

    class MyBinder extends Binder{

        public void startDownLoad(){
            Log.e("服务","开始下载");
        }
    }
}
