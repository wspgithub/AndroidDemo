package com.example.administrator.myapplication.OkHttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import okio.BufferedSink;
import okio.Okio;

@ShowActivity
public class SocketHttpTestActivity  extends AppCompatActivity implements View.OnClickListener {

    private TextView sendPost;


    private Socket socket;
    private BufferedSink sink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_http_test_activity);
        init();
    }

    private void init(){
        sendPost = findViewById(R.id.sendPost);
        sendPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.sendPost:

                 Runnable runnable = new Runnable() {
                     @Override
                     public void run() {
                         sendPost();
                     }
                 };

                 Thread thread = new Thread(runnable);
                 thread.start();

                 break;
                 default:break;
         }
    }

    private void sendPost() {
        try {
            socket = new Socket("218.104.133.234",80);
            //socket = new Socket("www.javathinker.org", 80);
          //  sink = Okio.buffer(Okio.sink(socket));
            StringBuffer sb = new StringBuffer("GET /group1/M00/00/05/wKgB91ny4waAPklgAAMAAPDWNn0586.png HTTP/1.1\r\n");
            // 以下为请求头
            sb.append("Host: 218.104.133.234\r\n");
            sb.append("User-Agent: okhttp/3.6.0\r\n");
            sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
            // 注意这里不要使用压缩 否则返回乱码
            sb.append("Accept-Encoding: \r\n");
            sb.append("Connection: keep-alive\r\n");
            // 注意这里要换行结束请求头
            sb.append("\r\n");
            Log.e("socket",sb.toString());
            try {
                OutputStream os = socket.getOutputStream();
                os.write(sb.toString().getBytes());

                InputStream is = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = is.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
                }
                Log.e("socket",new String(baos.toByteArray()));
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
