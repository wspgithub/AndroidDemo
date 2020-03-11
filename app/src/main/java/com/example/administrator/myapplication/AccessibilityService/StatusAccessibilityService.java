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
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //在服务正常的情况控制服务，isService默认为false,可在Activity中设置为true即可
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                AccessibilityServiceUtil.coordinatesClick(this, 500, 500) ;
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                AccessibilityServiceUtil.coordinatesClick(this, 500, 500);
                break;
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    class MyBinder extends Binder {

        public void startDownLoad(){
            Log.e("服务","开始下载");
        }
    }
}
