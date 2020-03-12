package com.example.administrator.myapplication.AccessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.service.MyService;
import com.orhanobut.logger.Logger;

@ShowActivity
public class MyAccessibilityActivity extends AppCompatActivity implements MyWindow.StartService,MyWindow.StopService {

    private Button startService;
    private Button click;
    private MyWindow button;
    private YHThread yhThread = null;
    private boolean isYHThreadRun = false;
    private int ScreenWidth = 0;
    private int ScreenHeigth = 0;
    //private AccessibilityService.IAccessibilityServiceClientWrapper myBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_accessibility_activity);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        ScreenHeigth = wm.getDefaultDisplay().getHeight();


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
                //serviceIsRunning(MyAccessibilityActivity.this);
                showFloatingWindow();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            // 获取WindowManager服务
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            if(button == null) {
                // 新建悬浮窗控件
                button = new MyWindow(getApplicationContext());
                button.setBackgroundColor(Color.BLUE);
                button.setIStopService(this);
                button.setIStartService(this);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                layoutParams.format = PixelFormat.RGBA_8888;
                layoutParams.width = 500;
                layoutParams.height = 500;
                layoutParams.x = 300;
                layoutParams.y = 300;
                // 将悬浮窗控件添加到WindowManager
                windowManager.addView(button,layoutParams);
            }
        }else{
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + MyAccessibilityActivity.this.getPackageName()));
            MyAccessibilityActivity.this.startActivityForResult(intent, 556);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 556:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(MyAccessibilityActivity.this,"悬浮窗权限未开启，请在设置中手动打开",Toast.LENGTH_LONG);
                    return;
                }
               // WindowController.getInstance().showThumbWindow();
                break;
        }
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

    private class YHThread extends Thread{
        public volatile boolean exit = false;
        public void run() {
            try {
                AccessibilityServiceUtil.getInStance().coordinatesClick(AccessibilityServiceUtil.getInStance().getStatusAccessibilityService(), getValue(455,1920,ScreenHeigth), getValue(711,1080,ScreenWidth));
                Thread.sleep(3000);
                while (!exit) {
                    Logger.e("YH正在运行", "");
                    AccessibilityServiceUtil.getInStance().coordinatesClick(AccessibilityServiceUtil.getInStance().getStatusAccessibilityService(), getValue(1744,1920,ScreenHeigth), getValue(945,1080,ScreenWidth));
                    Thread.sleep(50000);
                    AccessibilityServiceUtil.getInStance().coordinatesClick(AccessibilityServiceUtil.getInStance().getStatusAccessibilityService(), getValue(1020,1920,ScreenHeigth), getValue(847,1080,ScreenWidth));
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getValue(int a,int b,int c){
        float f = a/b;
        return (int)f*c;
    }

    @Override
    public void start() {
        if(isYHThreadRun == false) {
            isYHThreadRun = true;
            yhThread = new YHThread();
            yhThread.start();
        }
    }

    @Override
    public void stop() {
        isYHThreadRun = false;
        yhThread.exit = true;
        // AccessibilityServiceUtil.getInStance().setStatusAccessibilityService(null);
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.removeView(button);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(yhThread !=null){
            yhThread.exit = true;
        }
    }

}
