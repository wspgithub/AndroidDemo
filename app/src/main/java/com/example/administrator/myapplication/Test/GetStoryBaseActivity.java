package com.example.administrator.myapplication.Test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStoryBaseActivity extends AppCompatActivity {
    public String allContent="";
    public OkHttpClient okHttpClient = new OkHttpClient();


    //异步get
    public void AsynchronousGet(String url){

        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(url).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okhttp","失败");
            }
            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StringBuffer stringBuffer = new StringBuffer();
                BufferedReader in = new BufferedReader(new InputStreamReader(response.body().byteStream(),"gbk"));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = stringBuffer.toString();
                handler.sendMessage(message);
                //  Log.e("okhttp","成功"+stringBuffer.toString());
            }
        });
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandleMessage(msg);
        }
    };


    /**
     * 重写次方法
     * @param msg
     */
    public void HandleMessage(Message msg){

    }

}
