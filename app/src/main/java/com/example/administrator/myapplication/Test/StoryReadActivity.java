package com.example.administrator.myapplication.Test;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;

@ShowActivity
public class StoryReadActivity extends GetStoryBaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ScrollView scrollView;
    private TextView tvContent;
    private TextView last;
    private TextView next;
    private String leftoverContent="";
    private String mainUrl = "https://www.nbiquge.com";
    private String url = "";
    private String lastUrl="";
    private String nextUrl="";
    private Document document;
    private GetStoryTool storyTool;

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
        storyTool = new BiQuGeTool();
        mainUrl = getIntent().getStringExtra("mainUrl");
        url = getIntent().getStringExtra("url");
        init();
    }

     private void init(){
         AsynchronousGet(mainUrl+url);
    }
//https://www.nbiquge.com/0_138/62682.html


    @Override
    public void HandleMessage(Message msg) {
        super.HandleMessage(msg);
        switch (msg.what){
            case 1:
                allContent = (String) msg.obj;
                document = Jsoup.parse(allContent);
                scrollView.scrollTo(0,0);
                readTitle(document);
                readContent(document);
                lastUrl = storyTool.getLastUrl(document);
                nextUrl = storyTool.getNextUrl(document);
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

    private void readTitle(Document document){
//        int start = text.indexOf("<h1>");
//        int end = text.indexOf("</h1>");
//        title=text.substring(start+4,end);

       // title = storyTool.getTitle();
        tvTitle.setText(storyTool.getTitle(document));
        // System.out.print(title);
    }

    private void  readContent(Document document){
//        String title="";
//        int start =  0;
//        int end = 0;
//
//        start = text.indexOf("<div id=\"content\">");
//        content = text.substring(start+18,text.length());
//        end = content.indexOf("</div>");
//        leftoverContent = content.substring(end);
//        content = content.substring(0,end);
//
//        content=content.replace("// 免费电子书下载//","")
//                .replace("// 访问下载txt小说//","")
//                .replace("<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;","\n\n")
//                .replace("&nbsp;&nbsp;&nbsp;&nbsp;","\n\n");
        tvContent.setText(storyTool.getContent(document));
//      //  System.out.print(content);
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
