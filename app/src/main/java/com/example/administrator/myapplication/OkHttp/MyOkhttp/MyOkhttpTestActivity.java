package com.example.administrator.myapplication.OkHttp.MyOkhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpCall;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpCallBack;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpRealCall;
import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpResponse;
import com.example.administrator.myapplication.R;

import java.io.IOException;

@ShowActivity
public class MyOkhttpTestActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private MyHandler myHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_okhttp_test_activity);
        myHandler = new MyHandler();
        button = findViewById(R.id.send);
        textView = findViewById(R.id.tv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOkhttpRealCall call = new MyOkhttpRealCall();
                call.enqueue(new MyOkhttpCallBack() {
                    @Override
                    public void onFailure(MyOkhttpCall myOkhttpCall, IOException io) {
                        Log.e("MyOkhttpCall","onFailure");
                        Message message = myHandler.obtainMessage();
                        message.what = 0;
                        myHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(MyOkhttpCall myOkhttpCall, MyOkhttpResponse myOkhttpResponse) {
                        Log.e("MyOkhttpCall","onResponse");
                        Message message = myHandler.obtainMessage();
                        message.what = 1;
                        message.obj = myOkhttpResponse;
                        myHandler.sendMessage(message);
                    }
                });
            }
        });

    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    textView.setText("ERROR");
                    break;
                case 1:
                    MyOkhttpResponse myOkhttpResponse = (MyOkhttpResponse)msg.obj;
                    textView.setText(myOkhttpResponse.getMessage());
                    break;
                default:break;
            }
        }
    }
}
