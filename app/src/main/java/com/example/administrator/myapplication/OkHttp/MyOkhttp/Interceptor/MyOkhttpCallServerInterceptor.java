package com.example.administrator.myapplication.OkHttp.MyOkhttp.Interceptor;

import android.os.Message;
import android.util.Log;

import com.example.administrator.myapplication.OkHttp.MyOkhttp.CB.MyOkhttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;

public final class MyOkhttpCallServerInterceptor implements MyOkhttpInterceptor {
    private Socket socket;
    @Override
    public MyOkhttpResponse intercept(Chain chain) {
        MyOkhttpResponse myOkhttpResponse = new MyOkhttpResponse();
        myOkhttpResponse.setMessage("测试");
        return sendPost();
    }

    private MyOkhttpResponse sendPost() {
        MyOkhttpResponse myOkhttpResponse = new MyOkhttpResponse();
        try {
            Thread t = Thread.currentThread();
            String name = t.getName();
            Log.e("socket","线程"+ name);
            Log.e("socket",("编码格式"+ Charset.defaultCharset().name()));
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
                myOkhttpResponse.setMessage(response.toString());
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return myOkhttpResponse;
    }
}
