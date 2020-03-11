package com.example.administrator.myapplication.AccessibilityService;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.administrator.myapplication.R;

public class MyWindow extends ConstraintLayout {
    public View view;
    private int x;
    private int y;
    private WindowManager manager;
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
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
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


}
