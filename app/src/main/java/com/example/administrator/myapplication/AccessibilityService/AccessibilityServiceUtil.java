package com.example.administrator.myapplication.AccessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public  class AccessibilityServiceUtil {

    private static AccessibilityServiceUtil accessibilityServiceUtil = null;
    private StatusAccessibilityService statusAccessibilityService;
    public static AccessibilityServiceUtil getInStance(){

        if(accessibilityServiceUtil == null){
            accessibilityServiceUtil = new AccessibilityServiceUtil();
        }

        return accessibilityServiceUtil;
    }

    public void setStatusAccessibilityService(StatusAccessibilityService statusAccessibilityService) {
        this.statusAccessibilityService = statusAccessibilityService;
    }

    public StatusAccessibilityService getStatusAccessibilityService() {
        return statusAccessibilityService;
    }

    //通过坐标点击
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean coordinatesClick(AccessibilityService service, int x, int y) {
        if(service == null)
            return false;
        Log.e("打印","");
        Path path = new Path();
        path.moveTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 200)).build();
        return service.dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }
        }, null);
    }

}
