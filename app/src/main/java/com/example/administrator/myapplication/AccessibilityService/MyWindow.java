package com.example.administrator.myapplication.AccessibilityService;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.myapplication.R;

public class MyWindow extends ConstraintLayout implements View.OnClickListener {
    public View view;
    private int x;
    private int y;
    private WindowManager manager;
    private Button onClick;
    private Button startYH;
    private Button stopYH;
    private Button stopService;
    public StartService IStartService = null;
    public StopService IStopService = null;

    public interface StartService{
        public void start();
    }

    public interface StopService{
        public void stop();
    }

    public MyWindow(Context context) {
        super(context);
        init();
    }

    public MyWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.my_window, this);
        manager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        onClick = view.findViewById(R.id.onClick);
        onClick.setOnClickListener(this);

        startYH = view.findViewById(R.id.StartYh);
        startYH.setOnClickListener(this);

        stopYH = view.findViewById(R.id.StopYh);
        stopYH.setOnClickListener(this);

        stopService = view.findViewById(R.id.StopService);
        stopService.setOnClickListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("点击坐标X",""+event.getRawX());
        Log.e("点击坐标Y",""+event.getRawY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                int movedX = nowX - x;
                int movedY = nowY - y;
                x = nowX;
                y = nowY;
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams)view.getLayoutParams();//new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,0,0, PixelFormat.TRANSPARENT);
                layoutParams.x = layoutParams.x + movedX;
                layoutParams.y = layoutParams.y + movedY;

                // 更新悬浮窗控件布局
                manager.updateViewLayout(view, layoutParams);
                break;
            default:
                break;
        }
        return false;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onClick:
                AccessibilityServiceUtil.getInStance().coordinatesClick(AccessibilityServiceUtil.getInStance().getStatusAccessibilityService(), 500, 500) ;
                break;
            case R.id.StartYh:
                if(this.IStartService!=null)
                {
                    IStartService.start();
                }
                break;

            case R.id.StopYh:
                if(this.IStopService!=null)
                {
                    IStopService.stop();
                }
                break;
            case R.id.StopService:

                break;

            default:
                break;
        }

    }

    public void setIStartService(StartService IStartService) {
        this.IStartService = IStartService;
    }

    public void setIStopService(StopService IStopService) {
        this.IStopService = IStopService;
    }
}
