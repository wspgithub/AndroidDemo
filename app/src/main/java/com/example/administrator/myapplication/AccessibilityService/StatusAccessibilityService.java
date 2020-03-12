package com.example.administrator.myapplication.AccessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class StatusAccessibilityService extends AccessibilityService {


    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        AccessibilityServiceUtil.getInStance().setStatusAccessibilityService(this);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //在服务正常的情况控制服务，isService默认为false,可在Activity中设置为true即可
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.e("","");
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.e("","");
                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyBinder extends Binder {

        public void startDownLoad(){
            Log.e("服务","开始下载");
        }
    }
}
