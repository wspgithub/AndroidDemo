package com.example.administrator.myapplication.HandlerTest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2018/9/17.
 */
@ShowActivity
public class HandlerTestActivity extends AppCompatActivity {

    private Handler mHandlerOne;
    private Handler mHandlerTwo;
    private TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_test);
        textView = findViewById(R.id.uiText);
        mHandlerOne = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.arg1){
                    case 1:
                        textView.setText(msg.obj+"");
                        break;
                    default:break;
                }
            }
        };

        mHandlerTwo = new Handler(new mCallBack());

        myThrad myThrad = new myThrad();
        myThrad.start();


    }

    class mCallBack implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {

            return false;
        }
    }

    class  myThrad extends Thread{
        @Override
        public void run() {
            Looper.prepare();//新建的线程是没有消息队列的，需要创建
            //子线程的handler相遇更新UI线程需要获取UI线程的Loooper
            Handler handler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.arg1){
                        case 1:
                            textView.setText(msg.obj+"22");
                            break;
                        default:break;
                    }
                }
            };
            Message message = new Message();
            message.arg1 = 1;
            message.obj = "测试";
            handler.sendMessage(message);
            Looper.loop();//开启消息循环
        }
    }

}
