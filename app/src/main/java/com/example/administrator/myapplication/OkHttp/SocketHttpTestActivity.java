package com.example.administrator.myapplication.OkHttp;

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

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.net.SocketFactory;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

@ShowActivity
public class SocketHttpTestActivity  extends AppCompatActivity implements View.OnClickListener {

    private TextView sendPost;
    private TextView txShow;


    private Socket socket;
    private BufferedSink sink;
    private ImageView imShow;
    private BufferedSink mSink;
    private BufferedSource mSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_http_test_activity);
        init();
    }

    private void init(){
        sendPost = findViewById(R.id.sendPost);
        sendPost.setOnClickListener(this);

        txShow = findViewById(R.id.txShow);
        txShow.setOnClickListener(this);

        imShow = findViewById(R.id.imShow);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CreatSocket();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

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

    private void CreatSocket(){
        InetAddress ip2= null;
        try {
            ip2 = InetAddress.getByName("www.iloveturong.com");
            Log.e("socket",ip2.getHostAddress());//47.111.190.135
            Log.e("socket",ip2.getHostName());// www.iloveturong.com
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
        Log.e("socket",("编码格式"+Charset.defaultCharset().name()));
        if(socket == null) {
            socket = SocketFactory.getDefault().createSocket();
            // socket = new Socket("47.111.190.135",8080);
            // InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved("47.111.190.135", 8080);
            InetSocketAddress inetSocketAddress = new InetSocketAddress("47.111.190.135", 8080);
            socket.setKeepAlive(true);
            socket.setTcpNoDelay(true);
            socket.connect(inetSocketAddress, 100000);
        }} catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        // socket = new Socket("47.111.190.135",8080);
        //socket = new Socket("www.javathinker.org", 80);
        //  sink = Okio.buffer(Okio.sink(socket));


    }

    private void sendPost() {

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


                mSink = Okio.buffer(Okio.sink(socket));
                mSource = Okio.buffer(Okio.source(socket));

                mSink.writeUtf8(sb+"\n");
                mSink.flush();
//                OutputStream os = socket.getOutputStream();
//                os.write(sb.toString().getBytes());
//                InputStream is = socket.getInputStream();
//                Message message = MyHandler.obtainMessage();
//                message.arg1 = 0;
//                message.obj = is;
//                MyHandler.sendMessage(message);

//                String tem;
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
//                while((tem = bufferedReader.readLine())!=null) {
//                    System.out.println(tem);
//                    Log.e("socket",tem);
//                }

                StringBuffer response = new StringBuffer();
//                    BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String inputLine;
                    while ((inputLine = mSource.readUtf8Line()) != null) {
                        response.append(inputLine);
                    }
                Message message = MyHandler.obtainMessage();
                message.arg1 = 1;
                message.obj = response.toString();
                MyHandler.sendMessage(message);
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



    }

    private Handler MyHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 0:
//                InputStream is = (InputStream)msg.obj;
//                Bitmap bitmap = BitmapFactory.decodeStream(is);

                case 1:
                    txShow.setText((String)msg.obj);

                    break;
                default:break;
            }
        }
    };
}
