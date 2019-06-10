package com.example.administrator.myapplication.LifeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

@ShowActivity
public class ActivityA extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("生命周期","AonCreate");
        setContentView(R.layout.main);
        textView = findViewById(R.id.test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityA.this,ActivityB.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("生命周期","AonRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("生命周期","AonStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("生命周期","AonResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("生命周期","AonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("生命周期","AonStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("生命周期","AonDestroy");
    }
}
