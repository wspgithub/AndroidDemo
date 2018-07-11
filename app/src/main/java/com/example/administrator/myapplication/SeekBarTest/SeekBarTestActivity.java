package com.example.administrator.myapplication.SeekBarTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/5/3.
 */
@ShowActivity()
public class SeekBarTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textthumbseekbar);
    }
}
