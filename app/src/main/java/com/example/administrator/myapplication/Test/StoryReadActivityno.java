package com.example.administrator.myapplication.Test;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ShowActivity
public class StoryReadActivityno extends GetStoryBaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ScrollView scrollView;
    private TextView tvContent;
    private TextView last;
    private TextView next;
    //1.创建OkHttpClient对象
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String leftoverContent="";
    private String mainUrl = "https://www.nbiquge.com";
    private String lastUrl="";
    private String nextUrl="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_read_activity);
        tvTitle = findViewById(R.id.title);
        scrollView = findViewById(R.id.scrollView);
        tvContent = findViewById(R.id.content);
        last = findViewById(R.id.last);
        last.setOnClickListener(this);
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
        init();
    }

     private void init(){
         AsynchronousGet("https://www.nbiquge.com/0_138/62682.html");
    }
//https://www.nbiquge.com/0_138/62682.html


    @Override
    public void HandleMessage(Message msg) {
        super.HandleMessage(msg);
        switch (msg.what){
            case 1:
                allContent = (String) msg.obj;
                scrollView.scrollTo(0,0);
                readTitle(allContent);
                readContent(allContent);
                lastUrl = readLast(leftoverContent);
                nextUrl = readNext(leftoverContent);
                break;
            default:break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.last:
                AsynchronousGet(mainUrl+lastUrl);
                break;

            case R.id.next:
                AsynchronousGet(mainUrl+nextUrl);
                break;

            default:break;
        }
    }

    private  String readTitle(String text){
        String title="";
        int start = text.indexOf("<h1>");
        int end = text.indexOf("</h1>");
        title=text.substring(start+4,end);
        tvTitle.setText(title);
        // System.out.print(title);
        return title;
    }

    private  String readContent(String text){
        String title="";
        String content = "";
        int start =  0;
        int end = 0;

        start = text.indexOf("<div id=\"content\">");
        content = text.substring(start+18,text.length());
        end = content.indexOf("</div>");
        leftoverContent = content.substring(end);
        content = content.substring(0,end);

        content=content.replace("// 免费电子书下载//","")
                .replace("// 访问下载txt小说//","")
                .replace("<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;","\n\n")
                .replace("&nbsp;&nbsp;&nbsp;&nbsp;","\n\n");
        tvContent.setText(content);
      //  System.out.print(content);
        return title;
    }



    //<a href="      ">上一章</a>
    private  String readLast(String text){
        int start =  0;
        int end = 0;
        end = text.indexOf("\">上一章</a>");
        String content = "";
        for (int i = 0; i < 10; i++) {
            content = text.substring(end-10*i,end);
            if(content.contains("<a href="))
                break;
        }

        start = content.indexOf("href=\"");
        content = content.substring(start+6,content.length());
        return content;
    }

    private  String readNext(String text){
        int start =  0;
        int end = 0;
        end = text.indexOf("\">下一章</a>");
        String content = "";
        for (int i = 0; i < 10; i++) {
            content = text.substring(end-10*i,end);
            if(content.contains("<a href="))
                break;
        }

        start = content.indexOf("href=\"");
        content = content.substring(start+6,content.length());
        return content;
    }


    private static String readAssetsTxt(Context context, String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "gbk");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

}
