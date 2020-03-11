package com.example.administrator.myapplication.AccessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.service.MyService;

@ShowActivity
public class MyAccessibilityActivity extends AppCompatActivity {

    private Button startService;
    private Button click;
    //private AccessibilityService.IAccessibilityServiceClientWrapper myBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_accessibility_activity);
        startService = findViewById(R.id.startService);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
        click = findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceIsRunning(MyAccessibilityActivity.this);
            }
        });
    }

    public static boolean serviceIsRunning(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.administrator.myapplication.AccessibilityService.StatusAccessibilityService"
                    .equals(service.service.getClassName())) {
                Log.e("打印","");
                return true;
            }
        }
        return false;
    }


//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (MyService.MyBinder)service;
//            myBinder.startDownLoad();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };


}
