package com.example.administrator.myapplication.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
@ShowActivity
public class MyServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btStart;
    private Button btStop;
    private Button btBind;
    private Button btunBind;
    private MyService.MyBinder myBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);
        btStart = findViewById(R.id.start);
        btStart.setOnClickListener(this);
        btStop = findViewById(R.id.stop);
        btStop.setOnClickListener(this);
        btBind = findViewById(R.id.bind);
        btBind.setOnClickListener(this);
        btunBind = findViewById(R.id.unbind);
        btunBind.setOnClickListener(this);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder)service;
            myBinder.startDownLoad();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(this,MyService.class);
                startService(intent);
                break;
            case R.id.stop:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                   break;
            case R.id.bind:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
                break;

            case R.id.unbind:
                   unbindService(serviceConnection);
                break;

            default:break;
        }
    }
}
