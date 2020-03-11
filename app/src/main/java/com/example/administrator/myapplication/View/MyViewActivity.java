package com.example.administrator.myapplication.View;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@ShowActivity
public class MyViewActivity extends AppCompatActivity {

    private ArrayList<PieData> pieDataList = new ArrayList<>();
    private final LinkedHashMap<Integer,String> colorlinkedHashMap = new LinkedHashMap<>();
    private PieChatView pieChatView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view);
        colorlinkedHashMap.put(0,"#8673C1");
        colorlinkedHashMap.put(1,"#6A7178");
        colorlinkedHashMap.put(2,"#99BF63");
        colorlinkedHashMap.put(3,"#D7A587");
        colorlinkedHashMap.put(4,"#47DC1E");
        colorlinkedHashMap.put(5,"#C73376");
        colorlinkedHashMap.put(6,"#E1C83E");
        for (int i = 0; i < 7; i++) {
            PieData pieData = new PieData();
            pieData.setValue(i+3);
            pieData.setColor(colorlinkedHashMap.get(i));
            pieData.setValueText(i+"");
            pieDataList.add(pieData);
        }
        pieChatView = findViewById(R.id.pie);

        pieChatView.setData(pieDataList);
    }
}
