package com.example.administrator.myapplication.OkHttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import okio.BufferedSink;
import okio.Okio;

@ShowActivity
public class SocketHttpTestActivity  extends AppCompatActivity implements View.OnClickListener {

    private TextView sendPost;


    private Socket socket;
    private BufferedSink sink;
    private ImageView imShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_http_test_activity);
        init();
    }

    private void init(){
        sendPost = findViewById(R.id.sendPost);
        sendPost.setOnClickListener(this);
        imShow = findViewById(R.id.imShow);
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
            Log.e("socket",("编码格式"+Charset.defaultCharset().name()));
            socket = new Socket("47.111.190.135",8080);
            //socket = new Socket("www.javathinker.org", 80);
          //  sink = Okio.buffer(Okio.sink(socket));
            StringBuffer sb = new StringBuffer("GET /forever/img/tx.txt HTTP/1.1\r\n");
            // 以下为请求头
            sb.append("Host: 47.111.190.135\r\n");
            sb.append("User-Agent: okhttp/3.6.0\r\n");
            sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
            // 注意这里不要使用压缩 否则返回乱码
            sb.append("Accept-Encoding: \r\n");
            sb.append("Connection: keep-alive\r\n");

            sb.append("Accept-Charset: utf-8\r\n");
            sb.append("contentType: utf-8\r\n");
            // 注意这里要换行结束请求头
            sb.append("\r\n");
            Log.e("socket",sb.toString());
            try {
                OutputStream os = socket.getOutputStream();
                os.write(sb.toString().getBytes());

                InputStream is = socket.getInputStream();
                Message message = MyHandler.obtainMessage();
                message.arg1 = 0;
                message.obj = is;
                MyHandler.sendMessage(message);

//                String tem;
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
//                while((tem = bufferedReader.readLine())!=null) {
//                    System.out.println(tem);
//                    Log.e("socket",tem);
//                }

                StringBuffer response = new StringBuffer();
                    BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                Log.e("socket",response.toString());

//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] bytes = new byte[1024];
//                int len = -1;
//                while ((len = is.read(bytes)) != -1) {
//                    baos.write(bytes, 0, len);
//                }
               // Log.e("socket",new String(baos.toByteArray()));
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler MyHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0:
//                InputStream is = (InputStream)msg.obj;
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                imShow.setImageBitmap(bitmap);

                    break;
                default:break;
            }
        }
    };
}
