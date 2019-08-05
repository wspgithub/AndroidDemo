package com.example.administrator.myapplication.OkHttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.Annotation.ShowActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Administrator on 2018/8/1.
 */
@ShowActivity
public class OkHttpTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsynchronousGet();
    }

    //异步get
    private void AsynchronousGet(){
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url("http://www.iloveturong.com:8080/forever/img/tx.txt").method("GET",null).build();
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
                BufferedReader in = new BufferedReader(new InputStreamReader(response.body().byteStream(),"utf-8"));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                Log.e("okhttp","成功"+stringBuffer.toString());
            }
        });
    }

    //同步get
    private void SynchronousGet(){
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url("http://www.baidu.com").method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步调用,返回Response,会抛出IO异常
                    Response response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //下载图片
    public void downImage(View view) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .get()
                .url("http://avatar.csdn.net/B/0/1/1_new_one_object.jpg")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                //将图片显示到ImageView中
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // iv_result.setImageBitmap(bitmap);
                    }
                });
                //将图片保存到本地存储卡中
                File file = new File(Environment.getExternalStorageDirectory(), "image.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] temp = new byte[128];
                int length;
                while ((length = inputStream.read(temp)) != -1) {
                    fileOutputStream.write(temp, 0, length);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            }
        });
    }



    //POST提交键值对
    private void post1(){
        //1.创建OkHttpClient对象
        OkHttpClient  okHttpClient = new OkHttpClient();
        //2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
        RequestBody requestBody = new FormBody.Builder().add("name","zhangqilu").add("age","25").build();
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url("url").post(requestBody).build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //5.请求加入调度,重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    //POST提交字符串
    public void postString(View view) {
        OkHttpClient client = new OkHttpClient();
        //RequestBody中的MediaType指定为纯文本，编码方式是utf-8
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                "{username:admin;password:admin}");
        final Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //ToastUtil.showToast(PostStringActivity.this, "Post String 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                //ToastUtil.showToast(PostStringActivity.this, "Code：" + String.valueOf(response.code()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //tv_result.setText(responseStr);
                    }
                });
            }
        });
    }
    //POST提交表单
    public void postForm(View view) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "叶应是叶")
                .addFormDataPart("password", "叶应是叶")
                .build();
        final Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void postStreaming(View view) {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    public void postFile(View view) {
        OkHttpClient client = new OkHttpClient();
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("README.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }


}
