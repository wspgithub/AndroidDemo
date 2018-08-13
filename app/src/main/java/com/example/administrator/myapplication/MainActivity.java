package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.administrator.myapplication.Annotation.ShowActivity;

import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MainListItem> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.mRecyclerView);
        scanPackage();
        init();
    }

    private void init(){
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,arrayList);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    ////

    public void scanPackage() {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread
                    .currentThread().getContextClassLoader();
            DexFile dex = new DexFile(this.getBaseContext().getPackageCodePath());
            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement();
                if (entryName.startsWith("com.example.administrator.myapplication")) {
                    Class<?> entryClass = Class.forName(entryName, true, classLoader);
                    ShowActivity annotation = entryClass.getAnnotation(ShowActivity.class);
                    if (annotation != null) {
                        arrayList.add(new MainListItem(entryClass.getSimpleName()+"测试",entryClass));
                    }
                }
            }

            Log.e("","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
