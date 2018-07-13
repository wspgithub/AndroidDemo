package com.example.administrator.myapplication.ArchitectureComponents;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by Administrator on 2018/7/13.
 */

public class MyListener implements LifecycleObserver {

    public MyListener() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void Start(){
        Log.e("生命","控件生命开始");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void Stop(){
        Log.e("生命","控件生命结束");
    }
}
