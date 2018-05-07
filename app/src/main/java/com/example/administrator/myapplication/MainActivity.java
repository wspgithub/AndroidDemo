package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.administrator.myapplication.DataBindingTest.DataBingTestActivity;
import com.example.administrator.myapplication.FramentTest.TabFramentTest;
import com.example.administrator.myapplication.SeekBarTest.SeekBarTestActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MainListItem> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mRecyclerView);
        init();
    }

    private void init(){
        arrayList.add(new MainListItem("TextThumbSeekbar测试",SeekBarTestActivity.class));
        arrayList.add(new MainListItem("TabFramentTest测试",TabFramentTest.class));
        arrayList.add(new MainListItem("DataBingTest测试",DataBingTestActivity.class));
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,arrayList);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    ////
}
