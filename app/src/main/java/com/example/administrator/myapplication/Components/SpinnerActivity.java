package com.example.administrator.myapplication.Components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;

/**
 * Created by wsp on 2019/2/21.
 */
@ShowActivity
public class SpinnerActivity extends AppCompatActivity {

    private Spinner spinner;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_layout);
        init();
    }

    private void init(){
        spinner = findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this,getData());
        spinner.setAdapter(spinnerAdapter);
    }

    private ArrayList<String> getData() {
        // 数据源
        ArrayList<String> dataList = new ArrayList<String>();
        dataList.add("北京");
        dataList.add("上海");
        dataList.add("南京");
        dataList.add("宜昌");
        return dataList;
    }
}
