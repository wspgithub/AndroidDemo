package com.example.administrator.myapplication;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.OkHttp.OkIOTestActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.BaseDexClassLoader;
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
       // AllClass();
        init();
        Log.e("","");
    }

    private void init(){
        shellSortSmallToBig(arrayList);
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(this,arrayList);
        recyclerView.setAdapter(mainRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getAdapter().notifyDataSetChanged();

    }


    ////home

    private void AllClass(){
        arrayList.add(new MainListItem("OKIO测试", OkIOTestActivity.class));
    }

    public void scanPackage() {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread
                    .currentThread().getContextClassLoader();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){//若安卓版本小于7.0
                Field f = BaseDexClassLoader.class.getDeclaredField("pathList");
                f.setAccessible(true);
                Object obj = f.get(classLoader);
                Field f1 = obj.getClass().getDeclaredField("dexElements");
                f1.setAccessible(true);
                Object arr[]=(Object[]) f1.get(obj);
                for (Object oo : arr) {
                    Field f3 = oo.getClass().getDeclaredField("dexFile");
                    f3.setAccessible(true);
                    DexFile dexFile = (DexFile) f3.get(oo);
                    analysisEntries(dexFile.entries(), classLoader);
                }
            }else {//版本大于7.0

                DexFile dex = new DexFile(this.getPackageCodePath());
                Enumeration<String> entries = dex.entries();
                analysisEntries(entries, classLoader);
            }

            Log.e("","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analysisEntries(Enumeration<String> entries, PathClassLoader classLoader){
        //DexFile dex = null;
        try {
//            dex = new DexFile(this.getBaseContext().getPackageCodePath());
//            Enumeration<String> entries = dex.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement();
                if (entryName.startsWith("com.example.administrator.myapplication")) {
                    Class<?> entryClass = Class.forName(entryName, true, classLoader);
                    ShowActivity annotation = entryClass.getAnnotation(ShowActivity.class);
                    if (annotation != null) {
                        arrayList.add(new MainListItem(entryClass.getSimpleName() + "测试", entryClass));
                    }
                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void shellSortSmallToBig(ArrayList<MainListItem> list) {
        int j;
        MainListItem dev;
        for (int increment = list.size() / 2; increment > 0; increment /= 2) {
            for (int i = increment; i < list.size(); i++) {

                dev = list.get(i);
                for (j = i - increment; j >= 0; j -= increment) {

                    if (dev.getName().toLowerCase().charAt(0) < list.get(j).getName().toLowerCase().charAt(0)) {
                        list.set(j + increment,list.get(j));
                    } else if(dev.getName().toLowerCase().charAt(0) == list.get(j).getName().toLowerCase().charAt(0)) {
                        int len = dev.getName().length();
                        if(len>list.get(j).getName().length())
                            len = list.get(j).getName().length();
                        for (int k = 1; k < len; k++) {
                            if (dev.getName().toLowerCase().charAt(k) < list.get(j).getName().toLowerCase().charAt(k)) {
                                list.set(j + increment,list.get(j));
                                break;
                            }
                        }

                    }else{
                        break;
                    }
                }
                list.set(j + increment,dev);
            }
        }
    }
}
