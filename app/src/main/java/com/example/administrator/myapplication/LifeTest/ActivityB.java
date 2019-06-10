package com.example.administrator.myapplication.LifeTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("生命周期","BonCreate");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("生命周期","BonRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("生命周期","BonStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("生命周期","BonResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("生命周期","BonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("生命周期","BonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("生命周期","BonDestroy");
    }
}
