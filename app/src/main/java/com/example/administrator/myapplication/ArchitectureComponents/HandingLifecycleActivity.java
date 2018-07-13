package com.example.administrator.myapplication.ArchitectureComponents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.Annotation.ShowActivity;

/**
 * Created by Administrator on 2018/7/13.
 */
@ShowActivity
public class HandingLifecycleActivity extends AppCompatActivity {
    private MyListener myListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListener = new MyListener();
        this.getLifecycle().addObserver(myListener);
    }
}
