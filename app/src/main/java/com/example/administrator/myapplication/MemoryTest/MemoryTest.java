package com.example.administrator.myapplication.MemoryTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
@ShowActivity
public class MemoryTest extends AppCompatActivity implements MemoryMode.IMemory {
    private Button button;
    private MemoryMode memoryMode;
    private static Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button_layout);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryTest.this,MemoryTestTwo.class);
                startActivity(intent);
            }
        });

        //activity = this;
        memoryMode = new MemoryMode();
        memoryMode.setiMemory(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //memoryMode.setiMemory(null);
    }
}
