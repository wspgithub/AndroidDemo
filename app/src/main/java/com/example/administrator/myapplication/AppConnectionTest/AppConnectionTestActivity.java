package com.example.administrator.myapplication.AppConnectionTest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@ShowActivity
public class AppConnectionTestActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_connection);
        button = findViewById(R.id.getData);
        textView = findViewById(R.id.showTv);
        imageView = findViewById(R.id.imShow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                });
                thread.start();
            }
        });
    }

    private void getData(){
        //String url="http://mngtest.netvoxcloud.com:80/smarthome/api/user.do?seq=Y03IkVn6x4idzWeBjaD_1_0_-*GET_HOMES-*[18659284291]&timestamp=1563500500000&user=18659284291&data=%7B%22pagenum%22%3A1%2C%22user%22%3A%2218659284291%22%2C%22op%22%3A%22list_house%22%2C%22pagesize%22%3A0%7D&sign=8d5f770961fa7f6fa696a85f1630c515";
        String url = "http://www.iloveturong.com:8080/V2Test/servlet/login";
        //String url = "http://www.iloveturong.com:8080/forever/img/pig.jpg";
        URL reqURL;
        String ret = "";
        HttpURLConnection uRLConnection = null;
        // Date date = new Date();
        InputStream is = null;
        // ret = RequestMock.request(result.getOp(), result.getSeq());

        try {
            reqURL = new URL(url);
            // 根据URL对象打开链接
            uRLConnection = (HttpURLConnection) reqURL.openConnection();
            // 设置请求的方式
            uRLConnection.setRequestMethod("GET");
            // 设置请求的超时时间
            uRLConnection.setReadTimeout(5000);
            uRLConnection.setConnectTimeout(5000);
            // 设置请求的头
            uRLConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            uRLConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置请求的头
            // uRLConnection.setRequestProperty("Content-Length",
            // String.valueOf(data.getBytes().length));
            // 设置请求的头
            uRLConnection
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            uRLConnection.setDoOutput(false); // 发送POST请求必须设置允许输出
            uRLConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            // setDoInput的默认值就是true

            // 获取输出流
            // Debug.e(Debug.TAG_LEVEL_CHANNEL, "cloud req:"
            // + cmd.getParams().get("json"));

            if (uRLConnection.getResponseCode() == 200) {
                is = uRLConnection.getInputStream();
                byte by[] = readStream(is);

                    ret = new String(by, "utf-8");
                ret = ret.replace("\n", "").replace("\r", "");
                Log.e("app",ret);
                Message message = handler.obtainMessage();
                message.arg1 = 1;
                message.obj = ret;
                handler.sendMessage(message);
            }else{
                Log.e("app","连接失败");
            }


        }catch (Exception e) {
            e.printStackTrace();
            Log.e("app","连接异常");
        }
    }

    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case 1:
                    String st = (String) msg.obj;
                    textView.setText("");
                    textView.setText(st);
//                    InputStream is = (InputStream)msg.obj;
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    imageView.setImageBitmap(bitmap);
                    break;
                default:break;
            }
        }
    };
}
