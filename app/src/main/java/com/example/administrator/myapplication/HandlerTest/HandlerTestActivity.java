package com.example.administrator.myapplication.HandlerTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/9/17.
 */

public class HandlerTestActivity extends AppCompatActivity {

    private Handler mHandler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_test);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.arg1){
                    case 1:break;
                    default:break;
                }
            }
        };
    }

    class runable implements Runnable{
        @Override
        public void run() {


            Message message = new Message();
            message.arg1 = 1;
            mHandler.sendMessage(message);
        }
    }
}
