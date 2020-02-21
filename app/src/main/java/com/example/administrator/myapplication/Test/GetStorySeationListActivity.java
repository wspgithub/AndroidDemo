package com.example.administrator.myapplication.Test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
@ShowActivity
public class GetStorySeationListActivity extends GetStoryBaseActivity implements StorySeationViewAdapter.StorySeationOnclick {

    private String mainUrl = "";
    private RecyclerView recyclerView;
    private StorySeationViewAdapter storySeationViewAdapter;

    private HashMap<String,String> map = new HashMap<>();
    private ArrayList<String> seations = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_story_seation_list_activity);
        recyclerView = findViewById(R.id.recyclerView);
        init();
    }

    private void init(){
        mainUrl = "https://www.nbiquge.com";
        AsynchronousGet(mainUrl+"/0_138/");
    }


    @Override
    public void HandleMessage(Message msg) {
        super.HandleMessage(msg);
        switch (msg.what){
            case 1:
                allContent = (String) msg.obj;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message message = handler.obtainMessage();
                            message.what = 2;
                            getNovelIndex(allContent);
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                break;
            case 2:
                if (storySeationViewAdapter == null) {
                    storySeationViewAdapter = new StorySeationViewAdapter(this, seations);
                    storySeationViewAdapter.setStorySeationOnclick(this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(storySeationViewAdapter);
                }
                storySeationViewAdapter.notifyDataSetChanged();
                break;
            default:break;
        }

    }

    public void getNovelIndex(String html) throws IOException {
        Document document = Jsoup.parse(html);
        for (Element a : document.getElementsByTag("a")) {
            if ( a.attr("href").contains(".html")) {
                seations.add(a.text());
                map.put(a.text(),a.attr("href"));
            }
        }
    }

    @Override
    public void onClick(String st) {
        Intent intent = new Intent(this,StoryReadActivity.class);
        intent.putExtra("mainUrl",mainUrl);
        intent.putExtra("url",map.get(st));
        startActivity(intent);
    }
}
